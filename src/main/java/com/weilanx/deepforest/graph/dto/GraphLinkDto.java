// src/main/java/com/weilanx/deepforest/graph/dto/GraphLinkDto.java
package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 图谱关系（边）DTO
 * 表示两个节点之间的关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphLinkDto {
    /**
     * 源节点 ID
     */
    private String source;

    /**
     * 目标节点 ID
     */
    private String target;

    /**
     * 关系详细信息
     */
    private Map<String, Object> details;

    /**
     * 线条样式（可选）
     */
    private Map<String, Object> lineStyle;
}
