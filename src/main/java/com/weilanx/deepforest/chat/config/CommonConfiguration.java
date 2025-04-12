package com.weilanx.deepforest.chat.config;

// import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest; // 移除或注释掉

import com.volcengine.ark.runtime.service.ArkService;
import com.weilanx.deepforest.chat.prompt.PromptConstant;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel; // 假设你确实需要OpenAI的配置
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ArkConfigurationProperties.class})
public class CommonConfiguration {

    // @Resource // ArkConfigurationProperties 可以在需要的地方直接注入，这里不需要字段
    // private ArkConfigurationProperties arkConfigurationProperties;

    // 保持 ChatClient Bean (如果你同时使用 Spring AI 的话)
    @Bean
    public ChatClient chatClient(OpenAiChatModel model) { // 确保 OpenAiChatModel Bean 存在
        return ChatClient.builder(model)
                .defaultSystem(PromptConstant.DEEP_FOREST_PROMPT) // 确保 PromptConstant.DEEP_FOREST_PROMPT 已定义
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    /**
     * 火山引擎 ArkService 客户端 Bean
     *
     * @param arkConfigurationProperties 自动注入配置属性
     * @return ArkService 实例
     */
    @Bean
    public ArkService arkService(ArkConfigurationProperties arkConfigurationProperties) {
        // 检查 apiKey 是否存在，增加健壮性
        if (arkConfigurationProperties.getApiKey() == null || arkConfigurationProperties.getApiKey().isEmpty()) {
            // 在实际应用中，可能需要更优雅的处理，例如启动时失败或提供默认值/替代方案
            throw new IllegalArgumentException("火山引擎 Ark API Key 未配置 (ark.ai.apiKey)");
        }
        return ArkService.builder()
                .apiKey(arkConfigurationProperties.getApiKey())
                // 如果需要设置 Base URL (通常不需要，SDK 会处理)
                 .baseUrl(arkConfigurationProperties.getBaseUrl())
                .build();
    }

    /***
     * 【移除或注释掉】智能体应用 chatbot 的 BotChatCompletionRequest Bean。
     * 原因：这是一个包含请求特定数据（如消息列表）的对象，不应作为单例 Bean。
     * 每个请求都应在 Controller 中动态创建自己的 Request 实例。
     */
    /*
    @Bean
    public BotChatCompletionRequest botChatCompletionRequest(ArkConfigurationProperties arkConfigurationProperties) {
        // 这个 Bean 不应该存在，或者最多作为一个只读的“模板”Bean，但不能直接用于发送请求
        return BotChatCompletionRequest.builder()
                .model(arkConfigurationProperties.getModel()) // model 可以从配置读取后在 Controller 中设置到新请求上
                // 不应在这里设置 messages 或 user 等请求特定信息
                .build();
    }
    */
}