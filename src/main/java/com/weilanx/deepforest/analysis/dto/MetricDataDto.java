package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指标卡片数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricDataDto {
    private String id;
    private String icon;
    private String label;
    private Object value; // 使用 Object 接收 String 或 Number
    private String periodLabel;
    private Object periodValue; // 使用 Object
    private String unit;
    private String gradientClass;
}