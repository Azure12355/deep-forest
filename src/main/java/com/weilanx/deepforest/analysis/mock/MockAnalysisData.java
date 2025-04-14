package com.weilanx.deepforest.analysis.mock;

import com.weilanx.deepforest.analysis.dto.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 提供数据分析仪表盘的 Mock（模拟）数据 - **大幅扩展版·**
 * 用于在数据库未接入或开发初期提供测试数据
 * 显著增加了数据量和时间跨度，模拟更复杂的场景。
 */
@Component
public class MockAnalysisData {

    private static final Random random = ThreadLocalRandom.current();
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * 生成完整的仪表盘模拟数据 (扩展版)
     *
     * @return DashboardDataVO 包含大量模拟数据的对象
     */
    public static DashboardDataVO generateMockDashboardData() {
        // 设定基础数据量级
        int baseSpeciesCount = 15870; // 提升基础物种数量
        int baseRefCount = 85600;     // 提升基础文献数量
        long baseQaCount = 5320;       // 提升基础问答量
        double baseStorageGB = 450.5;   // 提升基础存储大小 GB
        int baseFileCount = 38500;     // 提升基础文件数

        return new DashboardDataVO(generateMockMetrics(baseSpeciesCount, baseRefCount, baseQaCount, baseStorageGB, baseFileCount), generateMockSpeciesTaxonomy(baseSpeciesCount), generateMockSpeciesStatus(baseSpeciesCount), generateMockGrowthData("species", baseSpeciesCount, 36), // 模拟过去3年物种增长
                generateMockGeoDistribution(baseSpeciesCount), generateMockTopHosts(baseSpeciesCount, 10), // 获取 Top 10 寄主
                generateMockGrowthData("reference", baseRefCount, 36), // 模拟过去3年文献增长
                generateMockReferenceTypes(baseRefCount), generateMockFileTypes(baseFileCount));
    }

    // --- 各部分模拟数据生成方法 (扩展版) ---

    /**
     * 生成指标卡片数据 (扩展版)
     */
    private static List<MetricDataVO> generateMockMetrics(int speciesCount, int refCount, long qaCountToday, double storageGB, int fileCount) {
        // 模拟周期增长值
        int speciesWeeklyIncrease = random.nextInt(80) + 50; // 每周新增 50-130
        int refWeeklyIncrease = random.nextInt(300) + 200;  // 每周新增 200-500
        long qaLast7Days = qaCountToday * (random.nextInt(4) + 5); // 近7日是今日的 5-8 倍

        return Arrays.asList(new MetricDataVO("species", "leaf", "物种总数", speciesCount, null, "本周新增", speciesWeeklyIncrease, "gradient-green"), new MetricDataVO("refs", "book-open", "参考文献总数", refCount, null, "本周新增", refWeeklyIncrease, "gradient-blue"), new MetricDataVO("qa", "comments", "今日问答量", qaCountToday, null, "近7日", qaLast7Days, "gradient-purple"),
                // 使用 BigDecimal 处理 GB 存储，保留一位小数
                new MetricDataVO("storage", "database", "文件总大小", BigDecimal.valueOf(storageGB).setScale(1, RoundingMode.HALF_UP).doubleValue(), "GB", "文件数", fileCount, "gradient-orange"));
    }

    /**
     * 生成物种分类层级分布数据 (扩展版)
     */
    private static List<NameValueDataVO> generateMockSpeciesTaxonomy(int totalSpecies) {
        // 增加分类并调整比例，确保总数大致等于 totalSpecies
        int insecta = (int) (totalSpecies * 0.55);
        int arachnida = (int) (totalSpecies * 0.18);
        int nematoda = (int) (totalSpecies * 0.10);
        int fungi = (int) (totalSpecies * 0.08);
        int bacteria = (int) (totalSpecies * 0.04);
        int virus = (int) (totalSpecies * 0.02);
        // 计算剩余数量给 "其他"
        int others = totalSpecies - insecta - arachnida - nematoda - fungi - bacteria - virus;
        // 确保 "其他" 不为负数
        others = Math.max(0, others);

        return Arrays.asList(new NameValueDataVO("昆虫纲 (Insecta)", insecta), new NameValueDataVO("蛛形纲 (Arachnida)", arachnida), new NameValueDataVO("线虫门 (Nematoda)", nematoda), new NameValueDataVO("真菌界 (Fungi)", fungi), new NameValueDataVO("细菌域 (Bacteria)", bacteria), new NameValueDataVO("病毒界 (Virus)", virus), // 界可能不准确，但作为分类示意
                new NameValueDataVO("软体动物门 (Mollusca)", random.nextInt(totalSpecies / 100) + 10), // 少量软体
                new NameValueDataVO("其他", others));
    }

