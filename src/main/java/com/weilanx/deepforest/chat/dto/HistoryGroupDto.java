package com.weilanx.deepforest.chat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 聊天历史分组数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryGroupDto {
    private String timeframe;
    private List<HistoryItemDto> items;
}