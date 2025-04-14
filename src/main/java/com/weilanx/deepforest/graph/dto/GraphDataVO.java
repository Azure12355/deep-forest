package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 知识图谱整体数据视图对象 (View Object)
 * 封装所有图谱相关数据，对应 API 响应体中的 'data' 字段
 * 对应前端 graph/types/index.ts 中的 GraphData
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphDataVO {
    /**
     * 节点列表
     */
    private List<GraphNodeVO> nodes;

    /**
     * 关系列表
     */
    private List<GraphLinkVO> links;

    /**
     * 节点分类定义列表
     */
    private List<GraphCategoryVO> categories;

    /**
     * 物种确认状态统计列表
     */
    private List<SpeciesStatusVO> speciesConfirmationStatus;
}