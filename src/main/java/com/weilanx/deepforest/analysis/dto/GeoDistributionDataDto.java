package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理分布数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoDistributionDataDto {
    private String name;
    private Number value; // 使用 Number
}