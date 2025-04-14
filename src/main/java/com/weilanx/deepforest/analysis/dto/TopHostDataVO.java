package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Top N 宿主数据视图对象 (View Object)
 * 常用于条形图，展示排名靠前的宿主及其关联数量
 * 与前端 analysis/types/index.tsx 中的 TopHostData 对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopHostDataVO {

    /**
     * 宿主名称列表 (例如: ["杨树 (Populus)", "松树 (Pinus)", ...])
     */
    private List<String> names;

    /**
     * 与 names 对应的关联物种数列表 (例如: [350, 280, ...])
     */
    private List<Number> counts; // 使用 Number
}