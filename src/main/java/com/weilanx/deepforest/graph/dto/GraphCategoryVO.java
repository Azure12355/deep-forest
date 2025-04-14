package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 知识图谱节点分类视图对象 (View Object)
 * 用于定义节点的类别及其显示样式（如图例颜色）
 * 对应前端 graph/types/index.ts 中的 GraphCategory
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphCategoryVO {
    /**
     * 分类名称 (例如 "Species", "Reference")
     */
    private String name;

    /**
     * 可选的分类样式，主要用于定义颜色
     */
    private ItemStyleVO itemStyle;
}