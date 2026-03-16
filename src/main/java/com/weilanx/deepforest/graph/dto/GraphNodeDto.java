// src/main/java/com/weilanx/deepforest/graph/dto/GraphNodeDto.java
package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 图谱节点 DTO
 * 表示知识图谱中的一个实体节点
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphNodeDto {
    /**
     * 节点唯一标识
     */
    private String id;

    /**
     * 节点名称（显示标签）
     */
    private String name;

    /**
     * 节点大小值（用于可视化）
     */
    private Integer value;

    /**
     * 节点分类索引（对应 categories 数组）
     */
    private Integer category;

    /**
     * 节点详细信息
     */
    private Map<String, Object> details;

    /**
     * 节点符号大小（可选）
     */
    private Integer symbolSize;

    /**
     * 节点样式（可选）
     */
    private Map<String, Object> itemStyle;

    /**
     * 标签配置（可选）
     */
    private Map<String, Object> label;
}