    /**
     * 生成物种确认状态数据 (扩展版)
     */
    private static List<NameValueDataVO> generateMockSpeciesStatus(int totalSpecies) {
        // 调整比例
        int confirmed = (int) (totalSpecies * 0.82);
        int pending = (int) (totalSpecies * 0.13);
        int questionable = totalSpecies - confirmed - pending;
        questionable = Math.max(0, questionable);

        return Arrays.asList(new NameValueDataVO("已确认", confirmed), new NameValueDataVO("待审核", pending), new NameValueDataVO("有疑问", questionable));
    }

    /**
     * 生成增长趋势数据 (通用方法，支持物种或文献，扩展时间跨度)
     *
     * @param type             "species" 或 "reference"
     * @param finalCount       最终数量
     * @param monthsToSimulate 模拟多少个月的数据
     * @return TimeSeriesDataVO
     */
    private static TimeSeriesDataVO generateMockGrowthData(String type, int finalCount, int monthsToSimulate) {
        List<String> dates = new ArrayList<>();
        List<Number> counts = new ArrayList<>();
        YearMonth currentMonth = YearMonth.now();
        double currentCount = finalCount;

        // 设定一个相对合理的初始比例和增长波动
        double initialRatio = 0.3 + random.nextDouble() * 0.2; // 初始占最终的 30%-50%
        double monthlyGrowthFactorMean = Math.pow((1.0 / initialRatio), 1.0 / monthsToSimulate); // 平均月增长因子
        double fluctuation = 0.005 + random.nextDouble() * 0.01; // 月增长率波动范围

        // 从最终月份倒推
        for (int i = 0; i < monthsToSimulate; i++) {
            dates.add(currentMonth.format(MONTH_FORMATTER));
            counts.add((int) currentCount);

            // 计算上个月的数量
            double randomFluctuation = 1.0 + (random.nextDouble() - 0.5) * 2 * fluctuation; // 引入随机波动
            double effectiveGrowthFactor = monthlyGrowthFactorMean * randomFluctuation;
            // 确保增长因子大于等于1（或非常接近1），避免负增长过多
            effectiveGrowthFactor = Math.max(1.001, effectiveGrowthFactor);
            currentCount = currentCount / effectiveGrowthFactor;
            currentMonth = currentMonth.minusMonths(1);
        }

        // 反转列表，使时间从过去到现在
        java.util.Collections.reverse(dates);
        java.util.Collections.reverse(counts);

        return new TimeSeriesDataVO(dates, counts);
    }


    /**
     * 生成地理分布数据 (扩展版，数值增加)
     */
    private static List<GeoDistributionDataVO> generateMockGeoDistribution(int totalSpecies) {
        // 基础比例，模拟非均匀分布
        double[] baseRatios = {0.05, 0.01, 0.07, 0.02, 0.13, 0.09, 0.04, 0.16, 0.11, 0.12, // 新疆-湖南
                0.06, 0.15, 0.08, 0.07, 0.11, 0.08, 0.10, 0.08, 0.09, 0.09, // 陕西-辽宁
                0.05, 0.10, 0.12, 0.13, 0.14, 0.10, 0.03, 0.05, 0.04, 0.02, // 山西-北京
                0.01, 0.02, 0.01, 0.01  // 天津-澳门
        };
        String[] provinceNames = {"新疆", "西藏", "内蒙古", "青海", "四川", "黑龙江", "甘肃", "云南", "广西", "湖南", "陕西", "广东", "吉林", "河北", "湖北", "贵州", "山东", "江西", "河南", "辽宁", "山西", "安徽", "福建", "浙江", "江苏", "重庆", "宁夏", "海南", "台湾", "北京", "天津", "上海", "香港", "澳门"};

        List<GeoDistributionDataVO> distribution = new ArrayList<>();
        for (int i = 0; i < provinceNames.length; i++) {
            // 根据总物种数和基础比例计算，并加入随机波动
            int value = (int) (totalSpecies * baseRatios[i] * (0.8 + random.nextDouble() * 0.4)); // 80%-120% 波动
            value = Math.max(5, value); // 保证最小值，避免0或过小的值
            distribution.add(new GeoDistributionDataVO(provinceNames[i], value));
        }
        return distribution;
    }

