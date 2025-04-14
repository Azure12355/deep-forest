package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 知识图谱关系视图对象 (View Object)
 * 用于封装返回给前端的单个关系数据
 * 对应前端 graph/types/index.ts 中的 GraphLink
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphLinkVO {
    /**
     * 源节点 ID
     */
    private String source;

    /**
     * 目标节点 ID
     */
    private String target;

    /**
     * 关系的详细属性信息 (键值对形式)
     */
    private Map<String, Object> details;

    /**
     * 可选的关系线样式
     * 如果为 null，前端将使用默认样式
     */
    private LineStyleVO lineStyle;

    // 注意：后端 VO 通常不包含 ECharts 特定的 label 样式
}