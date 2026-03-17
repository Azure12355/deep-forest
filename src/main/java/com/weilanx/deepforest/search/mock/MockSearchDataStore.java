package com.weilanx.deepforest.search.mock;

import com.weilanx.deepforest.search.dto.PageVO;
import com.weilanx.deepforest.search.dto.SearchResultItemVO;
import com.weilanx.deepforest.search.dto.SpeciesDetailVO;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.*;

@Component
public class MockSearchDataStore {

    private List<SearchResultItemVO> allResults;
    private Map<String, SpeciesDetailVO> detailMap;

    @PostConstruct
    public void init() {
        detailMap = new LinkedHashMap<>();
        allResults = buildMockData();
    }

    public PageVO<SearchResultItemVO> search(String query, int page, int pageSize, String type, String classification, String status) {
        List<SearchResultItemVO> filtered = new ArrayList<>();
        for (SearchResultItemVO item : allResults) {
            boolean matches = true;
            if (query != null && !query.isBlank()) {
                String q = query.toLowerCase();
                boolean qm = (item.getTitle() != null && item.getTitle().toLowerCase().contains(q))
                        || (item.getScientificName() != null && item.getScientificName().toLowerCase().contains(q))
                        || (item.getDescription() != null && item.getDescription().toLowerCase().contains(q));
                if (item.getTags() != null) {
                    for (String tag : item.getTags()) {
                        if (tag.toLowerCase().contains(q)) qm = true;
                    }
                }
                matches = qm;
            }
            if (type != null && !type.isBlank()) matches = matches && type.equals(item.getType());
            if (classification != null && !classification.isBlank() && item.getClassification() != null) {
                matches = matches && item.getClassification().contains(classification);
            }
            if (status != null && !status.isBlank()) matches = matches && status.equals(item.getStatus());
            if (matches) filtered.add(item);
        }
        int total = filtered.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) total / pageSize));
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, total);
        List<SearchResultItemVO> records = from < total ? filtered.subList(from, to) : Collections.emptyList();
        return PageVO.<SearchResultItemVO>builder().records(records).total(total).page(page).pageSize(pageSize).totalPages(totalPages).build();
    }

    public SearchResultItemVO getById(String id) {
        for (SearchResultItemVO item : allResults) {
                if (id.equals(item.getId())) return item;
            }
            return null;
    }

    public SpeciesDetailVO getDetailById(String id) {
        return detailMap.get(id);
    }

    private List<SearchResultItemVO> buildMockData() {
        List<SearchResultItemVO> results = new ArrayList<>();

        addSpecies(results, "sp-001", "松材线虫", "Bursaphelenchus xylophilus", "线虫动物门 > 线虫纲 > 滑刃目", "已确认", "confirmed", "松材线虫是毁灭性森林病原线虫，主要危害松树。", "Pine wood nematode", "Steiner, 1934", "DF-NEM-001", "马尾松,黑松,赤松", "木质部", "华东|江苏,浙江;华南|广东,福建");
        addSpecies(results, "sp-002", "美国白蛾", "Hyphantria cunea", "节肢动物门 > 昆虫纲 > 鳞翅目", "已确认", "confirmed", "美国白蛾为杂食性害虫，幼虫取食叶片。", "Fall webworm", "Drury, 1773", "DF-INS-002", "悬铃木,杨树,柳树", "叶片", "华北|北京,河北;东北|辽宁,吉林");
        addSpecies(results, "sp-003", "光肩星天牛", "Anoplophora glabripennis", "节肢动物门 > 昆虫纲 > 鞘翅目", "已确认", "confirmed", "光肩星天牛幼虫蛀食树干和大枝。", "Asian longhorned beetle", "Motschulsky, 1853", "DF-INS-003", "杨树,柳树,榆树", "树干", "黄淮海|山东,河南;西北|陕西,甘肃");
        addSpecies(results, "sp-004", "杨树溃疡病病原菌", "Botryosphaeria dothidea", "真菌界 > 子囊菌门 > 座囊菌纲", "待审核", "pending", "该病原菌引起杨树枝干溃疡。", "Poplar canker", "Moug. ex Fr.", "DF-FUN-004", "杨树,山杨", "树皮", "东北|黑龙江,吉林;西北|宁夏,内蒙古");
        addSpecies(results, "sp-005", "云杉八齿小蠹", "Ips typographus", "节肢动物门 > 昆虫纲 > 鞘翅目", "已确认", "confirmed", "云杉八齿小蠹危害云杉和冷杉。", "Spruce bark beetle", "Linnaeus, 1758", "DF-INS-005", "云杉,冷杉,落叶松", "韧皮部", "大兴安岭|呼伦贝尔;长白山|延边");
        addSpecies(results, "sp-006", "杨尺蠖", "Apocheima cinerarius", "节肢动物门 > 昆虫纲 > 鳞翅目", "待审核", "pending", "杨尺蠖幼虫暴食性强。", "Poplar looper", "Erschoff, 1874", "DF-INS-006", "杨树,柳树,榆树", "叶片", "华北平原|河北,山东;黄土高原|山西,陕西");
        addSpecies(results, "sp-007", "赤松毛虫", "Dendrolimus spectabilis", "节肢动物门 > 昆虫纲 > 鳞翅目", "已确认", "confirmed", "赤松毛虫为松林常见食叶害虫。", "Pine moth", "Butler, 1877", "DF-INS-007", "油松,黑松,赤松", "针叶", "华东沿海|江苏,浙江;华南丘陵|江西,福建");
        addSpecies(results, "sp-008", "松褐天牛", "Monochamus alternatus", "节肢动物门 > 昆虫纲 > 鞘翅目", "已确认", "confirmed", "松褐天牛是松材线虫传播媒介。", "Japanese pine sawyer", "Hope, 1842", "DF-INS-008", "马尾松,黑松,油松", "树干", "长江中下游|安徽,湖北;华南山地|广西,广东");
        addSpecies(results, "sp-009", "栗山天牛", "Massicus raddei", "节肢动物门 > 昆虫纲 > 鞘翅目", "有疑问", "default", "栗山天牛主要危害栎类和板栗。", "Oak longhorn beetle", "Blessig, 1872", "DF-INS-009", "栎树,板栗,麻栎", "树干", "秦巴山区|陕西,湖北;华中丘陵|湖南,江西");
        addSpecies(results, "sp-010", "落叶松早落病病原菌", "Mycosphaerella laricina", "真菌界 > 子囊菌门 > 座囊菌纲", "已确认", "confirmed", "落叶松早落病病原菌造成针叶提早脱落。", "Larch needle cast", "Hartig", "DF-FUN-010", "落叶松,长白落叶松", "针叶", "东北林区|黑龙江,吉林;西南高山|四川,云南");
        addSpecies(results, "sp-011", "红脂大小蠹", "Dendroctonus valens", "节肢动物门 > 昆虫纲 > 鞘翅目", "已确认", "confirmed", "红脂大小蠹主要危害松树根部。", "Red turpentine beetle", "LeConte, 1860", "DF-INS-011", "油松,华山松,白皮松", "根部", "华北|山西,河北;西北|陕西,甘肃");
        addSpecies(results, "sp-012", "杨干象", "Cryptorhynchus lapathi", "节肢动物门 > 昆虫纲 > 鞘翅目", "待审核", "pending", "杨干象幼虫在树干内蛀食。", "Poplar weevil", "Linnaeus, 1758", "DF-INS-012", "杨树,柳树,桤木", "树干", "东北|黑龙江,吉林;华北|内蒙古,河北");
        addSpecies(results, "sp-013", "松疱锈病病原菌", "Cronartium ribicola", "真菌界 > 担子菌门 > 锈菌纲", "已确认", "confirmed", "松疱锈病是五针松重要病害。", "Pine blister rust", "J.C. Fisch.", "DF-FUN-013", "红松,华山松,五针松", "枝干", "东北林区|黑龙江,吉林;西南高山|云南,四川");
        addSpecies(results, "sp-014", "黄脊竹蝗", "Ceracris kiangsu", "节肢动物门 > 昆虫纲 > 直翅目", "已确认", "confirmed", "黄脊竹蝗是竹林重要害虫。", "Bamboo locust", "Tsai, 1934", "DF-INS-014", "毛竹,慈竹,刚竹", "竹叶", "南方竹区|浙江,福建,江西;西南|四川,贵州");
        addSpecies(results, "sp-015", "马尾松毛虫", "Dendrolimus punctatus", "节肢动物门 > 昆虫纲 > 鳞翅目", "已确认", "confirmed", "马尾松毛虫是马尾松林主要食叶害虫。", "Masson pine caterpillar", "Walker, 1855", "DF-INS-015", "马尾松,湿地松,火炬松", "针叶", "华南地区|广东,广西,福建;华中地区|湖南,江西");
        addSpecies(results, "sp-016", "杨小舟蛾", "Micromelalopha troglodyta", "节肢动物门 > 昆虫纲 > 鳞翅目", "待审核", "pending", "杨小舟蛾幼虫取食杨树叶片。", "Poplar prominent", "Graeser, 1888", "DF-INS-016", "杨树,柳树", "叶片", "华北平原|河北,山东,河南;江淮地区|江苏,安徽");
        addSpecies(results, "sp-017", "松墨天牛", "Monochamus saltuarius", "节肢动物门 > 昆虫纲 > 鞘翅目", "已确认", "confirmed", "松墨天牛危害多种松树。", "Pine longhorn beetle", "Gebler, 1830", "DF-INS-017", "云杉,冷杉,落叶松,松树", "树干", "东北林区|黑龙江,吉林;华北山地|河北,山西");
        addSpecies(results, "sp-018", "苹果蠹蛾", "Cydia pomonella", "节肢动物门 > 昆虫纲 > 鳞翅目", "已确认", "confirmed", "苹果蠹蛾是苹果等果树重要蛀果害虫。", "Codling moth", "Linnaeus, 1758", "DF-INS-018", "苹果,梨,核桃,杏", "果实", "西北果区|陕西,甘肃,新疆;华北果区|山东,河北");
        addSpecies(results, "sp-019", "杨树黑斑病病原菌", "Marssonina brunnea", "真菌界 > 子囊菌门 > 座囊菌纲", "待审核", "pending", "杨树黑斑病病原菌引起杨树叶片黑褐色病斑。", "Poplar leaf spot", "Ell. and Ev.", "DF-FUN-019", "杨树,柳树", "叶片", "华北地区|北京,天津,河北;华东地区|江苏,浙江");
        addSpecies(results, "sp-020", "舞毒蛾", "Lymantria dispar", "节肢动物门 > 昆虫纲 > 鳞翅目", "已确认", "confirmed", "舞毒蛾是世界性害虫，幼虫食性杂。", "Gypsy moth", "Linnaeus, 1758", "DF-INS-020", "栎树,杨树,柳树,榆树,苹果", "叶片", "东北地区|辽宁,吉林;华北地区|河北,山东");
        addSpecies(results, "sp-021", "沙棘木蠹蛾", "Holcocerus hippophaecolus", "节肢动物门 > 昆虫纲 > 鳞翅目", "有疑问", "default", "沙棘木蠹蛾幼虫蛀食沙棘根部和树干。", "Seabuckthorn moth", "Hua et al., 1990", "DF-INS-021", "沙棘,胡颓子", "根部,树干", "西北地区|陕西,甘肃,宁夏;华北地区|山西,内蒙古");
        addSpecies(results, "sp-022", "杉木炭疽病病原菌", "Glomerella cingulata", "真菌界 > 子囊菌门 > 粪壳菌纲", "已确认", "confirmed", "杉木炭疽病病原菌危害杉木针叶和嫩梢。", "Chinese fir anthracnose", "Stonem.", "DF-FUN-022", "杉木,柳杉", "针叶,嫩梢", "南方杉木区|福建,江西,湖南;西南地区|四川,贵州");
        addSpecies(results, "sp-023", "油茶尺蠖", "Biston marginata", "节肢动物门 > 昆虫纲 > 鳞翅目", "待审核", "pending", "油茶尺蠖幼虫取食油茶叶片。", "Oiltea looper", "Matsumura, 1924", "DF-INS-023", "油茶,茶树", "叶片,嫩梢", "南方油茶区|湖南,江西,广西;福建|三明,南平");
        addSpecies(results, "sp-024", "刺槐蚜", "Aphis robiniae", "节肢动物门 > 昆虫纲 > 半翅目", "已确认", "confirmed", "刺槐蚜群集于刺槐嫩梢和叶片吸汁危害。", "Black locust aphid", "Canestrini, 1889", "DF-INS-024", "刺槐,紫穗槐", "嫩梢,叶片", "华北地区|山东,河北,河南;西北地区|陕西,甘肃");
        addSpecies(results, "sp-025", "泡桐丛枝病植原体", "Phytoplasma aurantifolia", "细菌界 > 柔膜菌门 > 柔膜菌纲", "有疑问", "default", "泡桐丛枝病是由植原体引起的系统性病害。", "Paulownia witches broom", "Phytoplasma", "DF-PHY-025", "泡桐,毛泡桐", "全株", "华北地区|河南,山东;华中地区|湖北,湖南");

        String[] spNames = {"松材线虫", "美国白蛾", "光肩星天牛", "杨树溃疡病", "云杉八齿小蠹", "杨尺蠖", "赤松毛虫", "松褐天牛", "栗山天牛", "落叶松早落病", "红脂大小蠹", "杨干象", "松疱锈病", "黄脊竹蝗", "马尾松毛虫", "杨小舟蛾", "松墨天牛", "苹果蠹蛾", "杨树黑斑病", "舞毒蛾"};
        String[][] themes = {{"研究进展", "综述"}, {"综合防治", "技术手册"}, {"监测预警", "风险评估"}, {"生物学特性", "实验研究"}};
        for (int i = 0; i < 20; i++) {
            String[] t = themes[i % 4];
            results.add(SearchResultItemVO.builder()
                    .id("doc-" + String.format("%03d", i + 1))
                    .type("document")
                    .icon("file-text")
                    .title(spNames[i] + t[0])
                    .author("林业研究组" + (i + 1))
                    .description("围绕" + spNames[i] + "的" + t[0] + "展开研究。")
                    .tags(Arrays.asList(spNames[i], t[0], t[1]))
                    .detailLink("/search")
                    .build());
        }
        return results;
    }

    private void addSpecies(List<SearchResultItemVO> results, String id, String cn, String sn, String cl, String st, String stt, String desc, String en, String au, String rc, String hn, String ap, String dd) {
        List<String> tags = new ArrayList<>();
        if (cl.contains("昆虫")) tags.add("昆虫纲");
        if (cl.contains("线虫")) tags.add("线虫");
        if (cl.contains("真菌")) tags.add("真菌");
        tags.add("林业有害生物");
        tags.add("监测预警");

        results.add(SearchResultItemVO.builder()
                .id(id).type("species").icon("bug").title(cn).scientificName(sn).classification(cl)
                .status(st).statusType(stt).description(desc + " 该条目为 DeepForest 演示数据。")
                .tags(tags).detailLink("/search/detail/" + id).build());

        String[] hosts = hn.split(",");
        String[] distParts = dd.split(";");
        List<SpeciesDetailVO.Distribution.Area> areas = new ArrayList<>();
        for (String part : distParts) {
            String[] ad = part.split("\\|");
            if (ad.length == 2) {
                areas.add(SpeciesDetailVO.Distribution.Area.builder().region(ad[0]).locations(Arrays.asList(ad[1].split(","))).build());
            }
        }

        List<SpeciesDetailVO.Host.HostItem> hostItems = new ArrayList<>();
        for (int i = 0; i < hosts.length; i++) {
            hostItems.add(SpeciesDetailVO.Host.HostItem.builder()
                    .name(hosts[i]).scientificName(hosts[i] + " sp.")
                    .type(i == 0 ? "primary" : "secondary")
                    .category(i == 0 ? "重点寄主" : "常见寄主").build());
        }

        String[] taxNames = cl.split(" > ");
        String[] taxRanks = {"界", "门", "纲", "目", "科", "属"};
        List<SpeciesDetailVO.Taxonomy> taxonomy = new ArrayList<>();
        for (int i = 0; i < taxNames.length; i++) {
            taxonomy.add(SpeciesDetailVO.Taxonomy.builder()
                    .rank(taxRanks.length > i ? taxRanks[i] : "层级")
                    .name(taxNames[i])
                    .isCurrent(i == taxNames.length - 1).build());
        }

        String theme = cl.contains("线虫") ? "线虫" : cl.contains("真菌") ? "真菌" : "昆虫";
        List<SpeciesDetailVO.Transmission.Medium> mediums = new ArrayList<>();
        if (theme.equals("线虫")) {
            mediums.add(SpeciesDetailVO.Transmission.Medium.builder().name("松褐天牛").type("Vector").method("媒介传播").build());
            mediums.add(SpeciesDetailVO.Transmission.Medium.builder().name("疫木调运").type("Human").method("人为传播").build());
        } else if (theme.equals("昆虫")) {
            mediums.add(SpeciesDetailVO.Transmission.Medium.builder().name("成虫迁飞").type("Vector").method("自然扩散").build());
            mediums.add(SpeciesDetailVO.Transmission.Medium.builder().name("苗木调运").type("Human").method("人为传播").build());
        } else {
            mediums.add(SpeciesDetailVO.Transmission.Medium.builder().name("风雨传播").type("Natural").method("孢子传播").build());
            mediums.add(SpeciesDetailVO.Transmission.Medium.builder().name("伤口侵染").type("Human").method("伤口侵染").build());
        }

        String stages = theme.equals("昆虫") ? "卵、幼虫、蛹、成虫" : theme.equals("真菌") ? "侵染、潜育、扩展、产孢" : "卵、幼虫、成虫、扩散型";

        detailMap.put(id, SpeciesDetailVO.builder()
                .id(id).chineseName(cn).scientificName(sn).authorship(au).status(stt).statusText(st)
                .iconClass("fa-microscope").englishName(en)
                .englishAbbr(en.substring(0, Math.min(3, en.length())).toUpperCase())
                .taxonomicUnit("Species")
                .biology(SpeciesDetailVO.Biology.builder()
                        .properties(cn + "以主要活动阶段危害林木。")
                        .stages(stages)
                        .visibility("可结合现场症状与实验室检测综合判定").build())
                .morphology(SpeciesDetailVO.Morphology.builder()
                        .characteristics("形态具有典型特征，可结合现场症状与实验室检测综合判定。")
                        .detectionMethods(Arrays.asList("现场症状调查", "诱捕监测", "实验室形态学鉴定", "分子检测验证")).build())
                .distribution(SpeciesDetailVO.Distribution.builder()
                        .description(cn + "在我国多个重点林区具有监测记录。")
                        .areas(areas)
                        .statusDescription("当前状态为" + st + "。").build())
                .host(SpeciesDetailVO.Host.builder()
                        .rangeDescription(cn + "的寄主范围以" + hosts[0] + "等树种为主。")
                        .hosts(hostItems)
                        .affectedParts(ap)
                        .intensity(stt.equals("confirmed") ? "中到高风险" : "需持续核验").build())
                .transmission(SpeciesDetailVO.Transmission.builder()
                        .mediums(mediums)
                        .pathwayDescription("可通过自然扩散和人为调运传播。")
                        .ecoImpact("大发生时可引起林分生长衰退。").build())
                .management(SpeciesDetailVO.Management.builder()
                        .summary("治理重点在于监测预警和综合防治。")
                        .methods(Arrays.asList("普查监测", "物理防治", "生物防治", "药剂干预"))
                        .remark("该详情为演示数据。").build())
                .references(Arrays.asList(SpeciesDetailVO.Reference.builder()
                        .id(id + "-ref-1")
                        .title(cn + "监测与防控技术研究进展")
                        .authors("DeepForest 项目组")
                        .source("林业有害生物研究")
                        .year(2024)
                        .tags(Arrays.asList("综述", "防治")).build()))
                .taxonomy(taxonomy)
                .images(Arrays.asList(SpeciesDetailVO.Image.builder()
                        .id(id + "-img-1")
                        .src("/deepforest.jpg")
                        .alt(cn + "示意图")
                        .caption(cn + "典型危害症状示意")
                        .type("symptom").build()))
                .otherNames(Arrays.asList(
                        SpeciesDetailVO.OtherName.builder().type("英文名").name(en).build(),
                        SpeciesDetailVO.OtherName.builder().type("系统编号").name(rc).year("2026").build()))
                .metadata(SpeciesDetailVO.Metadata.builder()
                        .creator("DeepForest Mock Engine")
                        .createdAt("2026-03-16")
                        .editor("Claude Code")
                        .updatedAt("2026-03-16")
                        .reviewer("Demo Review")
                        .reviewedAt("2026-03-16").build())
                .build());
    }
}