    /**
     * 生成 Top N 寄主数据 (扩展版)
     *
     * @param totalSpecies 总物种数，用于估算关联数量级
     * @param topN         需要多少个 Top 寄主
     * @return TopHostDataVO
     */
    private static TopHostDataVO generateMockTopHosts(int totalSpecies, int topN) {
        List<String> names = Arrays.asList("松属 (Pinus)", "杨属 (Populus)", "栎属 (Quercus)", "柳属 (Salix)", "桦木属 (Betula)", "榆属 (Ulmus)", "槭属 (Acer)", "苹果属 (Malus)", "李属 (Prunus)", "蔷薇属 (Rosa)", // 增加更多常见属
                "云杉属 (Picea)", "冷杉属 (Abies)", "玉米 (Zea mays)", "小麦 (Triticum aestivum)", "水稻 (Oryza sativa)" // 增加农作物作为潜在寄主
        );
        // 确保我们有足够的名称
        if (topN > names.size()) {
            topN = names.size();
        }

        List<String> topNames = new ArrayList<>();
        List<Number> topCounts = new ArrayList<>();

        // 模拟与物种总数相关的寄主关联数，并使其递减
        double baseCount = totalSpecies * (0.1 + random.nextDouble() * 0.1); // Top 1 关联 10%-20% 的物种
        for (int i = 0; i < topN; i++) {
            topNames.add(names.get(i));
            int count = (int) (baseCount * (1.0 - (double) i / topN) * (0.9 + random.nextDouble() * 0.2)); // 递减且有波动
            count = Math.max(totalSpecies / 100, count); // 保证一个最小值
            topCounts.add(count);
        }

        return new TopHostDataVO(topNames, topCounts);
    }

    /**
     * 生成参考文献类型分布数据 (扩展版)
     */
    private static List<NameValueDataVO> generateMockReferenceTypes(int totalRefs) {
        // 增加类型并调整比例
        int journal = (int) (totalRefs * 0.45);
        int conference = (int) (totalRefs * 0.20);
        int book = (int) (totalRefs * 0.12); // 书籍/章节
        int report = (int) (totalRefs * 0.08); // 技术报告
        int thesis = (int) (totalRefs * 0.05); // 学位论文
        int patent = (int) (totalRefs * 0.03); // 专利
        int website = (int) (totalRefs * 0.02); // 网站/网络资源
        int others = totalRefs - journal - conference - book - report - thesis - patent - website;
        others = Math.max(0, others);

        return Arrays.asList(new NameValueDataVO("期刊文章", journal), new NameValueDataVO("会议论文", conference), new NameValueDataVO("书籍/章节", book), new NameValueDataVO("技术报告", report), new NameValueDataVO("学位论文", thesis), new NameValueDataVO("专利文献", patent), new NameValueDataVO("网络资源", website), new NameValueDataVO("其他", others));
    }

    /**
     * 生成关联文件类型分布数据 (扩展版)
     */
    private static List<NameValueDataVO> generateMockFileTypes(int totalFiles) {
        // 调整比例并增加类型
        int pdf = (int) (totalFiles * 0.65);
        int doc = (int) (totalFiles * 0.15); // DOC/DOCX
        int image = (int) (totalFiles * 0.10); // JPG/PNG/GIF/TIFF etc.
        int excel = (int) (totalFiles * 0.04); // XLSX/CSV
        int text = (int) (totalFiles * 0.02); // TXT/XML/JSON
        int archive = (int) (totalFiles * 0.01); // ZIP/RAR
        int others = totalFiles - pdf - doc - image - excel - text - archive;
        others = Math.max(0, others);

        return Arrays.asList(new NameValueDataVO("PDF", pdf), new NameValueDataVO("DOC/DOCX", doc), new NameValueDataVO("图片文件", image), // 合并图片类型
                new NameValueDataVO("表格数据(XLSX/CSV)", excel), new NameValueDataVO("文本文件(TXT/XML)", text), new NameValueDataVO("压缩包(ZIP/RAR)", archive), new NameValueDataVO("其他", others));
    }
}