package com.weilanx.deepforest.analysis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 数据分析仪表盘整体数据视图对象 (View Object)
 * 封装所有需要在仪表盘页面展示的数据，对应API响应体中的 'data' 字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDataVO {

    /**
     * 指标卡片数据列表
     */
    private List<MetricDataVO> metrics;

    /**
     * 物种分类层级分布数据 (饼图)
     */
    private List<NameValueDataVO> speciesTaxonomy;

    /**
     * 物种确认状态数据 (玫瑰图)
     */
    private List<NameValueDataVO> speciesStatus;

    /**
     * 物种收录增长趋势数据 (面积折线图)
     */
    private TimeSeriesDataVO speciesGrowth;

    /**
     * 物种地理分布数据 (地图)
     */
    private List<GeoDistributionDataVO> geoDistribution;

    /**
     * Top 5 寄主植物数据 (条形图)
     */
    private TopHostDataVO topHosts;

    /**
     * 参考文献增长趋势数据 (面积折线图)
     */
    private TimeSeriesDataVO referenceGrowth;

    /**
     * 参考文献类型数据 (环形图)
     */
    private List<NameValueDataVO> referenceTypes;

    /**
     * 关联文件类型数据 (环形图)
     */
    private List<NameValueDataVO> fileTypes;
}