package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 节点或边的样式视图对象 (可选)
 * 主要用于定义 Category 的颜色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemStyleVO {
    /**
     * 颜色值 (例如 '#5dade2')
     */
    private String color;
    // 可以添加其他 ECharts 支持的 itemStyle 属性，如 borderColor, borderWidth 等
}