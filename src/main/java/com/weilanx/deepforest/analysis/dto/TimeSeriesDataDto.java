// src/main/java/com/weilanx/deepforest/analysis/dto/TimeSeriesDataDto.java
package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 时间序列数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeriesDataDto {
    private List<String> dates;
    private List<Number> counts; // 使用 Number 兼容整数和浮点数
}