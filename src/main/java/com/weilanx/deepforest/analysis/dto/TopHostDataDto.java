package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Top N 宿主数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopHostDataDto {
    private List<String> names;
    private List<Number> counts; // 使用 Number
}