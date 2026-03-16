// src/main/java/com/weilanx/deepforest/graph/dto/GraphDataDto.java
package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 完整的图谱数据 DTO
 * 包含节点、关系、分类和物种状态统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphDataDto {
    /**
     * 节点列表
     */
    private List<GraphNodeDto> nodes;

    /**
     * 关系列表
     */
    private List<GraphLinkDto> links;

    /**
     * 分类列表（用于图例和颜色）
     */
    private List<GraphCategoryDto> categories;

    /**
     * 物种确认状态统计
     */
    private List<SpeciesStatusDto> speciesConfirmationStatus;
}
