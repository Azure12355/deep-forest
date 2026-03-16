// src/main/java/com/weilanx/deepforest/graph/dto/GraphCategoryDto.java
package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 图谱节点分类 DTO
 * 用于 ECharts 图例和节点颜色配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphCategoryDto {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 样式配置
     */
    private Map<String, Object> itemStyle;
}
