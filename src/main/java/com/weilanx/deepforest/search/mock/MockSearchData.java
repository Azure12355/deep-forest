package com.weilanx.deepforest.search.mock;

import com.weilanx.deepforest.search.dto.*;
// 移除 Faker 导入
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 提供搜索和详情页的 Mock 数据 (无 Faker 扩展版)
 * 使用手动组合和预定义列表生成更真实的数据。
 * (已修复 ClassCastException)
 */
@Component
public class MockSearchData {

    // 移除 Faker: private static final Faker faker = new Faker(new Locale("zh-CN"));
    private static final List<SearchResultItemVO> allSearchResults = new ArrayList<>();
    private static final Map<String, SpeciesDetailVO> speciesDetails = new ConcurrentHashMap<>();
    private static final Random random = ThreadLocalRandom.current();
    private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // --- 预定义数据列表 ---
    private static final String[] SPECIES_PREFIXES = {"松", "杨", "柳", "栎", "桦", "杉", "桉", "苹果", "梨", "桃", "柑橘", "核桃", "板栗", "泡桐", "槐", "榆", "水杉", "银杏", "油橄榄", "橡胶"};
    private static final String[] PEST_SUFFIXES = {"天牛", "小蠹", "尺蠖", "舟蛾", "卷叶蛾", "蚜虫", "线虫", "象甲", "叶蜂", "粉虱", "木虱", "吉丁", "螟", "瘿蜂", "蚧", "螨"};
    private static final String[] DISEASE_SUFFIXES = {"枯萎病", "溃疡病", "炭疽病", "白粉病", "锈病", "根腐病", "叶斑病", "黄化病", "花叶病毒病", "丛枝病", "膏药病", "煤污病"};
    private static final String[] DOC_PREFIXES = {"关于", "探讨", "研究", "分析", "防治", "监测", "生物学特性", "综合治理", "风险评估", "可持续经营", "生态影响", "遗传多样性", "快速诊断"};
    private static final String[] DOC_SUFFIXES = {"研究", "报告", "论文集", "手册", "策略", "技术规程", "应用", "进展", "探讨", "分析"};
    private static final String[] AUTHORS = {"张伟", "李静", "王磊", "刘洋", "陈勇", "赵敏", "吴昊", "周强", "郑涛", "冯娟", "中国林科院森环森保所", "南京林业大学", "北京林业大学", "东北林业大学", "华南农业大学", "国际林业研究组织"};
    private static final String[] CLASSIFICATIONS = {"昆虫纲", "线虫动物门", "真菌界", "细菌域", "病毒界", "蛛形纲", "软体动物门", "植物界病害"};
    private static final String[] STATUSES = {"已确认", "待审核", "有疑问", "部分确认"};
    private static final String[] STATUS_TYPES = {"confirmed", "pending", "default", "partial"};
    private static final String[] TAGS_SPECIES = {"食叶性", "蛀干性", "刺吸性", "传病媒介", "病原体", "检疫性", "入侵物种", "主要害虫", "次要害虫", "根部危害", "果实危害", "林业害虫", "农业害虫"};
    private static final List<String> TAGS_DOCUMENT_LIST = Arrays.asList("综述", "研究报告", "会议论文", "学位论文", "专著", "技术手册", "防治方案", "监测报告", "风险分析", "生态学", "分子生物学", "遗传育种", "遥感应用"); // 改为 List
    private static final String[] HOSTS = {"松属", "杨属", "柳属", "栎属", "苹果属", "榆属", "桦木属", "杉木属", "桉属", "柑橘属", "核桃属", "栗属", "橡胶树属", "油橄榄属", "多种阔叶树", "多种针叶树"};
    private static final String[] LOCATIONS = {"亚洲", "欧洲", "北美", "南美", "非洲", "大洋洲", "中国", "日本", "韩国", "美国", "加拿大", "巴西", "澳大利亚", "德国", "法国", "西班牙", "葡萄牙", "江苏", "辽宁", "广东", "云南", "四川", "黑龙江", "山东", "福建", "广西", "浙江"};
    private static final String[] REF_SOURCES = {"林业科学", "昆虫学报", "植物保护学报", "生态学杂志", "中国森林病虫", "林业科技通讯", "农业科学", "Nature", "Science", "PNAS", "中国林业出版社", "科学出版社", "Springer", "Elsevier"};
    private static final String[] IMG_TYPES = {"adult", "larva", "pupa", "egg", "symptom", "damage", "habitat", "micrograph", "trap", "control_measure"};
    private static final String[] OTHER_NAME_TYPES = {"异名(Synonym)", "曾用名(Old Name)", "地方名(Local Name)", "英文俗名(Eng Common)"};


    // 初始化模拟数据
    static {
        // 静态初始化块中的方法调用是 ExceptionInInitializerError 的来源
        try {
            generateMockSearchResults(1000); // 生成 1000 条模拟搜索结果
            generateMockSpeciesDetails();   // 为部分结果生成详情
        } catch (Exception e) {
            // 在静态初始化块中捕获异常并打印，有助于调试
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("!!! Error during static initialization of MockSearchData !!!");
            e.printStackTrace();
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            // 抛出原始异常，让 Spring Boot 知道初始化失败
            throw new RuntimeException("Failed to initialize MockSearchData", e); // 包装成 RuntimeException
        }
    }

