package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理分布数据视图对象 (View Object)
 * 用于地图可视化，表示某个区域的数值
 * 与前端 analysis/types/index.tsx 中的 GeoDistributionData 对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoDistributionDataVO {

    /**
     * 地理区域名称 (例如: "新疆", "四川")
     */
    private String name;

    /**
     * 该区域对应的数值
     */
    private Number value; // 使用 Number
}