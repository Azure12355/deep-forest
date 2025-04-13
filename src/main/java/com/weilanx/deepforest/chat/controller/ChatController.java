// src/main/java/com/weilanx/deepforest/chat/controller/ChatController.java
package com.weilanx.deepforest.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
// 导入 Ark 相关的类
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import com.weilanx.deepforest.chat.config.ArkConfigurationProperties; // 导入配置类
import com.weilanx.deepforest.chat.dto.*;
import com.weilanx.deepforest.chat.mock.MockDataStore;
// RxJava 2 的导入 (如果需要显式声明，通常让类型推断处理)
// import io.reactivex.Flowable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// *** 关键：导入并使用 RxJava 2 适配器 ***
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 聊天功能的核心控制器，处理聊天相关的 HTTP 请求。
 * 包含获取历史记录、获取消息、发送消息并流式返回 AI 响应 (SSE)。
 * 已根据 ArkService 返回 RxJava 2 类型进行修复，使用 RxJava2Adapter。
 * 请务必配合正确的依赖管理（强制 RxJava 2 版本，排除传递依赖）解决 ClassNotFoundException。
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Resource
    private MockDataStore mockDataStore;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ArkService arkService;

    @Resource
    private ArkConfigurationProperties arkConfigurationProperties;


    // GET /history (保持不变)
    @GetMapping("/history")
    public ResponseEntity<List<HistoryGroupDto>> getChatHistory() {
        try {
            List<HistoryGroupDto> history = mockDataStore.getFormattedHistory();
            log.info("成功获取聊天历史记录，共 {} 组", history.size());
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            log.error("获取聊天历史失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /{chatId}/messages (保持不变)
    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<ChatMessageDto>> getChatMessages(@PathVariable String chatId) {
        try {
            List<ChatMessageDto> messages = mockDataStore.getMessagesByChatId(chatId);
            if (messages == null) {
                log.warn("尝试获取不存在的聊天 [{}] 的消息", chatId);
                return ResponseEntity.notFound().build();
            }
            log.info("成功获取聊天 [{}] 的消息，共 {} 条", chatId, messages.size());
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            log.error("获取聊天 [{}] 消息失败", chatId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /message
    @PostMapping(value = "/message", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chatStream(
            @RequestParam("prompt") String prompt,
            @RequestParam(value = "chatId", required = false) String chatId,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        // --- 步骤 0: 输入验证 ---
        if (!StringUtils.hasText(prompt) && CollectionUtils.isEmpty(files)) {
            log.warn("接收到无效请求：消息内容和附件均为空");
            SsePayload.ErrorPayload errorPayload = new SsePayload.ErrorPayload("消息内容和附件不能都为空");
            return Flux.just(createSseEvent("error", errorPayload));
        }

        // --- 步骤 1: 聊天 ID 管理 和 用户消息处理 ---
        final String effectiveChatId = determineChatId(prompt, chatId);
        final List<AttachmentDto> userAttachments = processFiles(files);
        storeUserMessage(effectiveChatId, prompt, userAttachments);

        // --- 步骤 2: 准备发送给 AI 的请求 ---
        BotChatCompletionRequest currentRequest;
        try {
            // *** 确认 buildBotChatCompletionRequest 设置了 botId ***
            currentRequest = buildBotChatCompletionRequest(effectiveChatId, prompt /*, userAttachments? */);
        } catch (Exception e) {
            log.error("为聊天 [{}] 构建 AI 请求时失败: {}", effectiveChatId, e.getMessage(), e);
            SsePayload.ErrorPayload errorPayload = new SsePayload.ErrorPayload("无法准备 AI 请求。");
            return Flux.just(createSseEvent("error", errorPayload));
        }


        // --- 步骤 3: 构建 SSE 事件流 ---
        try {
            // 事件流 1: 发送 Chat ID 事件
            Flux<ServerSentEvent<String>> chatIdEvent = Flux.just(
                    createSseEvent("chat_id", new SsePayload.ChatIdPayload(effectiveChatId))
            );

            // 事件流 2: 发送 "思考中" 步骤事件 (模拟)
            Flux<ServerSentEvent<String>> thinkingEvents = createThinkingStepsStream();

            // 事件流 3: AI 内容流事件
            AtomicReference<StringBuilder> aiContentBuilderRef = new AtomicReference<>(new StringBuilder());

            // *** 关键: 使用 RxJava2Adapter 将 RxJava 2 Flowable 转换为 Reactor Flux ***
            Flux<ServerSentEvent<String>> aiContentStream = RxJava2Adapter.flowableToFlux(
                            // *** 关键: 调用 streamBotChatCompletion 方法 ***
                            arkService.streamBotChatCompletion(currentRequest) // <--- 使用 Bot 相关方法
                                    .doOnError(err -> log.error("ArkService 流处理错误 (RxJava 层), ChatID {}: {}", effectiveChatId, err.getMessage(), err))
                    )
                    // 使用 concatMap 处理流中的每个块
                    .concatMap(chunk -> {
                        // 进行类型检查，确保是期望的响应类型
                        return processAiChunk(chunk, aiContentBuilderRef); // 调用辅助方法处理
                    })
                    // 流成功完成时记录日志
                    .doOnComplete(() -> log.info("AI 内容流处理完成, ChatID: {}", effectiveChatId));


            // 事件流 4: 最终消息事件 (延迟执行)
            Mono<ServerSentEvent<String>> finalMessageEvent = Mono.defer(() -> {
                log.info("AI 响应接收完毕, ChatID: {}. 准备发送最终消息.", effectiveChatId);
                String finalAiContent = aiContentBuilderRef.get().toString();
                ChatMessageDto aiMessage = createAndStoreFinalAiMessage(effectiveChatId, finalAiContent);
                return Mono.just(createSseEvent("final_message", aiMessage));
            });


            // --- 步骤 4: 组合所有事件流并添加全局错误处理 ---
            return Flux.concat(chatIdEvent, thinkingEvents, aiContentStream, finalMessageEvent)
                    .onErrorResume(error -> {
                        // 捕获流处理过程中的任何错误
                        log.error("处理聊天流时发生错误, ChatID: {}. 错误信息: {}", effectiveChatId, error.getMessage(), error);
                        SsePayload.ErrorPayload errorPayload = new SsePayload.ErrorPayload("处理您的请求时发生内部错误，请稍后再试。");
                        // 返回包含错误信息的 SSE 事件
                        return Flux.just(createSseEvent("error", errorPayload));
                    });

        } catch (Exception e) {
            // 捕获设置流时的同步异常
            log.error("设置 SSE 事件流时发生意外错误, ChatID: {}. 错误: {}", effectiveChatId, e.getMessage(), e);
            SsePayload.ErrorPayload errorPayload = new SsePayload.ErrorPayload("请求初始化失败。");
            return Flux.just(createSseEvent("error", errorPayload));
        }
    }

    // --- 辅助方法 ---

    // determineChatId(...) (保持不变)
    private String determineChatId(String prompt, String chatId) {
        if (StringUtils.hasText(chatId)) {
            log.info("继续现有聊天, ChatID: {}", chatId);
            return chatId;
        } else {
            String newChatId = mockDataStore.createNewChat(prompt);
            log.info("创建新聊天, ChatID: {}", newChatId);
            return newChatId;
        }
    }

    // processFiles(...) (保持不变)
    private List<AttachmentDto> processFiles(List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            return Collections.emptyList();
        }
        List<AttachmentDto> userAttachments = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                log.info("处理上传文件: 名称='{}', 类型='{}', 大小={} bytes", file.getOriginalFilename(), file.getContentType(), file.getSize());
                String fileId = "user-file-" + UUID.randomUUID();
                String mockUrl = "/api/files/mock/" + file.getOriginalFilename(); // 模拟 URL
                String fileType = determineFileType(file.getContentType());
                userAttachments.add(new AttachmentDto(fileId, fileType, file.getOriginalFilename(), mockUrl));
            }
        }
        return userAttachments;
    }

    // determineFileType(...) (保持不变)
    private String determineFileType(String contentType) {
        return contentType != null && contentType.startsWith("image/") ? "image" : "file";
    }

    // storeUserMessage(...) (保持不变)
    private void storeUserMessage(String chatId, String prompt, List<AttachmentDto> attachments) {
        long userTimestamp = Instant.now().toEpochMilli();
        ChatMessageDto userMessage = new ChatMessageDto("user-" + UUID.randomUUID(), "user", prompt, null, false, attachments.isEmpty() ? null : attachments, userTimestamp);
        mockDataStore.addMessage(chatId, userMessage);
        log.debug("已存储用户消息到聊天 [{}], 消息 ID: {}", chatId, userMessage.getId());
    }


    /**
     * 构建发送给火山引擎 ArkService 的 BotChatCompletionRequest 对象。
     * **关键**: 确保设置了 botId。
     *
     * @param chatId 当前聊天的 ID，用于获取历史记录
     * @param userPrompt 当前用户输入的文本
     * @return 配置好的 BotChatCompletionRequest 实例 (确保非空)
     * @throws IllegalStateException 如果 Bot ID 未配置
     */
    @NonNull
    private BotChatCompletionRequest buildBotChatCompletionRequest(String chatId, String userPrompt /*, List<AttachmentDto> attachments */) {
        // 1. 获取 Bot ID (从配置中读取，假设 model 属性存的是 Bot ID)
        String botId = arkConfigurationProperties.getModel(); // *** 确认这里获取的是 Bot ID ***
        if (!StringUtils.hasText(botId)) {
            log.error("火山引擎 Ark Bot ID 未在配置中设置 (ark.ai.model)");
            throw new IllegalStateException("Ark Bot ID 未配置");
        }

        // 2. 获取历史消息
        List<ChatMessageDto> historyDtos = mockDataStore.getMessagesByChatId(chatId);
        if (historyDtos == null) {
            historyDtos = new ArrayList<>();
            log.warn("未能获取到聊天 [{}] 的历史记录，将只发送当前消息", chatId);
        }
        log.info("为聊天 [{}] 获取到 {} 条历史记录用于构建请求", chatId, historyDtos.size() > 0 ? historyDtos.size() -1 : 0 );

        // 3. 转换历史消息
        List<ChatMessage> messages = new ArrayList<>();
        int historyLimit = 20; // 示例截断
        int startIndex = Math.max(0, historyDtos.size() - 1 - historyLimit);

        for (int i = startIndex; i < historyDtos.size() - 1; i++) {
            ChatMessageDto dto = historyDtos.get(i);
            ChatMessageRole role = mapRoleToArk(dto.getType());
            if (role != null && StringUtils.hasText(dto.getContent())) {
                messages.add(ChatMessage.builder().role(role).content(dto.getContent()).build());
            } else {
                log.warn("聊天 [{}] 中跳过无效的历史消息: ID={}, Role={}, ContentEmpty={}", chatId, dto.getId(), dto.getType(), !StringUtils.hasText(dto.getContent()));
            }
        }

        // 4. 添加当前用户的消息
        messages.add(ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build());


        // 5. 构建并返回 **新的** 请求对象实例
        return BotChatCompletionRequest.builder()
                .botId(botId) // *** 关键：设置 Bot ID ***
                .messages(messages)
                .stream(true) // 确保流式开启
                // .userId(...) // 可选
                // .temperature(...) // 可选
                .build();
    }

    // mapRoleToArk(...) (保持不变)
    private ChatMessageRole mapRoleToArk(String role) {
        if ("user".equalsIgnoreCase(role)) {
            return ChatMessageRole.USER;
        } else if ("ai".equalsIgnoreCase(role) || "assistant".equalsIgnoreCase(role)) {
            return ChatMessageRole.ASSISTANT;
        }
        log.warn("无法映射未知的角色类型: {}", role);
        return null;
    }

    // createThinkingStepsStream() (保持不变)
    private Flux<ServerSentEvent<String>> createThinkingStepsStream() {
        return Flux.concat(
                Mono.delay(Duration.ofMillis(100)).thenReturn(createSseEvent("thinking_step", new SsePayload.ThinkingStepPayload("🧠 正在分析您的问题..."))),
                Mono.delay(Duration.ofMillis(300)).thenReturn(createSseEvent("thinking_step", new SsePayload.ThinkingStepPayload("📚 检索相关林业知识..."))),
                Mono.delay(Duration.ofMillis(500)).thenReturn(createSseEvent("thinking_step", new SsePayload.ThinkingStepPayload("✍️ 组织语言生成回答...")))
        );
    }

    // processAiChunk(...) (保持不变, 参数类型为 ChatCompletionStreamResponse)
    private Mono<ServerSentEvent<String>> processAiChunk(BotChatCompletionChunk chunk, AtomicReference<StringBuilder> aiContentBuilderRef) {
        if (chunk == null || CollectionUtils.isEmpty(chunk.getChoices())) {
            log.warn("接收到无效或空的 AI chunk: {}", chunk);
            return Mono.empty();
        }
        ChatCompletionChoice choice = chunk.getChoices().get(0);
        if (choice == null || choice.getMessage() == null) {
            log.warn("接收到空的 choice 或 delta in AI chunk: {}", choice);
            return Mono.empty();
        }
        String contentDelta = (String) choice.getMessage().getContent();
        if (contentDelta != null) {
            if (!contentDelta.isEmpty()) {
                aiContentBuilderRef.get().append(contentDelta);
                log.trace("收到 AI 内容块: {}", contentDelta);
            }
            SsePayload.ContentChunkPayload payload = new SsePayload.ContentChunkPayload(contentDelta);
            return Mono.just(createSseEvent("content_chunk", payload));
        } else {
            if (choice.getFinishReason() != null) {
                log.debug("AI chunk finish reason: {}, ChatID 相关: {}", choice.getFinishReason(), chunk.getId());
            } else {
                log.warn("接收到 contentDelta 为 null 且无 finishReason 的 AI chunk: {}", chunk);
            }
            return Mono.empty();
        }
    }

    // createAndStoreFinalAiMessage(...) (保持不变)
    @NonNull
    private ChatMessageDto createAndStoreFinalAiMessage(String chatId, String finalAiContent) {
        long aiTimestamp = Instant.now().toEpochMilli();
        List<AttachmentDto> aiAttachments = null;
        ChatMessageDto aiMessage = new ChatMessageDto("ai-" + UUID.randomUUID(), "ai", finalAiContent, List.of("🧠 正在分析您的问题...", "📚 检索相关林业知识...", "✍️ 组织语言生成回答...", "✅ 生成回答完毕。"), false, aiAttachments, aiTimestamp);
        mockDataStore.addMessage(chatId, aiMessage);
        log.info("已存储最终 AI 消息到聊天 [{}], 消息 ID: {}", chatId, aiMessage.getId());
        return aiMessage;
    }

    // createSseEvent(...) (保持不变)
    @NonNull
    private <T> ServerSentEvent<String> createSseEvent(String eventName, T payload) {
        try {
            String jsonData = objectMapper.writeValueAsString(payload);
            return ServerSentEvent.<String>builder().id(UUID.randomUUID().toString()).event(eventName).data(jsonData).build();
        } catch (JsonProcessingException e) {
            log.error("序列化 SSE 事件数据失败! Event: '{}', Payload Type: '{}'. Error: {}", eventName, payload != null ? payload.getClass().getName() : "null", e.getMessage(), e);
            SsePayload.ErrorPayload errorPayload = new SsePayload.ErrorPayload("内部服务器错误：无法序列化事件数据");
            try {
                String errorJson = objectMapper.writeValueAsString(errorPayload);
                return ServerSentEvent.<String>builder().id(UUID.randomUUID().toString()).event("error").data(errorJson).build();
            } catch (JsonProcessingException ex) {
                log.error("无法序列化 SSE 错误回退事件!", ex);
                return ServerSentEvent.<String>builder().id(UUID.randomUUID().toString()).event("error").data("{\"message\":\"Internal Server Error: Serialization failed\"}").build();
            }
        }
    }

    // --- 内部静态类，定义 SSE 事件的负载结构 ---
    // 已移动到 com.weilanx.deepforest.chat.dto.SsePayload
}