    /**
     * 生成指定数量的模拟搜索结果条目 (无 Faker)
     */
    private static void generateMockSearchResults(int count) {
        allSearchResults.clear(); // 清空旧数据
        Set<String> generatedIds = new HashSet<>(); // 确保 ID 唯一

        for (int i = 1; i <= count; i++) {
            boolean isSpecies = random.nextDouble() < 0.7; // 70% 概率是物种
            SearchResultItemVO.SearchResultItemVOBuilder builder = SearchResultItemVO.builder();

            String idBase = (isSpecies ? "sp" : "doc") + "-" + String.format("%05d", i); // 基于序号生成基础 ID
            String id = idBase + "-" + UUID.randomUUID().toString().substring(0, 4); // 添加随机后缀保证唯一性
            while(generatedIds.contains(id)) { // 确保 ID 唯一
                id = idBase + "-" + UUID.randomUUID().toString().substring(0, 4);
            }
            generatedIds.add(id);

            builder.id(id);
            builder.type(isSpecies ? "species" : "document");

            List<String> currentTags = new ArrayList<>();
            String title;
            if (isSpecies) {
                String prefix = randomElement(SPECIES_PREFIXES);
                String suffix = random.nextBoolean() ? randomElement(PEST_SUFFIXES) : randomElement(DISEASE_SUFFIXES);
                title = prefix + suffix;
                String sciNamePart1 = capitalizeFirst(randomElement(PEST_SUFFIXES).toLowerCase()); // 用害虫/病害名模拟属名或种加词
                String sciNamePart2 = randomElement(DISEASE_SUFFIXES).toLowerCase();
                String scientificName = sciNamePart1 + " " + sciNamePart2;
                String classification = randomElement(CLASSIFICATIONS);
                int statusIndex = random.nextInt(STATUSES.length);

                builder.icon("faLeaf") // 或根据分类细化
                        .title(title)
                        .scientificName(scientificName)
                        .classification(classification)
                        .status(STATUSES[statusIndex])
                        .statusType(STATUS_TYPES[statusIndex])
                        .description(generateRealisticDescription(title, classification, true))
                        .detailLink("/search/detail/" + id); // 详情链接指向物种详情

                // 添加物种标签
                int tagCount = random.nextInt(3) + 1; // 1-3 个标签
                Set<String> speciesTags = new HashSet<>();
                speciesTags.add("寄主: " + randomElement(HOSTS));
                speciesTags.add("分布: " + randomElement(LOCATIONS));
                for(int j=0; j < tagCount; j++) {
                    speciesTags.add(randomElement(TAGS_SPECIES));
                }
                currentTags.addAll(speciesTags);

            } else {
                String prefix = randomElement(DOC_PREFIXES);
                String topic = randomElement(SPECIES_PREFIXES) + (random.nextBoolean() ? randomElement(PEST_SUFFIXES) : randomElement(DISEASE_SUFFIXES));
                title = prefix + topic + randomElement(DOC_SUFFIXES);
                String author = generateAuthors();

                builder.icon("faBookOpen")
                        .title(title)
                        .author(author)
                        .description(generateRealisticDescription(title, author, false))
                        .detailLink("/literature/" + id); // 假设文献链接

                // 添加文献标签
                int tagCount = random.nextInt(3) + 2; // 2-4 个标签
                Set<String> docTags = new HashSet<>();
                docTags.add("关键词: " + topic);
                for(int j=0; j < tagCount; j++) {
                    docTags.add(randomElement(TAGS_DOCUMENT_LIST)); // 使用 List
                }
                currentTags.addAll(docTags);
            }
            builder.tags(currentTags);
            allSearchResults.add(builder.build());
        }
        System.out.println("Generated " + allSearchResults.size() + " mock search results.");
    }

    /**
     * 为部分物种生成详细信息 (无 Faker - **已修复 ClassCastException**)
     */
    private static void generateMockSpeciesDetails() {
        speciesDetails.clear(); // 清空旧数据
        // 为搜索结果中前 50 个物种生成详情
        List<String> idsToDetail = allSearchResults.stream()
                .filter(item -> "species".equals(item.getType()))
                .map(SearchResultItemVO::getId)
                .limit(50) // *** 增加生成详情的数量 ***
                .collect(Collectors.toList());

        // --- 手动添加几个关键物种的详细数据 ---
        addDetailedSpeciesPWN();
        addDetailedSpeciesAWM();
        addDetailedSpeciesRIFA();

        // --- 为其余 ID 生成半随机的详细数据 ---
        for (String id : idsToDetail) {
            if (speciesDetails.containsKey(id)) continue; // 跳过已手动添加的

            SearchResultItemVO summary = allSearchResults.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
            if (summary == null) continue;

            SpeciesDetailVO.SpeciesDetailVOBuilder detailBuilder = SpeciesDetailVO.builder();

            detailBuilder.id(id)
                    .chineseName(summary.getTitle())
                    .scientificName(summary.getScientificName())
                    .authorship(randomElement(AUTHORS).split(",")[0] + ", " + (1900 + random.nextInt(125))) // 简化命名人
                    .status(summary.getStatusType()) // 使用 statusType 作为内部状态标识
                    .statusText(summary.getStatus()) // 显示文本
                    .description(summary.getDescription() + " " + generateParagraphs(1, 3)) // 1-3段描述
                    .sources(randomElement(REF_SOURCES) + ", " + randomElement(AUTHORS).split(" ")[0] + "等");

            // --- 模拟各部分详情 (使用预定义数据和模板) ---
            detailBuilder.biology(SpeciesDetailVO.BiologyInfoVO.builder()
                    .properties("生活史包括" + generateStages() + "。 " + generateParagraphs(1, 2))
                    .stages(generateStages())
                    .visibility(random.nextBoolean() ? "肉眼可见" : "需借助工具")
                    .build());

            detailBuilder.morphology(SpeciesDetailVO.MorphologyInfoVO.builder()
                    .characteristics(generateParagraphs(1, 2))
                    .detectionMethods(generateList(2, 4, "检测方法"))
                    .build());

            // 模拟分布
            List<SpeciesDetailVO.DistributionAreaVO> areas = new ArrayList<>();
            int regionCount = random.nextInt(2) + 1; // 1-2 个大区
            Set<String> usedLocations = new HashSet<>();
            for(int i=0; i<regionCount; i++) {
                List<String> locs = new ArrayList<>();
                int locCount = random.nextInt(4) + 2; // 2-5 个地点
                for(int j=0; j<locCount; j++) {
                    String loc = randomElement(LOCATIONS);
                    if(usedLocations.add(loc)) locs.add(loc); // 避免重复
                }
                if (!locs.isEmpty()) {
                    areas.add(new SpeciesDetailVO.DistributionAreaVO(randomElement(LOCATIONS), locs)); // 区域名也随机选
                }
            }
            detailBuilder.distribution(SpeciesDetailVO.DistributionVO.builder()
                    .description("主要分布于" + areas.stream().map(SpeciesDetailVO.DistributionAreaVO::getRegion).collect(Collectors.joining(", ")) + "等地。")
                    .areas(areas)
                    .statusDescription(randomElement(Arrays.asList("广泛分布", "局部发生", "呈扩散趋势", "需重点监测")))
                    .build());

            // 模拟寄主
            List<SpeciesDetailVO.HostItemVO> hosts = new ArrayList<>();
            int hostCount = random.nextInt(3) + 1; // 1-3 个寄主
            String[] hostTypes = {"primary", "secondary", "occasional"};
            Set<String> usedHosts = new HashSet<>();
            for(int i=0; i<hostCount; i++) {
                String hostName = randomElement(HOSTS);
                if(usedHosts.add(hostName)) {
                    hosts.add(SpeciesDetailVO.HostItemVO.builder()
                            .name(hostName)
                            .scientificName(capitalizeFirst(hostName.split(" ")[0]) + " spp.") // 模拟学名
                            .type(randomElement(hostTypes))
                            .category("自然寄主")
                            .build());
                }
            }
            detailBuilder.host(SpeciesDetailVO.HostInfoVO.builder()
                    .rangeDescription("寄主范围" + (random.nextBoolean() ? "广泛" : "较专一") + "。")
                    .hosts(hosts)
                    .affectedParts(String.join(", ", generateSet(1, 3, Arrays.asList("叶片", "枝干", "根部", "果实", "花"))))
                    .intensity(randomElement(Arrays.asList("高", "中", "低", "局部严重")))
                    .build());

            // 模拟传播
            List<SpeciesDetailVO.MediumInfoVO> mediums = new ArrayList<>();
            int mediumCount = random.nextInt(2) + 1; // 1-2 个媒介
            String[] mediumTypes = {"Vector", "HumanActivity", "Wind", "Water"};
            Set<String> usedMediums = new HashSet<>();
            for(int i=0; i<mediumCount; i++) {
                String mediumName = randomElement(Arrays.asList("气流", "雨水", "人为调运", "昆虫媒介", "鸟类"));
                if(usedMediums.add(mediumName)) {
                    mediums.add(SpeciesDetailVO.MediumInfoVO.builder()
                            .name(mediumName)
                            .type(randomElement(mediumTypes))
                            .method(randomElement(Arrays.asList("随风扩散", "雨水溅播", "接触传播", "昆虫携带", "苗木调运")))
                            .build());
                }
            }
            detailBuilder.transmission(SpeciesDetailVO.TransmissionInfoVO.builder()
                    .mediums(mediums)
                    .pathwayDescription(generateParagraphs(1, 1))
                    .ecoImpact(generateParagraphs(1, 1))
                    .build());

            // 模拟防治
            detailBuilder.management(SpeciesDetailVO.ManagementInfoVO.builder()
                    .summary("采取综合防治措施，预防为主。")
                    .methods(generateList(2, 5, "防治方法"))
                    .remark("注意保护天敌。")
                    .build());

            // 模拟文献
            List<SpeciesDetailVO.ReferenceInfoVO> refs = new ArrayList<>();
            int refCount = random.nextInt(4)+1; // 1-4篇文献
            Set<String> usedRefTitles = new HashSet<>();
            for(int i=0; i<refCount; i++) {
                String refTitle = randomElement(DOC_PREFIXES) + summary.getTitle() + randomElement(DOC_SUFFIXES);
                if(usedRefTitles.add(refTitle)) {
                    // *** 修复 ClassCastException 的地方 ***
                    // 将 generateSet 返回的 Set<String> 转换为 List<String>
                    List<String> currentRefTags = new ArrayList<>(generateSet(1, 3, TAGS_DOCUMENT_LIST)); // 使用 List

                    refs.add(SpeciesDetailVO.ReferenceInfoVO.builder()
                            .id("ref-" + UUID.randomUUID().toString().substring(0,4))
                            .title(refTitle)
                            .authors(generateAuthors())
                            .source(randomElement(REF_SOURCES))
                            .year(1990 + random.nextInt(35))
                            .tags(currentRefTags) // **使用转换后的 List**
                            .doi("10."+random.nextInt(9999)+"/"+random.nextInt(99999))
                            .link(random.nextBoolean() ? "http://example.com/doc" + i : null)
                            .build());
                }
            }
            detailBuilder.references(refs); // 设置文献列表


            // 模拟分类
            detailBuilder.taxonomy(generateTaxonomy(summary.getClassification())); // 分类

            // 模拟图片
            detailBuilder.images(generateImages(summary.getTitle(), random.nextInt(4)+1)); // 图片

            // 模拟其他名称
            List<SpeciesDetailVO.OtherNameVO> otherNames = new ArrayList<>();
            int otherNameCount = random.nextInt(2); // 0-1 个其他名称
            Set<String> usedOtherNames = new HashSet<>();
            for(int i=0; i<otherNameCount; i++) {
                String otherName = capitalizeFirst(randomElement(PEST_SUFFIXES)) + " " + randomElement(SPECIES_PREFIXES);
                if(usedOtherNames.add(otherName)) {
                    otherNames.add(SpeciesDetailVO.OtherNameVO.builder()
                            .type(randomElement(OTHER_NAME_TYPES))
                            .name(otherName)
                            .year(String.valueOf(1950 + random.nextInt(70)))
                            .build());
                }
            }
            detailBuilder.otherNames(otherNames);

            // 模拟元数据
            detailBuilder.metadata(generateMetadata()); // 元数据


            speciesDetails.put(id, detailBuilder.build());
        }
        System.out.println("Generated/Updated details for " + speciesDetails.size() + " species.");
    }

