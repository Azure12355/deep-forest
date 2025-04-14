package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物种确认状态统计视图对象 (View Object)
 * 用于封装饼图所需的数据项
 * 对应前端 graph/types/index.ts 中的 SpeciesStatus
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesStatusVO {
    /**
     * 状态名称 (例如 "已确认", "待审核")
     */
    private String name;

    /**
     * 该状态下的物种数量
     */
    private Number value;
}