package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 时间序列数据视图对象 (View Object)
 * 常用于折线图、面积图等表示趋势的数据
 * 与前端 analysis/types/index.tsx 中的 TimeSeriesData 对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSeriesDataVO {

    /**
     * 日期或时间点的标签列表 (例如: ["2024-01", "2024-02", ...])
     */
    private List<String> dates;

    /**
     * 与 dates 对应的数值列表 (例如: [850, 920, ...])
     */
    private List<Number> counts; // 使用 Number 以支持整数和浮点数
}