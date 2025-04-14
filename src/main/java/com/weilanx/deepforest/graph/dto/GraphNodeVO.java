package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 知识图谱节点视图对象 (View Object)
 * 用于封装返回给前端的单个节点数据
 * 对应前端 graph/types/index.ts 中的 GraphNode
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphNodeVO {
    /**
     * 节点唯一 ID
     */
    private String id;

    /**
     * 节点名称 (可能包含前端用于换行的 \n)
     */
    private String name;

    /**
     * 节点的值，可用于计算 ECharts 中的 symbolSize
     * 可以是度数、权重等
     */
    private Number value; // 使用 Number 支持整数或浮点数

    /**
     * 节点所属类别的索引 (对应 categories 列表的下标)
     */
    private int category;

    /**
     * 节点的详细属性信息 (键值对形式)
     * 使用 Map<String, Object> 以支持任意类型的属性值
     */
    private Map<String, Object> details;

    // 注意：后端 VO 通常不包含 ECharts 特定的样式属性如 symbolSize, itemStyle, label
    // 这些通常由前端根据数据动态计算或在 ECharts option 中统一配置
}