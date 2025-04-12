package com.weilanx.deepforest.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 聊天消息数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String id;
    private String type; // "user" 或 "ai"
    private String content;
    private List<String> thinkingSteps;
    private Boolean isThinking; // 通常在 final_message 中为 false
    private List<AttachmentDto> attachments;
    private Long timestamp; // 毫秒时间戳
}