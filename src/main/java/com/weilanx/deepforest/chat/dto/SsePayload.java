package com.weilanx.deepforest.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SSE 事件的通用载荷结构 (用于发送 JSON 数据)
 * 这里定义几个具体的子类或直接使用 Map/Object 可能更灵活，
 * 但为了类型安全和文档清晰，可以为每个 event 定义 payload DTO。
 */
public class SsePayload {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL) // 忽略 null 字段
    public static class ChatIdPayload {
        private String chatId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ThinkingStepPayload {
        private String step;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContentChunkPayload {
        private String delta;
    }

    // final_message 使用 ChatMessageDto

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorPayload {
        private String message;
    }
}