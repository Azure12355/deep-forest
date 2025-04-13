// src/main/java/com/weilanx/deepforest/analysis/mock/MockAnalysisDataStore.java
package com.weilanx.deepforest.analysis.mock;

import com.weilanx.deepforest.analysis.dto.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 模拟数据分析数据存储
 */
@Component
public class MockAnalysisDataStore {

    // 直接返回与前端 getMockData() 结构一致的数据

    public List<MetricDataDto> getMetrics() {
        return Arrays.asList(
                new MetricDataDto("species", "leaf", "物种总数", 1853, "已收录", null, null, "gradient-green"),
                new MetricDataDto("refs", "book-open", "参考文献总数", 12450, "本周新增", 150, null, "gradient-blue"),
                new MetricDataDto("qa", "comments", "今日问答量", 215, "近7日", 1488, null, "gradient-purple"),
                new MetricDataDto("storage", "database", "文件总大小", 88.5, "文件数", 4320, "GB", "gradient-orange")
        );
    }

    public List<NameValueDataDto> getSpeciesTaxonomy() {
        return Arrays.asList(
                new NameValueDataDto("昆虫纲 (Insecta)", 980),
                new NameValueDataDto("蛛形纲 (Arachnida)", 350),
                new NameValueDataDto("线虫门 (Nematoda)", 180),
                new NameValueDataDto("真菌界 (Fungi)", 150),
                new NameValueDataDto("其他", 93)
        );
    }

    public List<NameValueDataDto> getSpeciesStatus() {
        return Arrays.asList(
                new NameValueDataDto("已确认", 1500),
                new NameValueDataDto("待审核", 253),
                new NameValueDataDto("有疑问", 100)
        );
    }

    public TimeSeriesDataDto getSpeciesGrowth() {
        return new TimeSeriesDataDto(
                Arrays.asList("2024-01", "2024-02", "2024-03", "2024-04", "2024-05", "2024-06", "2024-07"),
                Arrays.asList(850, 920, 1100, 1350, 1580, 1750, 1853)
        );
    }

    public List<GeoDistributionDataDto> getGeoDistribution() {
        return Arrays.asList(
                new GeoDistributionDataDto("新疆", 85), new GeoDistributionDataDto("西藏", 30), new GeoDistributionDataDto("内蒙古", 120),
                new GeoDistributionDataDto("青海", 50), new GeoDistributionDataDto("四川", 230), new GeoDistributionDataDto("黑龙江", 160),
                new GeoDistributionDataDto("甘肃", 90), new GeoDistributionDataDto("云南", 280), new GeoDistributionDataDto("广西", 190),
                new GeoDistributionDataDto("湖南", 210), new GeoDistributionDataDto("陕西", 110), new GeoDistributionDataDto("广东", 260),
                new GeoDistributionDataDto("吉林", 140), new GeoDistributionDataDto("河北", 130), new GeoDistributionDataDto("湖北", 200),
                new GeoDistributionDataDto("贵州", 150), new GeoDistributionDataDto("山东", 170), new GeoDistributionDataDto("江西", 140),
                new GeoDistributionDataDto("河南", 150), new GeoDistributionDataDto("辽宁", 160), new GeoDistributionDataDto("山西", 100),
                new GeoDistributionDataDto("安徽", 170), new GeoDistributionDataDto("福建", 220), new GeoDistributionDataDto("浙江", 230),
                new GeoDistributionDataDto("江苏", 250), new GeoDistributionDataDto("重庆", 180), new GeoDistributionDataDto("宁夏", 60),
                new GeoDistributionDataDto("海南", 90), new GeoDistributionDataDto("台湾", 80), new GeoDistributionDataDto("北京", 40),
                new GeoDistributionDataDto("天津", 30), new GeoDistributionDataDto("上海", 50), new GeoDistributionDataDto("香港", 25),
                new GeoDistributionDataDto("澳门", 15)
        );
    }

    public TopHostDataDto getTopHosts() {
        return new TopHostDataDto(
                Arrays.asList("杨树 (Populus)", "松树 (Pinus)", "柳树 (Salix)", "苹果 (Malus)", "玉米 (Zea mays)"),
                Arrays.asList(350, 280, 190, 150, 120)
        );
    }

    public TimeSeriesDataDto getReferenceGrowth() {
        return new TimeSeriesDataDto(
                Arrays.asList("2024-01", "2024-02", "2024-03", "2024-04", "2024-05", "2024-06", "2024-07"),
                Arrays.asList(8000, 8500, 9200, 10100, 11000, 11800, 12450)
        );
    }

    public List<NameValueDataDto> getReferenceTypes() {
        return Arrays.asList(
                new NameValueDataDto("期刊文章", 4500),
                new NameValueDataDto("会议论文", 3200),
                new NameValueDataDto("书籍章节", 1800),
                new NameValueDataDto("技术报告", 1500),
                new NameValueDataDto("其他", 1450)
        );
    }

    public List<NameValueDataDto> getFileTypes() {
        return Arrays.asList(
                new NameValueDataDto("PDF", 2800),
                new NameValueDataDto("DOC/DOCX", 950),
                new NameValueDataDto("JPG/PNG", 450),
                new NameValueDataDto("其他", 120)
        );
    }
}