package com.weilanx.deepforest.analysis.mock;

import com.weilanx.deepforest.analysis.dto.*;
import org.springframework.stereotype.Component; // 使用 Component 使其可被注入，如果需要的话

import java.util.Arrays;
import java.util.List;

/**
 * 提供数据分析仪表盘的 Mock（模拟）数据
 * 用于在数据库未接入或开发初期提供测试数据
 */
@Component // 标记为 Spring 组件，方便后续注入 Service 或直接使用
public class MockAnalysisData {

    /**
     * 生成完整的仪表盘模拟数据
     * @return DashboardDataVO 包含所有模拟数据的对象
     */
    public static DashboardDataVO generateMockDashboardData() {
        return new DashboardDataVO(
                generateMockMetrics(),
                generateMockSpeciesTaxonomy(),
                generateMockSpeciesStatus(),
                generateMockSpeciesGrowth(),
                generateMockGeoDistribution(),
                generateMockTopHosts(),
                generateMockReferenceGrowth(),
                generateMockReferenceTypes(),
                generateMockFileTypes()
        );
    }

    // --- 各部分模拟数据生成方法 ---

    private static List<MetricDataVO> generateMockMetrics() {
        return Arrays.asList(
                new MetricDataVO("species", "leaf", "物种总数", 1853, null, "已收录", null, "gradient-green"),
                new MetricDataVO("refs", "book-open", "参考文献总数", 12450, null, "本周新增", 150, "gradient-blue"),
                new MetricDataVO("qa", "comments", "今日问答量", 215, null, "近7日", 1488, "gradient-purple"),
                new MetricDataVO("storage", "database", "文件总大小", 88.5, "GB", "文件数", 4320, "gradient-orange")
        );
    }

    private static List<NameValueDataVO> generateMockSpeciesTaxonomy() {
        return Arrays.asList(
                new NameValueDataVO("昆虫纲 (Insecta)", 980),
                new NameValueDataVO("蛛形纲 (Arachnida)", 350),
                new NameValueDataVO("线虫门 (Nematoda)", 180),
                new NameValueDataVO("真菌界 (Fungi)", 150),
                new NameValueDataVO("其他", 93)
        );
    }

    private static List<NameValueDataVO> generateMockSpeciesStatus() {
        return Arrays.asList(
                new NameValueDataVO("已确认", 1500),
                new NameValueDataVO("待审核", 253),
                new NameValueDataVO("有疑问", 100)
        );
    }

    private static TimeSeriesDataVO generateMockSpeciesGrowth() {
        List<String> dates = Arrays.asList("2024-01", "2024-02", "2024-03", "2024-04", "2024-05", "2024-06", "2024-07");
        List<Number> counts = Arrays.asList(850, 920, 1100, 1350, 1580, 1750, 1853);
        return new TimeSeriesDataVO(dates, counts);
    }

    private static List<GeoDistributionDataVO> generateMockGeoDistribution() {
        return Arrays.asList(
                new GeoDistributionDataVO("新疆", 85), new GeoDistributionDataVO("西藏", 30), new GeoDistributionDataVO("内蒙古", 120),
                new GeoDistributionDataVO("青海", 50), new GeoDistributionDataVO("四川", 230), new GeoDistributionDataVO("黑龙江", 160),
                new GeoDistributionDataVO("甘肃", 90), new GeoDistributionDataVO("云南", 280), new GeoDistributionDataVO("广西", 190),
                new GeoDistributionDataVO("湖南", 210), new GeoDistributionDataVO("陕西", 110), new GeoDistributionDataVO("广东", 260),
                new GeoDistributionDataVO("吉林", 140), new GeoDistributionDataVO("河北", 130), new GeoDistributionDataVO("湖北", 200),
                new GeoDistributionDataVO("贵州", 150), new GeoDistributionDataVO("山东", 170), new GeoDistributionDataVO("江西", 140),
                new GeoDistributionDataVO("河南", 150), new GeoDistributionDataVO("辽宁", 160), new GeoDistributionDataVO("山西", 100),
                new GeoDistributionDataVO("安徽", 170), new GeoDistributionDataVO("福建", 220), new GeoDistributionDataVO("浙江", 230),
                new GeoDistributionDataVO("江苏", 250), new GeoDistributionDataVO("重庆", 180), new GeoDistributionDataVO("宁夏", 60),
                new GeoDistributionDataVO("海南", 90), new GeoDistributionDataVO("台湾", 80), new GeoDistributionDataVO("北京", 40),
                new GeoDistributionDataVO("天津", 30), new GeoDistributionDataVO("上海", 50), new GeoDistributionDataVO("香港", 25),
                new GeoDistributionDataVO("澳门", 15)
                // 注意：确保这里的省份名称与你的 china.geojson 文件中的名称一致
        );
    }

    private static TopHostDataVO generateMockTopHosts() {
        List<String> names = Arrays.asList("杨树 (Populus)", "松树 (Pinus)", "柳树 (Salix)", "苹果 (Malus)", "玉米 (Zea mays)");
        List<Number> counts = Arrays.asList(350, 280, 190, 150, 120);
        return new TopHostDataVO(names, counts);
    }

    private static TimeSeriesDataVO generateMockReferenceGrowth() {
        List<String> dates = Arrays.asList("2024-01", "2024-02", "2024-03", "2024-04", "2024-05", "2024-06", "2024-07");
        List<Number> counts = Arrays.asList(8000, 8500, 9200, 10100, 11000, 11800, 12450);
        return new TimeSeriesDataVO(dates, counts);
    }

    private static List<NameValueDataVO> generateMockReferenceTypes() {
        return Arrays.asList(
                new NameValueDataVO("期刊文章", 4500),
                new NameValueDataVO("会议论文", 3200),
                new NameValueDataVO("书籍章节", 1800),
                new NameValueDataVO("技术报告", 1500),
                new NameValueDataVO("其他", 1450)
        );
    }

    private static List<NameValueDataVO> generateMockFileTypes() {
        return Arrays.asList(
                new NameValueDataVO("PDF", 2800),
                new NameValueDataVO("DOC/DOCX", 950),
                new NameValueDataVO("JPG/PNG", 450),
                new NameValueDataVO("其他", 120)
        );
    }
}