    // --- 手动添加关键物种详情的方法 (保持不变) ---
    private static void addDetailedSpeciesPWN() {
        String id = "sp1-pwn";
        if (speciesDetails.containsKey(id)) return;

        // 手动创建一个对应的 SearchResultItemVO，如果 allSearchResults 中没有的话
        if (allSearchResults.stream().noneMatch(item -> item.getId().equals(id))) {
            allSearchResults.add(SearchResultItemVO.builder()
                    .id(id).type("species").icon("faLeaf").title("松材线虫")
                    .scientificName("Bursaphelenchus xylophilus").classification("线虫动物门")
                    .status("已确认").statusType("confirmed")
                    .description("一种毁灭性的植物寄生线虫...")
                    .tags(Arrays.asList("寄主: 松属", "分布: 亚洲"))
                    .detailLink("/search/detail/" + id)
                    .build());
        }

        SpeciesDetailVO detail = SpeciesDetailVO.builder()
                .id(id)
                .chineseName("松材线虫")
                .scientificName("Bursaphelenchus xylophilus")
                .authorship("(Steiner & Buhrer, 1934) Nickle, 1970")
                .status("confirmed")
                .statusText("已确认")
                .iconClass("fa-microscope")
                .englishName("Pine Wood Nematode")
                .englishAbbr("PWN")
                .taxonomicUnit("种 (Species)")
                .riskCode("EPPO A2 List")
                .guid("guid-sp1-pwn")
                .description("一种毁灭性的植物寄生线虫，被列为国际重要的检疫性有害生物。主要侵染松属植物，引起松材线虫病（Pine Wilt Disease, PWD），导致松树在短时间内（通常1-3个月）快速枯萎死亡。是全球重要的森林病害之一。")
                .sources("EPPO Global Database, CABI Compendium, 中国林业有害生物信息网")
                .biology(SpeciesDetailVO.BiologyInfoVO.builder()
                        .properties("典型的雌雄异体，卵生。生活史包括卵、四个幼虫期（J1-J4）和成虫期。J2期幼虫在环境胁迫下可形成高抗逆性的扩散型J4（dauerlarva），这是通过媒介昆虫传播的关键虫态。线虫主要取食寄主木材薄壁细胞以及侵入木材的共生真菌（如蓝变菌）菌丝。在适宜温度（25℃）下，世代周期短，约4-5天，繁殖能力极强。")
                        .stages("卵, 幼虫(J1, J2, J3, J4, J4d), 成虫")
                        .visibility("需借助显微镜观察")
                        .build())
                .morphology(SpeciesDetailVO.MorphologyInfoVO.builder()
                        .characteristics("虫体微小，线状，长度约0.6-1.1毫米。体表具细环纹，侧区明显。口针发达，基部具球结。雌虫尾端形态变异大，钝圆至尖细，具阴门瓣。雄虫尾弯曲，具发达的交合刺和小型交合伞。扩散型J4体壁增厚，口针退化，体内富含脂类。")
                        .detectionMethods(Arrays.asList(
                                "症状诊断: 观察松树针叶失水、萎蔫、快速变黄至红褐色，树脂分泌停止或异常。",
                                "贝尔曼漏斗法分离: 从可疑木材削片或木屑中分离线虫进行观察。",
                                "形态学鉴定: 在显微镜下根据口针、交合刺、尾端形态等关键特征进行鉴定。",
                                "分子检测 (PCR/qPCR): 检测线虫特异性DNA序列，准确、快速、灵敏。",
                                "媒介昆虫解剖: 解剖天牛成虫，检查气管等部位是否携带线虫。"
                        ))
                        .build())
                .distribution(SpeciesDetailVO.DistributionVO.builder()
                        .description("起源于北美洲，现已传播至亚洲（中国、日本、韩国等）和欧洲（葡萄牙、西班牙）。中国的分布范围仍在持续扩大中。")
                        .areas(Arrays.asList(
                                new SpeciesDetailVO.DistributionAreaVO("亚洲", Arrays.asList("中国 (多省)", "日本", "韩国")),
                                new SpeciesDetailVO.DistributionAreaVO("北美洲", Arrays.asList("美国", "加拿大", "墨西哥")),
                                new SpeciesDetailVO.DistributionAreaVO("欧洲", Arrays.asList("葡萄牙", "西班牙"))
                        ))
                        .statusDescription("在疫区为高风险检疫对象。")
                        .build())
                .host(SpeciesDetailVO.HostInfoVO.builder()
                        .rangeDescription("主要危害松属(Pinus)植物。对不同松种的易感性差异很大。")
                        .hosts(Arrays.asList(
                                new SpeciesDetailVO.HostItemVO("赤松", "Pinus densiflora", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("黑松", "Pinus thunbergii", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("马尾松", "Pinus massoniana", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("油松", "Pinus tabuliformis", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("黄山松", "Pinus hwangshanensis", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("欧洲赤松", "Pinus sylvestris", "secondary", "次要寄主"),
                                new SpeciesDetailVO.HostItemVO("湿地松", "Pinus elliottii", "occasional", "偶发寄主"),
                                new SpeciesDetailVO.HostItemVO("火炬松", "Pinus taeda", "occasional", "偶发寄主")
                        ))
                        .affectedParts("木质部（全株）")
                        .intensity("极高")
                        .build())
                .transmission(SpeciesDetailVO.TransmissionInfoVO.builder()
                        .mediums(Arrays.asList(
                                new SpeciesDetailVO.MediumInfoVO("松墨天牛", "Vector", "虫媒传播"),
                                new SpeciesDetailVO.MediumInfoVO("花墨天牛", "Vector", "虫媒传播"),
                                new SpeciesDetailVO.MediumInfoVO("云杉花墨天牛", "Vector", "虫媒传播(潜在)"),
                                new SpeciesDetailVO.MediumInfoVO("带疫松木及制品", "HumanActivity", "人为远距离传播")
                        ))
                        .pathwayDescription("天牛成虫取食健康松树嫩枝或产卵时传入线虫。人为传播主要是通过带疫原木、包装材料、木质电缆盘等。")
                        .ecoImpact("导致松林毁灭性破坏，改变森林结构，威胁生物多样性，造成巨大经济损失。")
                        .build())
                .management(SpeciesDetailVO.ManagementInfoVO.builder()
                        .summary("核心在于“清源、断道、强基”，即清除传染源、切断传播途径、增强森林抵抗力。")
                        .methods(Arrays.asList(
                                "严格检疫封锁疫区。",
                                "及时、彻底地砍伐和无害化处理疫木（焚烧、削片、药剂熏蒸等）。",
                                "利用诱捕器、药剂防治等手段控制天牛媒介种群数量。",
                                "对重点区域或古松名木进行树干注药（阿维菌素、甲维盐等）预防。",
                                "推广抗病或耐病松树品种。",
                                "加强林分抚育管理，提高森林健康水平。",
                                "建立健全的监测预警体系。"
                        ))
                        .remark("需严格遵守国家和地方相关防治技术规定。")
                        .build())
                .references(Arrays.asList( // 添加更多真实相关文献信息
                        new SpeciesDetailVO.ReferenceInfoVO("ref-pwn1", "松材线虫病研究进展", "叶建仁, 孙江华", "林业科学", 2010, Arrays.asList("综述", "生物学", "防治"), null, null, null),
                        new SpeciesDetailVO.ReferenceInfoVO("ref-pwn2", "Pine Wilt Disease", "Zhao, B. G., et al. (Eds.)", "Springer Japan", 2008, Arrays.asList("专著", "分布", "生物学"), "10.1007/978-4-431-75655-2", null, null),
                        new SpeciesDetailVO.ReferenceInfoVO("ref-pwn3", "松材线虫分子检测技术研究", "李义, 王兵 等", "中国森林病虫", 2015, Arrays.asList("研究报告", "分子生物学", "检测"), null, null, null)
                ))
                .taxonomy(generateTaxonomy("线虫动物门")) // 生成通用分类路径
                .images(generateImages("松材线虫", 4)) // 生成4张占位图
                .otherNames(Arrays.asList(new SpeciesDetailVO.OtherNameVO("异名(Synonym)", "Aphelenchoides xylophilus", "1934")))
                .metadata(generateMetadata())
                .build();
        speciesDetails.put(id, detail);
    }
    private static void addDetailedSpeciesAWM() {
        String id = "sp2-hca"; // 定义一个 ID
        if (speciesDetails.containsKey(id)) return;

        // 手动创建一个对应的 SearchResultItemVO，如果 allSearchResults 中没有的话
        if (allSearchResults.stream().noneMatch(item -> item.getId().equals(id))) {
            allSearchResults.add(SearchResultItemVO.builder()
                    .id(id).type("species").icon("faBug").title("美国白蛾")
                    .scientificName("Hyphantria cunea").classification("昆虫纲")
                    .status("已确认").statusType("confirmed")
                    .description("一种重要的国际检疫性害虫，食性杂...")
                    .tags(Arrays.asList("寄主: 杨属, 柳属", "分布: 北美"))
                    .detailLink("/search/detail/" + id)
                    .build());
        }

        SpeciesDetailVO detail = SpeciesDetailVO.builder()
                .id(id)
                .chineseName("美国白蛾")
                .scientificName("Hyphantria cunea")
                .authorship("(Drury, 1773)")
                .status("confirmed")
                .statusText("已确认")
                .iconClass("fa-bug")
                .englishName("Fall Webworm")
                .taxonomicUnit("种 (Species)")
                .riskCode("EPPO A2 List")
                .guid("guid-sp2-hca")
                .description("原产北美，现已广泛分布于欧亚多国。食性极杂，可危害300多种植物，尤喜杨、柳、榆、桑、核桃、苹果等。幼虫群居结网，啃食叶片，严重时可将整树叶片吃光，影响树木生长，甚至导致死亡。繁殖量大，传播速度快，是重要的国际检疫对象和林业、园林、果树的重要害虫。")
                .sources("CABI Compendium, EPPO Global Database, 全国农业技术推广服务中心")
                .biology(SpeciesDetailVO.BiologyInfoVO.builder()
                        .properties("年生代数因地域而异，在中国一般每年发生2-3代。以蛹在树皮缝、枯枝落叶、土壤等处越冬。成虫有趋光性。卵产于叶背，呈块状。幼虫共7龄（有时6龄），低龄幼虫群集吐丝结网取食，高龄后分散危害。食性随龄期增长而扩大。")
                        .stages("卵, 幼虫(1-7龄), 蛹, 成虫")
                        .visibility("卵、幼虫、蛹、成虫均肉眼可见")
                        .build())
                .morphology(SpeciesDetailVO.MorphologyInfoVO.builder()
                        .characteristics("成虫白色，部分个体前翅有黑褐色斑点。幼虫体色多变，常见黄绿色至灰褐色，背部有黑色斑点列，全身密布白色长毛。蛹红褐色，纺锤形。卵圆球形，淡绿或淡黄色。")
                        .detectionMethods(Arrays.asList(
                                "网幕观察: 查找树枝上典型的白色丝质网幕，是识别关键。",
                                "灯光诱集成虫: 利用成虫趋光性进行监测。",
                                "性信息素诱捕: 使用性信息素诱芯监测雄成虫发生期和种群密度。",
                                "人工巡查: 检查树干、枝条、叶片有无卵块、幼虫、蛹及危害状。"
                        ))
                        .build())
                .distribution(SpeciesDetailVO.DistributionVO.builder()
                        .description("原产北美洲，现已入侵欧洲大部、亚洲（中国、日本、韩国、中亚等）。在中国，北起黑龙江，南至长江流域及以南部分地区均有分布。")
                        .areas(Arrays.asList(
                                new SpeciesDetailVO.DistributionAreaVO("北美洲", Arrays.asList("美国", "加拿大", "墨西哥")),
                                new SpeciesDetailVO.DistributionAreaVO("欧洲", Arrays.asList("广泛分布")),
                                new SpeciesDetailVO.DistributionAreaVO("亚洲", Arrays.asList("中国(大部)", "日本", "韩国", "中亚"))
                        ))
                        .statusDescription("在中国为重要的外来入侵物种和检疫对象。")
                        .build())
                .host(SpeciesDetailVO.HostInfoVO.builder()
                        .rangeDescription("食性极杂，记录寄主植物超过300种。")
                        .hosts(Arrays.asList(
                                new SpeciesDetailVO.HostItemVO("杨属", "Populus spp.", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("柳属", "Salix spp.", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("榆属", "Ulmus spp.", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("桑属", "Morus spp.", "primary", "主要寄主"),
                                new SpeciesDetailVO.HostItemVO("核桃", "Juglans regia", "secondary", "次要寄主"),
                                new SpeciesDetailVO.HostItemVO("苹果属", "Malus spp.", "secondary", "次要寄主"),
                                new SpeciesDetailVO.HostItemVO("槭属", "Acer spp.", "secondary", "次要寄主"),
                                new SpeciesDetailVO.HostItemVO("梧桐", "Firmiana simplex", "occasional", "偶发寄主")
                        ))
                        .affectedParts("叶片")
                        .intensity("高至极高")
                        .build())
                .transmission(SpeciesDetailVO.TransmissionInfoVO.builder()
                        .mediums(Arrays.asList(
                                new SpeciesDetailVO.MediumInfoVO("成虫扩散", "NaturalSpread", "短距离飞行"),
                                new SpeciesDetailVO.MediumInfoVO("人为调运", "HumanActivity", "随带蛹、卵的苗木、木材、货物远距离传播"),
                                new SpeciesDetailVO.MediumInfoVO("风力", "Wind", "低龄幼虫和丝网可随风飘移")
                        ))
                        .pathwayDescription("自然扩散能力有限，远距离传播主要依靠人为因素。")
                        .ecoImpact("严重破坏森林、园林和果树景观，影响树木生长，降低经济效益。可能与其他害虫竞争资源。")
                        .build())
                .management(SpeciesDetailVO.ManagementInfoVO.builder()
                        .summary("采取“预防为主，分区治理，科学防控，依法监管”的策略。")
                        .methods(Arrays.asList(
                                "检疫: 严防疫情随调运传入传出。",
                                "物理防治: 剪除网幕并销毁幼虫；灯光诱杀成虫。",
                                "生物防治: 保护和利用天敌（如周氏啮小蜂、多种捕食性昆虫）；应用生物农药（如Bt、白僵菌、核型多角体病毒NPV）。",
                                "化学防治: 在幼虫低龄期（网幕期或分散危害初期）喷洒高效低毒农药（如甲维盐、灭幼脲、氯虫苯甲酰胺等）。",
                                "性信息素诱捕: 用于监测和干扰交配。"
                        ))
                        .remark("化学防治应注意保护天敌和环境。")
                        .build())
                .references(Arrays.asList( // 添加更多真实相关文献信息
                        new SpeciesDetailVO.ReferenceInfoVO("ref-awm1", "美国白蛾生物学特性及综合防治研究", "杨茂发 等", "贵州农学院学报", 1995, Arrays.asList("研究报告", "生物学", "防治"), null, null, null),
                        new SpeciesDetailVO.ReferenceInfoVO("ref-awm2", "Fall Webworm - CABI Invasive Species Compendium", "CABI", "CABI ISC", 2023, Arrays.asList("综述", "分布", "生物学"), null, "https://www.cabi.org/isc/datasheet/27373", null),
                        new SpeciesDetailVO.ReferenceInfoVO("ref-awm3", "美国白蛾性信息素的研究与应用", "杜永均 等", "林业科学研究", 2008, Arrays.asList("研究报告", "监测", "防治"), null, null, null)
                ))
                .taxonomy(generateTaxonomy("昆虫纲"))
                .images(generateImages("美国白蛾", 5))
                .otherNames(Arrays.asList(
                        new SpeciesDetailVO.OtherNameVO("英文俗名(Eng Common)", "Fall Webworm"),
                        new SpeciesDetailVO.OtherNameVO("异名(Synonym)", "Bombyx cunea Drury", "1773")
                ))
                .metadata(generateMetadata())
                .build();
        speciesDetails.put(id, detail);
    }
    private static void addDetailedSpeciesRIFA() {
        String id = "sp-rifa"; // 红火蚁 ID
        if (speciesDetails.containsKey(id)) return;

        if (allSearchResults.stream().noneMatch(item -> item.getId().equals(id))) {
            allSearchResults.add(SearchResultItemVO.builder()
                    .id(id).type("species").icon("faBug").title("红火蚁")
                    .scientificName("Solenopsis invicta").classification("昆虫纲")
                    .status("已确认").statusType("confirmed")
                    .description("极具危害性的入侵蚂蚁...")
                    .tags(Arrays.asList("入侵物种", "检疫性", "危害广泛"))
                    .detailLink("/search/detail/" + id)
                    .build());
        }

        SpeciesDetailVO detail = SpeciesDetailVO.builder()
                .id(id)
                .chineseName("红火蚁")
                .scientificName("Solenopsis invicta")
                .authorship("Buren, 1972")
                .status("confirmed")
                .statusText("已确认")
                .iconClass("faBug") // 可以用虫子图标
                .englishName("Red Imported Fire Ant")
                .englishAbbr("RIFA")
                .taxonomicUnit("种 (Species)")
                .riskCode("IUCN 100 Worst Invasive Species")
                .guid("guid-sp-rifa")
                .description("原产于南美洲，现已入侵全球多个国家和地区。具有强大的竞争、适应和繁殖能力。蚁巢通常建在开阔、阳光充足的地方，如草坪、公园、农田、堤坝、林缘等地，形成明显隆起的土堆状蚁丘。对农林业生产、公共安全、生态系统和生物多样性造成严重威胁。被列为全球100种最具破坏力的入侵物种之一。")
                .sources("全球入侵物种数据库(GISD), 中国农业有害生物信息系统, USDA APHIS")
                .biology(SpeciesDetailVO.BiologyInfoVO.builder()
                        .properties("社会性昆虫，蚁群包括蚁后、雄蚁、工蚁（多型）和兵蚁。可通过分巢、婚飞和人为携带等方式扩散。食性杂，捕食昆虫、蚯蚓等无脊椎动物，也取食植物种子、果实、嫩芽及含糖分泌物。工蚁有强烈的攻击性，受惊扰时会群起叮蛰入侵者。")
                        .stages("卵, 幼虫, 蛹, 成虫(蚁后、雄蚁、工蚁、兵蚁)")
                        .visibility("成虫、蚁丘肉眼可见")
                        .build())
                .morphology(SpeciesDetailVO.MorphologyInfoVO.builder()
                        .characteristics("工蚁体长约3-6毫米，红褐色。头胸腹连接处有明显的2节结节。触角10节，末端2节膨大呈棒状。上颚发达。腹部末端有螯针，可进行叮蛰。")
                        .detectionMethods(Arrays.asList(
                                "蚁丘调查: 寻找特征性的隆起土堆状蚁丘。",
                                "诱饵诱集: 使用香肠、薯片、糖水等诱饵诱集工蚁进行观察和采集。",
                                "形态学鉴定: 根据工蚁的结节数、触角节数等关键特征进行鉴定。",
                                "分子鉴定: 可用于精确区分近似种。"
                        ))
                        .build())
                .distribution(SpeciesDetailVO.DistributionVO.builder()
                        .description("原产于南美洲巴拉那河流域。现已入侵美国、澳大利亚、新西兰、中国大陆、台湾、香港、澳门及加勒比地区等。在中国主要分布于南方省份。")
                        .areas(Arrays.asList(
                                new SpeciesDetailVO.DistributionAreaVO("南美洲", Arrays.asList("巴西", "阿根廷", "巴拉圭(原产地)")),
                                new SpeciesDetailVO.DistributionAreaVO("北美洲", Arrays.asList("美国(南部)", "墨西哥")),
                                new SpeciesDetailVO.DistributionAreaVO("大洋洲", Arrays.asList("澳大利亚", "新西兰")),
                                new SpeciesDetailVO.DistributionAreaVO("亚洲", Arrays.asList("中国(南方省份)", "台湾", "日本(零星发现)"))
                        ))
                        .statusDescription("在中国为重大外来入侵物种和检疫对象。")
                        .build())
                .host(SpeciesDetailVO.HostInfoVO.builder() // 红火蚁严格意义上没有“寄主”，但会影响多种植物和环境
                        .rangeDescription("食性极杂，虽不直接寄生植物，但会取食植物种子、果实、幼苗根茎，筑巢影响植物根系，捕食传粉昆虫影响授粉，危害范围极广。")
                        .hosts(Arrays.asList( // 列出受影响的作物或环境类型
                                new SpeciesDetailVO.HostItemVO("农田作物", "Various crops", "affected", "农业"),
                                new SpeciesDetailVO.HostItemVO("草坪绿地", "Turfgrass", "affected", "园林"),
                                new SpeciesDetailVO.HostItemVO("苗圃", "Nurseries", "affected", "林业/园艺"),
                                new SpeciesDetailVO.HostItemVO("堤坝设施", "Infrastructure", "affected", "公共设施"),
                                new SpeciesDetailVO.HostItemVO("幼小动物", "Small animals", "predated", "生态")
                        ))
                        .affectedParts("种子, 果实, 根系, 幼苗, 地面活动节肢动物, 雏鸟等")
                        .intensity("极高")
                        .build())
                .transmission(SpeciesDetailVO.TransmissionInfoVO.builder()
                        .mediums(Arrays.asList(
                                new SpeciesDetailVO.MediumInfoVO("婚飞扩散", "NaturalSpread", "新蚁后飞行建巢"),
                                new SpeciesDetailVO.MediumInfoVO("分巢扩散", "NaturalSpread", "蚁群分裂"),
                                new SpeciesDetailVO.MediumInfoVO("人为携带", "HumanActivity", "随带土苗木、草皮、垃圾、设备等远距离传播"),
                                new SpeciesDetailVO.MediumInfoVO("水流扩散", "Water", "洪水可携带蚁巢漂流")
                        ))
                        .pathwayDescription("人为远距离传播是造成其快速入侵的主要原因。")
                        .ecoImpact("破坏生物多样性，攻击人畜，损坏基础设施，造成农业减产，增加防治成本。")
                        .build())
                .management(SpeciesDetailVO.ManagementInfoVO.builder()
                        .summary("采取“阻截、扑灭、控制”相结合的策略，重点是防止扩散和扑灭新区疫情。")
                        .methods(Arrays.asList(
                                "检疫: 严防带疫物品调运。",
                                "监测: 定期调查，布设诱饵监测点，及时发现蚁巢。",
                                "药剂防治: 使用毒饵诱杀（常用药剂如氟虫腈、吡丙醚、多杀霉素等）；对蚁丘进行灌药或粉剂处理。",
                                "物理防治: 开水烫杀（少量蚁巢）、挖巢销毁（需专业防护）。",
                                "生物防治: 研究和引进天敌（如南美蚤蝇）或病原微生物。",
                                "公众宣传: 提高公众意识，参与防控。"
                        ))
                        .remark("红火蚁叮蛰可引起严重过敏反应，防治时需注意安全防护！")
                        .build())
                .references(Arrays.asList(
                        new SpeciesDetailVO.ReferenceInfoVO("ref-rifa1", "红火蚁的综合治理技术研究", "曾玲 等", "植物保护", 2005, Arrays.asList("研究报告", "防治"), null, null, null),
                        new SpeciesDetailVO.ReferenceInfoVO("ref-rifa2", "Red imported fire ant - GISD", "Global Invasive Species Database", "GISD", 2023, Arrays.asList("综述", "分布", "入侵生物学"), null, "http://www.iucngisd.org/gisd/species.php?sc=110", null),
                        new SpeciesDetailVO.ReferenceInfoVO("ref-rifa3", "红火蚁生物防治研究进展", "陆永跃, 梁广文", "昆虫知识", 2006, Arrays.asList("综述", "生物防治"), null, null, null)
                ))
                .taxonomy(generateTaxonomy("昆虫纲")) // 蚂蚁属于昆虫纲
                .images(generateImages("红火蚁", 3))
                .otherNames(Arrays.asList(
                        new SpeciesDetailVO.OtherNameVO("英文俗名(Eng Common)", "Red Imported Fire Ant (RIFA)"),
                        new SpeciesDetailVO.OtherNameVO("地方名(Local Name)", "入侵红火蚁")
                ))
                .metadata(generateMetadata())
                .build();
        speciesDetails.put(id, detail);
    }

    /**
     * 模拟执行搜索和分页
     */
    public PageVO<SearchResultItemVO> search(SearchRequest request) {
        String query = StringUtils.hasText(request.getQuery()) ? request.getQuery().toLowerCase().trim() : null;
        String typeFilter = request.getType();
        String classificationFilter = request.getClassification();
        String statusFilter = request.getStatus();
        // ... 获取其他筛选条件 ...

        // 1. 过滤
        List<SearchResultItemVO> filteredResults = allSearchResults.stream()
                .filter(item -> {
                    boolean match = true;
                    // 类型过滤
                    if (StringUtils.hasText(typeFilter) && !item.getType().equalsIgnoreCase(typeFilter)) {
                        return false;
                    }
                    // 关键词过滤
                    if (query != null) {
                        match = (item.getTitle() != null && item.getTitle().toLowerCase().contains(query)) ||
                                (item.getScientificName() != null && item.getScientificName().toLowerCase().contains(query)) ||
                                (item.getDescription() != null && item.getDescription().toLowerCase().contains(query)) ||
                                (item.getTags() != null && item.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(query)));
                        if (!match) return false;
                    }

                    // --- 添加其他筛选条件的过滤逻辑 ---
                    if (match && StringUtils.hasText(classificationFilter) && "species".equals(item.getType())) {
                        if (item.getClassification() == null || !item.getClassification().contains(classificationFilter)) {
                            match = false;
                        }
                    }
                    if (match && StringUtils.hasText(statusFilter) && "species".equals(item.getType())) {
                        if (item.getStatus() == null || !item.getStatus().equals(statusFilter)) {
                            match = false;
                        }
                    }
                    // TODO: 添加更多筛选逻辑... (taxonomicLevel, continent, country, province, hostName, hostType, refType, pubYear)

                    return match;
                })
                .collect(Collectors.toList());

        // 2. 分页
        long total = filteredResults.size();
        int page = request.getPage();
        int pageSize = request.getPageSize();
        long totalPages = PageVO.calculateTotalPages(total, pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, (int) total);
        List<SearchResultItemVO> paginatedResults = (startIndex < total) ? filteredResults.subList(startIndex, endIndex) : Collections.emptyList();

        return new PageVO<>(paginatedResults, total, page, pageSize, totalPages);
    }

    /**
     * 根据 ID 获取物种详情
     */
    public SpeciesDetailVO getSpeciesDetailById(String speciesId) {
        return speciesDetails.get(speciesId);
    }

    // --- 辅助方法 ---
    private static <T> T randomElement(T[] array) {
        if (array == null || array.length == 0) return null;
        return array[random.nextInt(array.length)];
    }
    private static <T> T randomElement(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(random.nextInt(list.size()));
    }
    private static String capitalizeFirst(String str) {
        if (!StringUtils.hasText(str)) return str;
        // 处理单个字的情况
        if (str.length() == 1) return str.toUpperCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    private static String generateParagraphs(int min, int max) {
        int count = random.nextInt(max - min + 1) + min;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(capitalizeFirst(randomElement(DOC_PREFIXES)) + "了" + randomElement(SPECIES_PREFIXES) + "的" + randomElement(Arrays.asList("生长", "分布", "危害", "防治")) + "情况。");
            if (i < count - 1) sb.append(" "); // 段落间用空格，前端渲染时处理换行
        }
        return sb.toString();
    }
    private static List<String> generateList(int min, int max, String prefix) {
        int count = random.nextInt(max - min + 1) + min;
        List<String> list = new ArrayList<>();
        Set<String> generated = new HashSet<>(); // 避免重复
        while(list.size() < count) {
            String item = prefix + " " + (list.size() + 1) + ": " + capitalizeFirst(randomElement(Arrays.asList("方法", "技术", "策略", "手段", "措施", "要点", "关键", "注意事项")));
            if (generated.add(item)) {
                list.add(item);
            }
        }
        return list;
    }
    private static <T> Set<T> generateSet(int min, int max, List<T> source) {
        int count = random.nextInt(max - min + 1) + min;
        count = Math.min(count, source.size());
        Set<T> set = new HashSet<>();
        if (source.isEmpty()) return set; // 处理空源列表
        while(set.size() < count) {
            set.add(randomElement(source));
        }
        return set;
    }
    private static String generateAuthors() {
        int authorCount = random.nextInt(3) + 1;
        List<String> authorList = new ArrayList<>();
        Set<String> usedAuthors = new HashSet<>();
        while(authorList.size() < authorCount) {
            String author = randomElement(AUTHORS).split(" ")[0]; // 只取第一部分
            if (usedAuthors.add(author)) {
                authorList.add(author);
            }
            // 防止无限循环（如果作者列表不够）
            if(usedAuthors.size() >= AUTHORS.length) break;
        }

        String result = String.join(", ", authorList);
        if (authorCount > 1 && random.nextBoolean()) {
            result += " 等";
        }
        return result;
    }
    private static String generateStages() {
        List<String> stages = new ArrayList<>(Arrays.asList("卵", "幼虫", "蛹", "成虫"));
        if(random.nextDouble() < 0.3) stages.add(random.nextInt(2)+1, "若虫");
        if(random.nextDouble() < 0.2) stages.add(random.nextInt(stages.size()-1)+1, "龄期(多个)");
        return String.join(", ", stages);
    }
    private static List<SpeciesDetailVO.TaxonomyItemVO> generateTaxonomy(String primaryClassification) {
        List<SpeciesDetailVO.TaxonomyItemVO> taxonomy = new ArrayList<>();
        String[] ranks = {"界", "门", "纲", "目", "科", "属", "种"};
        String[] prefixes = {"动物界", "节肢动物门", "昆虫纲", "鞘翅目", "天牛科", "墨天牛属", "松墨天牛种"}; // 默认
        if (primaryClassification != null) {
            if (primaryClassification.contains("线虫")) {
                prefixes = new String[]{"动物界", "线虫动物门", "色矛纲", "滑刃目", "伞滑刃科", "滑刃属", "松材线虫种"};
            } else if (primaryClassification.contains("真菌")) {
                prefixes = new String[]{"真菌界", "子囊菌门", "座囊菌纲", "炭疽菌目", "小丛壳科", "刺盘孢属", "炭疽病菌种"};
            } else if (primaryClassification.contains("蛛形")) {
                prefixes = new String[]{"动物界", "节肢动物门", "蛛形纲", "蜱螨目", "真螨亚目", "叶螨科", "叶螨属"};
            } else if (primaryClassification.contains("植物")) {
                prefixes = new String[]{"植物界", "被子植物门", "双子叶植物纲", "蔷薇目", "蔷薇科", "苹果属", "苹果种"}; // 示例
            }
        }
        // 确保 prefixes 长度足够
        if (prefixes.length < ranks.length) {
            String[] defaultPrefixes = {"未知界", "未知门", "未知纲", "未知目", "未知科", "未知属", "未知种"};
            String[] finalPrefixes = new String[ranks.length];
            System.arraycopy(prefixes, 0, finalPrefixes, 0, prefixes.length);
            System.arraycopy(defaultPrefixes, prefixes.length, finalPrefixes, prefixes.length, ranks.length - prefixes.length);
            prefixes = finalPrefixes;
        }

        for(int i=0; i<ranks.length; i++) {
            taxonomy.add(SpeciesDetailVO.TaxonomyItemVO.builder()
                    .rank(ranks[i])
                    .name(prefixes[i].replace("界/门/纲...", "")) // 简化名称
                    .isCurrent(i == ranks.length - 1)
                    .build());
        }
        return taxonomy;
    }
    private static List<SpeciesDetailVO.ImageVO> generateImages(String speciesName, int count) {
        List<SpeciesDetailVO.ImageVO> images = new ArrayList<>();
        Set<String> usedTypes = new HashSet<>();
        for(int i=0; i<count; i++) {
            String imgType = randomElement(IMG_TYPES);
            // 生成稍微不同的 URL 以避免浏览器缓存完全相同的图片
            String colorHex = String.format("%06x", random.nextInt(0xffffff + 1));
            String text = speciesName.length() > 5 ? speciesName.substring(0, 5) : speciesName;
            if (usedTypes.add(imgType + colorHex)) { // 结合类型和颜色确保稍微不同
                images.add(SpeciesDetailVO.ImageVO.builder()
                        .id("img-" + UUID.randomUUID().toString().substring(0,5))
                        .src("https://placehold.co/400x300/" + colorHex + "/FFF?text=" + text + "+" + imgType)
                        .alt(speciesName + " " + imgType + " 图片")
                        .caption(capitalizeFirst(imgType) + " 示例图")
                        .type(imgType)
                        .build());
            }
        }
        return images;
    }
    private static SpeciesDetailVO.MetadataVO generateMetadata() {
        boolean reviewed = random.nextDouble() < 0.7; // 70% 概率已审核
        LocalDateTime createdAt = LocalDateTime.now().minusDays(random.nextInt(1095) + 30); // 1个月到3年前创建
        LocalDateTime updatedAt = createdAt.plusDays(random.nextInt((int)java.time.temporal.ChronoUnit.DAYS.between(createdAt, LocalDateTime.now()))); // 更新时间在创建之后，现在之前
        LocalDateTime reviewedAt = reviewed ? updatedAt.plusDays(random.nextInt(30)) : null; // 审核时间在更新后30天内

        return SpeciesDetailVO.MetadataVO.builder()
                .creator(randomElement(AUTHORS))
                .createdAt(createdAt.format(dtFormatter))
                .editor(randomElement(AUTHORS))
                .updatedAt(updatedAt.format(dtFormatter))
                .reviewer(reviewed ? randomElement(AUTHORS) : null)
                .reviewedAt(reviewedAt != null ? reviewedAt.format(dtFormatter) : null)
                .build();
    }
    private static String generateRealisticDescription(String title, String context, boolean isSpecies) {
        String template1 = "%s是常见的%s之一，主要危害%s，在我国%s等地区有分布。其%s特性对当地%s造成一定影响，需关注其%s。";
        String template2 = "本文%s关于%s的%s研究，结果表明%s与%s存在%s关联。为%s提供了理论依据。";
        String template3 = "%s是一种%s%s，具有%s等典型特征，推荐采用%s等综合措施进行%s防治。";

        try {
            if (isSpecies) {
                return String.format(template1, title,
                        randomElement(Arrays.asList("林业害虫", "农业病害", "重要病原真菌", "外来入侵物种")),
                        randomElement(HOSTS),
                        randomElement(LOCATIONS),
                        randomElement(Arrays.asList("生物学", "繁殖", "传播", "危害", "遗传")),
                        randomElement(Arrays.asList("森林生态系统", "农业生产", "生物多样性", "经济林木")),
                        randomElement(Arrays.asList("种群动态", "发生规律", "抗药性发展"))
                );
            } else {
                String topic = title.replaceFirst(randomElement(DOC_PREFIXES), "").replaceFirst(randomElement(DOC_SUFFIXES), "").trim();
                return String.format(template2,
                        randomElement(Arrays.asList("探讨", "分析", "总结", "报告", "综述")),
                        topic.isEmpty() ? "相关问题" : topic, // 防止 topic 为空
                        randomElement(Arrays.asList("防治效果", "发生规律", "监测技术", "风险等级", "生态适应性")),
                        randomElement(Arrays.asList("气候因子", "寄主抗性", "天敌数量", "管理措施", "林分结构")),
                        randomElement(Arrays.asList("病虫害发生程度", "林分健康状况", "经济效益", "生物入侵风险")),
                        randomElement(Arrays.asList("显著正相关", "显著负相关", "密切", "潜在", "复杂")),
                        randomElement(Arrays.asList("科学防治", "精准监测", "风险预警", "可持续管理"))
                );
            }
        } catch (Exception e) {
            System.err.println("Error generating description: " + e.getMessage());
            return "默认描述：这是一个关于 " + title + " 的条目。"; // 提供一个回退描述
        }
    }

}