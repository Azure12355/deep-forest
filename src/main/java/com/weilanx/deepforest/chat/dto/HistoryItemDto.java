package com.weilanx.deepforest.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天历史项数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryItemDto {
    private String id;
    private String title;
}