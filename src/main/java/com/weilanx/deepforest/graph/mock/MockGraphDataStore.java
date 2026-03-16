package com.weilanx.deepforest.graph.mock;

import com.weilanx.deepforest.graph.dto.GraphCategoryDto;
import com.weilanx.deepforest.graph.dto.GraphDataDto;
import com.weilanx.deepforest.graph.dto.GraphLinkDto;
import com.weilanx.deepforest.graph.dto.GraphNodeDto;
import com.weilanx.deepforest.graph.dto.SpeciesStatusDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 知识图谱模拟数据存储。
 * 使用结构化种子数据生成较大规模的稳定图谱，避免手写海量节点与关系。
 */
@Component
public class MockGraphDataStore {

    private final GraphDataDto graphData = buildGraphData();

    public GraphDataDto getGraphData() {
        return graphData;
    }

    private GraphDataDto buildGraphData() {
        List<TaxonomySeed> taxonomySeeds = createTaxonomySeeds();
        List<LocationSeed> locationSeeds = createLocationSeeds();
        List<HostSeed> hostSeeds = createHostSeeds();
        List<ReferenceSeed> referenceSeeds = createReferenceSeeds();
        List<SpeciesSeed> speciesSeeds = createSpeciesSeeds();
        List<FileSeed> fileSeeds = createFileSeeds(referenceSeeds);
        List<ImageSeed> imageSeeds = createImageSeeds(speciesSeeds);

        List<GraphNodeDto> nodes = new ArrayList<>();
        taxonomySeeds.forEach(seed -> nodes.add(createTaxonomyNode(seed)));
        locationSeeds.forEach(seed -> nodes.add(createLocationNode(seed)));
        hostSeeds.forEach(seed -> nodes.add(createHostNode(seed)));
        referenceSeeds.forEach(seed -> nodes.add(createReferenceNode(seed)));
        fileSeeds.forEach(seed -> nodes.add(createFileNode(seed)));
        imageSeeds.forEach(seed -> nodes.add(createImageNode(seed)));
        speciesSeeds.forEach(seed -> nodes.add(createSpeciesNode(seed)));

        List<GraphLinkDto> links = new ArrayList<>();
        addTaxonomyHierarchyLinks(links, taxonomySeeds);
        addLocationHierarchyLinks(links, locationSeeds);
        addReferenceFileLinks(links, fileSeeds);
        addImageOwnershipLinks(links, imageSeeds);
        addSpeciesLinks(links, speciesSeeds);
        addAdditionalSpeciesRelations(links);

        validateGraph(nodes, links);

        return new GraphDataDto(
                nodes,
                links,
                getCategories(),
                getSpeciesConfirmationStatus(speciesSeeds)
        );
    }

    private List<GraphCategoryDto> getCategories() {
        List<GraphCategoryDto> categories = new ArrayList<>();
        categories.add(new GraphCategoryDto("Species", Map.of("color", "#5dade2")));
        categories.add(new GraphCategoryDto("Reference", Map.of("color", "#48c9b0")));
        categories.add(new GraphCategoryDto("Location", Map.of("color", "#f39c12")));
        categories.add(new GraphCategoryDto("Taxonomy", Map.of("color", "#e74c3c")));
        categories.add(new GraphCategoryDto("Host", Map.of("color", "#a569bd")));
        categories.add(new GraphCategoryDto("File", Map.of("color", "#1abc9c")));
        categories.add(new GraphCategoryDto("Image", Map.of("color", "#3498db")));
        return categories;
    }

    private List<TaxonomySeed> createTaxonomySeeds() {
        return List.of(
                taxonomy("tx-001", "松材线虫科\nAphelenchoididae", 10, "Family", "Aphelenchoididae", null),
                taxonomy("tx-002", "伞滑刃属\nBursaphelenchus", 9, "Genus", "Bursaphelenchus", "tx-001"),
                taxonomy("tx-003", "天牛科\nCerambycidae", 10, "Family", "Cerambycidae", null),
                taxonomy("tx-004", "松墨天牛属\nMonochamus", 9, "Genus", "Monochamus", "tx-003"),
                taxonomy("tx-005", "灯蛾科\nErebidae", 9, "Family", "Erebidae", null),
                taxonomy("tx-006", "毒蛾属\nLymantria", 8, "Genus", "Lymantria", "tx-005"),
                taxonomy("tx-007", "枯叶蛾科\nLasiocampidae", 9, "Family", "Lasiocampidae", null),
                taxonomy("tx-008", "松毛虫属\nDendrolimus", 8, "Genus", "Dendrolimus", "tx-007"),
                taxonomy("tx-009", "小蠹亚科\nScolytinae", 9, "Subfamily", "Scolytinae", null),
                taxonomy("tx-010", "树蜂科\nSiricidae", 8, "Family", "Siricidae", null),
                taxonomy("tx-011", "吉丁虫科\nBuprestidae", 8, "Family", "Buprestidae", null),
                taxonomy("tx-012", "舟蛾科\nNotodontidae", 8, "Family", "Notodontidae", null),
                taxonomy("tx-013", "葡萄座腔菌科\nBotryosphaeriaceae", 8, "Family", "Botryosphaeriaceae", null),
                taxonomy("tx-014", "黑盘孢科\nDrepanopezizaceae", 8, "Family", "Drepanopezizaceae", null),
                taxonomy("tx-015", "栗疫菌科\nCryphonectriaceae", 8, "Family", "Cryphonectriaceae", null),
                taxonomy("tx-016", "长喙壳科\nOphiostomataceae", 8, "Family", "Ophiostomataceae", null),
                taxonomy("tx-017", "赤壳科\nNectriaceae", 8, "Family", "Nectriaceae", null),
                taxonomy("tx-018", "球腔菌科\nMycosphaerellaceae", 8, "Family", "Mycosphaerellaceae", null),
                taxonomy("tx-019", "疫霉科\nPhytophthoraceae", 8, "Family", "Phytophthoraceae", null),
                taxonomy("tx-020", "丝黑穗霉科\nCeratocystidaceae", 8, "Family", "Ceratocystidaceae", null),
                taxonomy("tx-021", "网蝽科\nTingidae", 7, "Family", "Tingidae", null),
                taxonomy("tx-022", "姬小蜂科\nEulophidae", 7, "Family", "Eulophidae", null),
                taxonomy("tx-023", "蜡蚧科\nCoccidae", 7, "Family", "Coccidae", null),
                taxonomy("tx-024", "乳菇多孔菌科\nBondarzewiaceae", 7, "Family", "Bondarzewiaceae", null),
                taxonomy("tx-025", "栎褐天牛属\nMassicus", 7, "Genus", "Massicus", "tx-003"),
                taxonomy("tx-026", "松纵坑切梢小蠹属\nTomicus", 8, "Genus", "Tomicus", "tx-009"),
                taxonomy("tx-027", "八齿小蠹属\nIps", 8, "Genus", "Ips", "tx-009")
        );
    }

    private List<LocationSeed> createLocationSeeds() {
        return List.of(
                location("loc-001", "亚洲", 10, "Continent", "亚洲", null),
                location("loc-002", "欧洲", 9, "Continent", "欧洲", null),
                location("loc-003", "北美", 9, "Continent", "北美", null),
                location("loc-004", "中国", 15, "Country", "中国", "loc-001"),
                location("loc-005", "日本", 10, "Country", "日本", "loc-001"),
                location("loc-006", "韩国", 9, "Country", "韩国", "loc-001"),
                location("loc-007", "美国", 11, "Country", "美国", "loc-003"),
                location("loc-008", "加拿大", 10, "Country", "加拿大", "loc-003"),
                location("loc-009", "俄罗斯", 10, "Country", "俄罗斯", "loc-001"),
                location("loc-010", "德国", 9, "Country", "德国", "loc-002"),
                location("loc-011", "意大利", 9, "Country", "意大利", "loc-002"),
                location("loc-012", "江苏", 11, "Province", "江苏省", "loc-004"),
                location("loc-013", "浙江", 11, "Province", "浙江省", "loc-004"),
                location("loc-014", "安徽", 10, "Province", "安徽省", "loc-004"),
                location("loc-015", "福建", 10, "Province", "福建省", "loc-004"),
                location("loc-016", "广东", 12, "Province", "广东省", "loc-004"),
                location("loc-017", "广西", 11, "Province", "广西壮族自治区", "loc-004"),
                location("loc-018", "云南", 12, "Province", "云南省", "loc-004"),
                location("loc-019", "四川", 10, "Province", "四川省", "loc-004"),
                location("loc-020", "湖南", 10, "Province", "湖南省", "loc-004"),
                location("loc-021", "湖北", 10, "Province", "湖北省", "loc-004"),
                location("loc-022", "山东", 11, "Province", "山东省", "loc-004"),
                location("loc-023", "河北", 10, "Province", "河北省", "loc-004"),
                location("loc-024", "辽宁", 10, "Province", "辽宁省", "loc-004"),
                location("loc-025", "吉林", 9, "Province", "吉林省", "loc-004"),
                location("loc-026", "黑龙江", 9, "Province", "黑龙江省", "loc-004"),
                location("loc-027", "内蒙古", 9, "Province", "内蒙古自治区", "loc-004"),
                location("loc-028", "陕西", 9, "Province", "陕西省", "loc-004")
        );
    }

    private List<HostSeed> createHostSeeds() {
        return List.of(
                host("host-001", "Pinus massoniana", "马尾松", "松科", "primary", 18),
                host("host-002", "Pinus tabuliformis", "油松", "松科", "primary", 18),
                host("host-003", "Pinus densiflora", "赤松", "松科", "primary", 15),
                host("host-004", "Pinus thunbergii", "黑松", "松科", "primary", 15),
                host("host-005", "Pinus sylvestris var. mongolica", "樟子松", "松科", "primary", 14),
                host("host-006", "Pinus yunnanensis", "云南松", "松科", "primary", 15),
                host("host-007", "Pinus kesiya var. langbianensis", "思茅松", "松科", "primary", 13),
                host("host-008", "Pinus elliottii", "湿地松", "松科", "secondary", 14),
                host("host-009", "Picea asperata", "云杉", "松科", "primary", 14),
                host("host-010", "Larix gmelinii", "兴安落叶松", "松科", "secondary", 13),
                host("host-011", "Cunninghamia lanceolata", "杉木", "杉科", "primary", 14),
                host("host-012", "Platycladus orientalis", "侧柏", "柏科", "secondary", 12),
                host("host-013", "Populus tomentosa", "毛白杨", "杨柳科", "primary", 18),
                host("host-014", "Populus deltoides", "美洲黑杨", "杨柳科", "secondary", 16),
                host("host-015", "Salix matsudana", "旱柳", "杨柳科", "secondary", 14),
                host("host-016", "Ulmus pumila", "榆树", "榆科", "primary", 12),
                host("host-017", "Fraxinus chinensis", "白蜡", "木犀科", "primary", 12),
                host("host-018", "Quercus mongolica", "蒙古栎", "壳斗科", "primary", 14),
                host("host-019", "Quercus acutissima", "麻栎", "壳斗科", "secondary", 13),
                host("host-020", "Castanea mollissima", "板栗", "壳斗科", "primary", 13),
                host("host-021", "Platanus × acerifolia", "法桐", "悬铃木科", "primary", 12),
                host("host-022", "Eucalyptus robusta", "桉树", "桃金娘科", "primary", 12),
                host("host-023", "Robinia pseudoacacia", "刺槐", "豆科", "secondary", 11),
                host("host-024", "Morus alba", "桑树", "桑科", "primary", 11),
                host("host-025", "Erythrina variegata", "刺桐", "豆科", "primary", 10)
        );
    }

    private List<ReferenceSeed> createReferenceSeeds() {
        return List.of(
                reference("ref-001", "松材线虫病监测与阻截技术规程", "国家林草局病虫害防治总站", 2023, "技术规程", "quarantine", 14),
                reference("ref-002", "松褐天牛传播生态研究", "张凯, 刘涛, 王兵", 2022, "期刊文章", "biology", 12),
                reference("ref-003", "美国白蛾综合治理手册", "中国林业出版社", 2021, "专著", "control", 13),
                reference("ref-004", "舞毒蛾暴发与林分结构关系", "陈晨, 吴昊", 2020, "期刊文章", "distribution", 10),
                reference("ref-005", "中国松毛虫防治图谱", "李建国", 2022, "图谱", "control", 12),
                reference("ref-006", "光肩星天牛风险评估报告", "林业检疫中心", 2023, "调查报告", "quarantine", 12),
                reference("ref-007", "红脂大小蠹入侵生物学研究", "赵峰, 孙杨", 2021, "期刊文章", "biology", 11),
                reference("ref-008", "云杉八齿小蠹识别与检疫", "东北林业大学森林保护团队", 2022, "技术手册", "taxonomy", 10),
                reference("ref-009", "松纵坑切梢小蠹综合管理技术", "云南省林科院", 2021, "技术手册", "control", 10),
                reference("ref-010", "松树蜂及其共生真菌关系", "蒋楠, 徐磊", 2020, "期刊文章", "biology", 10),
                reference("ref-011", "美国白蜡窄吉丁应急防控指南", "国家林草局生物灾害中心", 2023, "技术指南", "control", 11),
                reference("ref-012", "双条杉天牛监测调查规范", "华东林业调查设计院", 2022, "技术规程", "survey", 9),
                reference("ref-013", "杨树食叶害虫诊断手册", "周玲, 黄涛", 2021, "专著", "taxonomy", 12),
                reference("ref-014", "杨树溃疡病流行规律研究", "侯宁, 孙悦", 2022, "期刊文章", "pathology", 10),
                reference("ref-015", "杨树黑斑病早期诊断方法", "宋雨, 马洁", 2021, "期刊文章", "pathology", 10),
                reference("ref-016", "栗疫病病原生物学与防治", "中国林科院森林保护研究所", 2020, "专著", "pathology", 11),
                reference("ref-017", "榆树荷兰病监测与处置", "欧洲森林健康网络", 2022, "调查报告", "quarantine", 10),
                reference("ref-018", "松树树脂溃疡病检疫分析", "王雪, 何滨", 2023, "期刊文章", "quarantine", 10),
                reference("ref-019", "松针红斑病田间识别手册", "林间病害实验室", 2021, "技术手册", "taxonomy", 9),
                reference("ref-020", "松针褐斑病长期监测报告", "北方针叶林监测联盟", 2022, "调查报告", "distribution", 9),
                reference("ref-021", "苗木疫霉根腐病防控方案", "南方苗圃协会", 2023, "技术方案", "control", 9),
                reference("ref-022", "橡树枯萎病传播与封锁", "北美森林病理学会", 2021, "期刊文章", "quarantine", 9),
                reference("ref-023", "法桐方翅网蝽城市林业管理", "城市园林病虫害中心", 2022, "专著", "control", 9),
                reference("ref-024", "蓝桉姬小蜂入侵风险评估", "热带林业研究中心", 2023, "调查报告", "quarantine", 9),
                reference("ref-025", "刺桐姬小蜂生物学研究", "林森, 陆航", 2021, "期刊文章", "biology", 8),
                reference("ref-026", "红蜡蚧绿色防控技术", "园林植物保护实验室", 2022, "技术手册", "control", 8),
                reference("ref-027", "松根朽病诊断与采伐更新建议", "寒温带森林经营协作组", 2021, "技术报告", "pathology", 9),
                reference("ref-028", "栎树褐天牛危害调查报告", "华北林业调查院", 2023, "调查报告", "survey", 8),
                reference("ref-029", "中国森林病虫害图鉴", "中国林业出版社", 2020, "图鉴", "taxonomy", 14),
                reference("ref-030", "国家林业有害生物检疫名录解读", "国家林草局检疫处", 2023, "标准解读", "quarantine", 13),
                reference("ref-031", "主要针叶树寄主数据库", "森林数据中心", 2024, "数据库", "dataset", 11),
                reference("ref-032", "林业病虫害知识图谱建模实践", "图谱工程团队", 2024, "技术白皮书", "knowledge-graph", 11)
        );
    }

    private List<SpeciesSeed> createSpeciesSeeds() {
        return List.of(
                species("sp-001", "Bursaphelenchus xylophilus", "松材线虫", 25, "confirmed", "线虫动物门",
                        "毁灭性松树病原线虫，能够引起松材线虫病并导致寄主快速枯死。", "tx-002",
                        ids("loc-004", "loc-005", "loc-006", "loc-012", "loc-013", "loc-016"),
                        hostRelations(hostRelation("host-001", "primary", "木质部"),
                                hostRelation("host-004", "primary", "木质部"),
                                hostRelation("host-008", "secondary", "木质部")),
                        ids("ref-001", "ref-002", "ref-029")),
                species("sp-002", "Bursaphelenchus mucronatus", "拟松材线虫", 16, "confirmed", "线虫动物门",
                        "与松材线虫形态相近，常见于东亚针叶林，需要与高致病性种群区分。", "tx-002",
                        ids("loc-004", "loc-005", "loc-006", "loc-024", "loc-025"),
                        hostRelations(hostRelation("host-002", "primary", "木质部"),
                                hostRelation("host-003", "secondary", "木质部")),
                        ids("ref-001", "ref-018")),
                species("sp-003", "Monochamus alternatus", "松褐天牛", 20, "confirmed", "节肢动物门",
                        "松材线虫的重要传播媒介，幼虫主要危害衰弱松木的树干与枝条。", "tx-004",
                        ids("loc-004", "loc-005", "loc-006", "loc-015", "loc-016", "loc-020"),
                        hostRelations(hostRelation("host-001", "primary", "树干"),
                                hostRelation("host-006", "secondary", "树干"),
                                hostRelation("host-008", "secondary", "树干")),
                        ids("ref-002", "ref-006", "ref-030")),
                species("sp-004", "Monochamus saltuarius", "云斑白条天牛", 16, "confirmed", "节肢动物门",
                        "东北地区常见的松墨天牛属种类，可作为松材线虫潜在传播媒介。", "tx-004",
                        ids("loc-004", "loc-006", "loc-009", "loc-024", "loc-025", "loc-026"),
                        hostRelations(hostRelation("host-005", "primary", "树干"),
                                hostRelation("host-009", "secondary", "树干")),
                        ids("ref-002", "ref-030")),
                species("sp-005", "Hyphantria cunea", "美国白蛾", 22, "confirmed", "节肢动物门",
                        "世界性检疫害虫，幼虫结网取食，寄主范围广，对阔叶树危害明显。", "tx-005",
                        ids("loc-004", "loc-007", "loc-008", "loc-022", "loc-023", "loc-024"),
                        hostRelations(hostRelation("host-013", "primary", "叶片"),
                                hostRelation("host-015", "secondary", "叶片"),
                                hostRelation("host-023", "secondary", "叶片")),
                        ids("ref-003", "ref-029", "ref-030")),
                species("sp-006", "Lymantria dispar", "舞毒蛾", 19, "confirmed", "节肢动物门",
                        "典型暴食性食叶害虫，暴发期可造成大面积阔叶林失叶。", "tx-006",
                        ids("loc-004", "loc-009", "loc-010", "loc-018", "loc-027"),
                        hostRelations(hostRelation("host-018", "primary", "叶片"),
                                hostRelation("host-019", "primary", "叶片"),
                                hostRelation("host-013", "secondary", "叶片")),
                        ids("ref-004", "ref-029")),
                species("sp-007", "Dendrolimus punctatus", "马尾松毛虫", 18, "confirmed", "节肢动物门",
                        "中国南方松林主要食叶害虫之一，常对马尾松纯林形成持续压力。", "tx-008",
                        ids("loc-004", "loc-015", "loc-016", "loc-017", "loc-020"),
                        hostRelations(hostRelation("host-001", "primary", "针叶"),
                                hostRelation("host-008", "secondary", "针叶")),
                        ids("ref-005", "ref-029")),
                species("sp-008", "Dendrolimus tabulaeformis", "油松毛虫", 17, "confirmed", "节肢动物门",
                        "华北地区常见松林食叶害虫，偏好油松、樟子松等针叶寄主。", "tx-008",
                        ids("loc-004", "loc-022", "loc-023", "loc-027", "loc-028"),
                        hostRelations(hostRelation("host-002", "primary", "针叶"),
                                hostRelation("host-005", "secondary", "针叶")),
                        ids("ref-005", "ref-029")),
                species("sp-009", "Anoplophora glabripennis", "光肩星天牛", 21, "confirmed", "节肢动物门",
                        "重要木质部蛀干害虫，寄主多样，国际贸易木包装材料传播风险高。", "tx-003",
                        ids("loc-004", "loc-007", "loc-012", "loc-022", "loc-023"),
                        hostRelations(hostRelation("host-013", "primary", "树干"),
                                hostRelation("host-017", "primary", "树干"),
                                hostRelation("host-021", "secondary", "树干")),
                        ids("ref-006", "ref-030")),
                species("sp-010", "Apriona germari", "桑天牛", 15, "confirmed", "节肢动物门",
                        "以蛀干方式危害桑树、杨树等阔叶木本植物。", "tx-003",
                        ids("loc-004", "loc-016", "loc-017", "loc-018"),
                        hostRelations(hostRelation("host-024", "primary", "树干"),
                                hostRelation("host-013", "secondary", "树干")),
                        ids("ref-012", "ref-029")),
                species("sp-011", "Dendroctonus valens", "红脂大小蠹", 17, "confirmed", "节肢动物门",
                        "典型树皮小蠹，可在受压松树上形成高密度危害。", "tx-009",
                        ids("loc-004", "loc-007", "loc-022", "loc-024", "loc-028"),
                        hostRelations(hostRelation("host-001", "primary", "树皮"),
                                hostRelation("host-002", "primary", "树皮"),
                                hostRelation("host-005", "secondary", "树皮")),
                        ids("ref-007", "ref-030")),
                species("sp-012", "Ips typographus", "云杉八齿小蠹", 16, "confirmed", "节肢动物门",
                        "欧亚寒温带云杉林关键害虫，可在风倒木和立木间快速扩散。", "tx-027",
                        ids("loc-004", "loc-009", "loc-010", "loc-025", "loc-026"),
                        hostRelations(hostRelation("host-009", "primary", "树皮"),
                                hostRelation("host-010", "secondary", "树皮")),
                        ids("ref-008", "ref-030")),
                species("sp-013", "Tomicus piniperda", "欧洲松梢小蠹", 15, "confirmed", "节肢动物门",
                        "以松梢和树干双重危害见长，常降低针叶林年生长量。", "tx-026",
                        ids("loc-004", "loc-010", "loc-011", "loc-024", "loc-027"),
                        hostRelations(hostRelation("host-003", "primary", "梢部"),
                                hostRelation("host-004", "primary", "梢部"),
                                hostRelation("host-005", "secondary", "梢部")),
                        ids("ref-009", "ref-030")),
                species("sp-014", "Tomicus yunnanensis", "云南松纵坑切梢小蠹", 14, "confirmed", "节肢动物门",
                        "西南山地针叶林重要蛀干害虫，偏好云南松和思茅松。", "tx-026",
                        ids("loc-004", "loc-017", "loc-018", "loc-019"),
                        hostRelations(hostRelation("host-006", "primary", "梢部"),
                                hostRelation("host-007", "primary", "梢部")),
                        ids("ref-009", "ref-029")),
                species("sp-015", "Sirex noctilio", "松树蜂", 17, "confirmed", "节肢动物门",
                        "入侵性木材害虫，产卵同时接种共生真菌，造成针叶树枯萎。", "tx-010",
                        ids("loc-004", "loc-007", "loc-010", "loc-025", "loc-026", "loc-027"),
                        hostRelations(hostRelation("host-005", "primary", "木质部"),
                                hostRelation("host-006", "secondary", "木质部"),
                                hostRelation("host-008", "secondary", "木质部")),
                        ids("ref-010", "ref-030")),
                species("sp-016", "Agrilus planipennis", "美国白蜡窄吉丁", 18, "confirmed", "节肢动物门",
                        "白蜡属树木毁灭性害虫，蛀食形成层并迅速削弱树势。", "tx-011",
                        ids("loc-004", "loc-007", "loc-008", "loc-024", "loc-025"),
                        hostRelations(hostRelation("host-017", "primary", "形成层")),
                        ids("ref-011", "ref-030")),
                species("sp-017", "Semanotus bifasciatus", "双条杉天牛", 14, "confirmed", "节肢动物门",
                        "杉柏类木材害虫，多在衰弱木和采伐剩余物中发育。", "tx-003",
                        ids("loc-004", "loc-005", "loc-012", "loc-013", "loc-015"),
                        hostRelations(hostRelation("host-011", "primary", "树干"),
                                hostRelation("host-012", "secondary", "树干")),
                        ids("ref-012", "ref-029")),
                species("sp-018", "Calliteara horsfieldii", "杨毒蛾", 14, "confirmed", "节肢动物门",
                        "杨树人工林常见食叶害虫，幼虫食量大且群体发生明显。", "tx-005",
                        ids("loc-004", "loc-021", "loc-022", "loc-023"),
                        hostRelations(hostRelation("host-013", "primary", "叶片"),
                                hostRelation("host-014", "secondary", "叶片")),
                        ids("ref-013", "ref-029")),
                species("sp-019", "Micromelalopha troglodyta", "杨小舟蛾", 13, "confirmed", "节肢动物门",
                        "杨柳类苗木和幼林期常见食叶害虫。", "tx-012",
                        ids("loc-004", "loc-012", "loc-014", "loc-022"),
                        hostRelations(hostRelation("host-013", "primary", "叶片"),
                                hostRelation("host-014", "secondary", "叶片"),
                                hostRelation("host-015", "secondary", "叶片")),
                        ids("ref-013", "ref-029")),
                species("sp-020", "Clostera anachoreta", "杨扇舟蛾", 13, "confirmed", "节肢动物门",
                        "杨树叶部害虫，发生期与苗圃经营措施高度相关。", "tx-012",
                        ids("loc-004", "loc-012", "loc-014", "loc-021"),
                        hostRelations(hostRelation("host-013", "primary", "叶片"),
                                hostRelation("host-014", "secondary", "叶片")),
                        ids("ref-013", "ref-029")),
                species("sp-021", "Botryosphaeria dothidea", "杨树溃疡病菌", 15, "confirmed", "真菌界",
                        "可引起枝干溃疡、梢枯和树势衰退，是杨树病害调查中的高频病原。", "tx-013",
                        ids("loc-004", "loc-012", "loc-021", "loc-022"),
                        hostRelations(hostRelation("host-013", "primary", "枝干"),
                                hostRelation("host-014", "secondary", "枝干")),
                        ids("ref-014", "ref-029")),
                species("sp-022", "Marssonina brunnea", "杨树黑斑病菌", 14, "confirmed", "真菌界",
                        "主要危害杨树叶片，重病年可造成提前落叶。", "tx-014",
                        ids("loc-004", "loc-012", "loc-013", "loc-014"),
                        hostRelations(hostRelation("host-013", "primary", "叶片"),
                                hostRelation("host-014", "secondary", "叶片")),
                        ids("ref-015", "ref-029")),
                species("sp-023", "Cryphonectria parasitica", "栗疫病菌", 16, "confirmed", "真菌界",
                        "全球重要树木病原，主要侵染板栗枝干形成致死性溃疡。", "tx-015",
                        ids("loc-004", "loc-007", "loc-011", "loc-028"),
                        hostRelations(hostRelation("host-020", "primary", "枝干"),
                                hostRelation("host-018", "secondary", "枝干")),
                        ids("ref-016", "ref-030")),
                species("sp-024", "Ophiostoma novo-ulmi", "榆树荷兰病病原", 15, "pending", "真菌界",
                        "榆树维管束病害病原，常经媒介昆虫和带病木材传播。", "tx-016",
                        ids("loc-004", "loc-007", "loc-010", "loc-011"),
                        hostRelations(hostRelation("host-016", "primary", "导管")),
                        ids("ref-017", "ref-030")),
                species("sp-025", "Fusarium circinatum", "松树树脂溃疡病菌", 15, "confirmed", "真菌界",
                        "能够诱导树脂流出与梢枯，是国际松树种苗检疫重点病原。", "tx-017",
                        ids("loc-004", "loc-005", "loc-007", "loc-016", "loc-018"),
                        hostRelations(hostRelation("host-001", "secondary", "枝梢"),
                                hostRelation("host-002", "secondary", "枝梢"),
                                hostRelation("host-004", "primary", "枝梢")),
                        ids("ref-018", "ref-030")),
                species("sp-026", "Dothistroma septosporum", "松针红斑病菌", 14, "confirmed", "真菌界",
                        "针叶出现带状红斑并脱落，常在高湿条件下加重。", "tx-018",
                        ids("loc-004", "loc-007", "loc-010", "loc-024", "loc-025"),
                        hostRelations(hostRelation("host-004", "primary", "针叶"),
                                hostRelation("host-005", "secondary", "针叶"),
                                hostRelation("host-008", "secondary", "针叶")),
                        ids("ref-019", "ref-031")),
                species("sp-027", "Lecanosticta acicola", "松针褐斑病菌", 14, "confirmed", "真菌界",
                        "常与其他针叶病共同出现，造成幼林生长停滞。", "tx-018",
                        ids("loc-004", "loc-007", "loc-022", "loc-023"),
                        hostRelations(hostRelation("host-001", "primary", "针叶"),
                                hostRelation("host-004", "secondary", "针叶"),
                                hostRelation("host-008", "secondary", "针叶")),
                        ids("ref-020", "ref-031")),
                species("sp-028", "Phytophthora cinnamomi", "疫霉根腐病菌", 13, "confirmed", "卵菌界",
                        "苗圃和山地更新地常见土传病原，可导致根系褐变和整株衰败。", "tx-019",
                        ids("loc-004", "loc-011", "loc-016", "loc-018"),
                        hostRelations(hostRelation("host-018", "secondary", "根系"),
                                hostRelation("host-020", "primary", "根系")),
                        ids("ref-021", "ref-031")),
                species("sp-029", "Ceratocystis fagacearum", "橡树枯萎病菌", 14, "pending", "真菌界",
                        "壳斗科树木高风险病原，病害监测与封锁对城市森林尤为关键。", "tx-020",
                        ids("loc-004", "loc-007", "loc-022", "loc-023"),
                        hostRelations(hostRelation("host-018", "primary", "导管"),
                                hostRelation("host-019", "primary", "导管")),
                        ids("ref-022", "ref-031")),
                species("sp-030", "Corythucha ciliata", "法桐方翅网蝽", 13, "confirmed", "节肢动物门",
                        "城市行道树高频害虫，主要在法桐叶背吸食取汁并形成褪绿斑。", "tx-021",
                        ids("loc-004", "loc-011", "loc-012", "loc-013", "loc-022"),
                        hostRelations(hostRelation("host-021", "primary", "叶片")),
                        ids("ref-023", "ref-029")),
                species("sp-031", "Leptocybe invasa", "蓝桉姬小蜂", 13, "confirmed", "节肢动物门",
                        "可诱导桉树嫩梢和叶柄形成虫瘿，影响苗木整齐度与生长势。", "tx-022",
                        ids("loc-004", "loc-007", "loc-016", "loc-017", "loc-018"),
                        hostRelations(hostRelation("host-022", "primary", "嫩梢")),
                        ids("ref-024", "ref-031")),
                species("sp-032", "Quadrastichus erythrinae", "刺桐姬小蜂", 12, "pending", "节肢动物门",
                        "热带沿海地区刺桐属植物的重要虫瘿害虫。", "tx-022",
                        ids("loc-004", "loc-005", "loc-015", "loc-016", "loc-017"),
                        hostRelations(hostRelation("host-025", "primary", "叶柄")),
                        ids("ref-025", "ref-031")),
                species("sp-033", "Ceroplastes rubens", "红蜡蚧", 12, "questionable", "节肢动物门",
                        "广食性刺吸式害虫，在部分园林和防护林树种上可形成局部危害。", "tx-023",
                        ids("loc-004", "loc-005", "loc-012", "loc-013"),
                        hostRelations(hostRelation("host-021", "secondary", "枝条"),
                                hostRelation("host-025", "secondary", "枝条")),
                        ids("ref-026", "ref-029")),
                species("sp-034", "Heterobasidion annosum", "松根朽病菌", 14, "confirmed", "真菌界",
                        "根部和基部腐朽病原，常沿伐桩和根系传播。", "tx-024",
                        ids("loc-004", "loc-009", "loc-010", "loc-025", "loc-026"),
                        hostRelations(hostRelation("host-009", "primary", "根系"),
                                hostRelation("host-010", "secondary", "根系"),
                                hostRelation("host-011", "secondary", "根系")),
                        ids("ref-027", "ref-031")),
                species("sp-035", "Massicus raddei", "栎树褐天牛", 13, "pending", "节肢动物门",
                        "壳斗科树木蛀干害虫，近年在局部栎林中出现抬头趋势。", "tx-025",
                        ids("loc-004", "loc-009", "loc-023", "loc-024", "loc-028"),
                        hostRelations(hostRelation("host-018", "primary", "树干"),
                                hostRelation("host-019", "secondary", "树干")),
                        ids("ref-028", "ref-029")),
                species("sp-036", "Clostera anastomosis", "杨二尾舟蛾", 12, "questionable", "节肢动物门",
                        "杨树食叶害虫，通常在幼林和苗圃内形成局部危害。", "tx-012",
                        ids("loc-004", "loc-009", "loc-012", "loc-014", "loc-022"),
                        hostRelations(hostRelation("host-013", "primary", "叶片"),
                                hostRelation("host-014", "secondary", "叶片")),
                        ids("ref-013", "ref-029"))
        );
    }

    private List<FileSeed> createFileSeeds(List<ReferenceSeed> references) {
        List<FileSeed> files = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            ReferenceSeed reference = references.get(i);
            String extension = switch (reference.refType()) {
                case "数据库" -> "xlsx";
                case "技术白皮书" -> "json";
                default -> "pdf";
            };
            String fileType = extension.toUpperCase(Locale.ROOT);
            String fileId = String.format(Locale.ROOT, "file-%03d", i + 1);
            String name = reference.title() + "." + extension;
            String url = "/mock/graph/files/" + reference.id() + "." + extension;
            String fileSize = String.format(Locale.ROOT, "%.1f MB", 1.2 + (i % 6) * 0.7);
            files.add(new FileSeed(fileId, reference.id(), name, fileType, fileSize, url, 4 + (i % 3)));
        }
        return files;
    }

    private List<ImageSeed> createImageSeeds(List<SpeciesSeed> speciesSeeds) {
        List<ImageSeed> images = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            SpeciesSeed species = speciesSeeds.get(i);
            String imageId = String.format(Locale.ROOT, "img-%03d", i + 1);
            String title = species.chineseName() + "现场图";
            String description = species.chineseName() + "相关形态或危害症状的现场记录图像";
            String imagePath = "/mock/graph/images/" + species.id() + ".jpg";
            images.add(new ImageSeed(imageId, species.id(), title, imagePath, description, 4 + (i % 2)));
        }
        return images;
    }

    private void addTaxonomyHierarchyLinks(List<GraphLinkDto> links, List<TaxonomySeed> taxonomySeeds) {
        for (TaxonomySeed taxonomySeed : taxonomySeeds) {
            if (taxonomySeed.parentId() != null) {
                links.add(createLink(taxonomySeed.id(), taxonomySeed.parentId(), "BELONGS_TO", null));
            }
        }
    }

    private void addLocationHierarchyLinks(List<GraphLinkDto> links, List<LocationSeed> locationSeeds) {
        for (LocationSeed locationSeed : locationSeeds) {
            if (locationSeed.parentId() != null) {
                links.add(createLink(locationSeed.id(), locationSeed.parentId(), "PART_OF", null));
            }
        }
    }

    private void addReferenceFileLinks(List<GraphLinkDto> links, List<FileSeed> fileSeeds) {
        for (FileSeed fileSeed : fileSeeds) {
            links.add(createLink(fileSeed.referenceId(), fileSeed.id(), "HAS_FILE", null));
        }
    }

    private void addImageOwnershipLinks(List<GraphLinkDto> links, List<ImageSeed> imageSeeds) {
        for (ImageSeed imageSeed : imageSeeds) {
            links.add(createLink(imageSeed.speciesId(), imageSeed.id(), "HAS_IMAGE", null));
        }
    }

    private void addSpeciesLinks(List<GraphLinkDto> links, List<SpeciesSeed> speciesSeeds) {
        for (SpeciesSeed speciesSeed : speciesSeeds) {
            links.add(createLink(speciesSeed.id(), speciesSeed.taxonomyId(), "IS_CLASSIFIED_AS", null));
            for (String locationId : speciesSeed.locationIds()) {
                links.add(createLink(speciesSeed.id(), locationId, "DISTRIBUTED_IN", "reported"));
            }
            for (HostRelationSeed hostRelation : speciesSeed.hostRelations()) {
                links.add(createHostsOnLink(speciesSeed.id(), hostRelation.hostId(), hostRelation.interaction(), hostRelation.parts()));
            }
            for (String referenceId : speciesSeed.referenceIds()) {
                links.add(createLink(speciesSeed.id(), referenceId, "MENTIONED_IN", null));
            }
        }
    }

    private void addAdditionalSpeciesRelations(List<GraphLinkDto> links) {
        links.add(createLink("sp-001", "sp-003", "TRANSMITTED_BY", null));
        links.add(createLink("sp-001", "sp-004", "TRANSMITTED_BY", null));
        links.add(createLink("sp-002", "sp-003", "TRANSMITTED_BY", null));
        links.add(createLink("sp-011", "sp-013", "CO_OCCURS_WITH", null));
        links.add(createLink("sp-012", "sp-014", "CO_OCCURS_WITH", null));
        links.add(createLink("sp-019", "sp-020", "CO_OCCURS_WITH", null));
        links.add(createLink("sp-021", "sp-022", "CO_OCCURS_WITH", null));
        links.add(createLink("sp-026", "sp-027", "CO_OCCURS_WITH", null));
        links.add(createLink("sp-031", "sp-032", "CO_OCCURS_WITH", null));
        links.add(createLink("sp-009", "sp-035", "SAME_FAMILY_AS", null));
    }

    private List<SpeciesStatusDto> getSpeciesConfirmationStatus(List<SpeciesSeed> speciesSeeds) {
        Map<String, Integer> counters = new LinkedHashMap<>();
        counters.put("已确认", 0);
        counters.put("待审核", 0);
        counters.put("有疑问", 0);
        for (SpeciesSeed speciesSeed : speciesSeeds) {
            String label = switch (speciesSeed.status()) {
                case "pending" -> "待审核";
                case "questionable" -> "有疑问";
                default -> "已确认";
            };
            counters.put(label, counters.get(label) + 1);
        }
        List<SpeciesStatusDto> result = new ArrayList<>();
        counters.forEach((name, value) -> result.add(new SpeciesStatusDto(name, value)));
        return result;
    }

    private void validateGraph(List<GraphNodeDto> nodes, List<GraphLinkDto> links) {
        Map<String, Long> nodeCounts = nodes.stream()
                .collect(Collectors.groupingBy(GraphNodeDto::getId, LinkedHashMap::new, Collectors.counting()));

        List<String> duplicatedNodes = nodeCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();
        if (!duplicatedNodes.isEmpty()) {
            throw new IllegalStateException("Graph mock contains duplicate node ids: " + duplicatedNodes);
        }

        Set<String> nodeIds = new LinkedHashSet<>(nodeCounts.keySet());
        List<String> brokenLinks = new ArrayList<>();
        Set<String> duplicatedLinks = new LinkedHashSet<>();
        Set<String> linkKeys = new LinkedHashSet<>();

        for (GraphLinkDto link : links) {
            if (!nodeIds.contains(link.getSource()) || !nodeIds.contains(link.getTarget())) {
                brokenLinks.add(link.getSource() + "->" + link.getTarget());
            }
            String type = Objects.toString(link.getDetails().get("type"), "UNKNOWN");
            String linkKey = link.getSource() + "|" + type + "|" + link.getTarget();
            if (!linkKeys.add(linkKey)) {
                duplicatedLinks.add(linkKey);
            }
        }

        if (!brokenLinks.isEmpty()) {
            throw new IllegalStateException("Graph mock contains broken links: " + brokenLinks);
        }
        if (!duplicatedLinks.isEmpty()) {
            throw new IllegalStateException("Graph mock contains duplicate links: " + duplicatedLinks);
        }
    }

    private GraphNodeDto createSpeciesNode(SpeciesSeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.chineseName() + "\n" + abbreviateScientificName(seed.scientificName()));
        node.setValue(seed.value());
        node.setCategory(0);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Species");
        details.put("guid", seed.id());
        details.put("scientificName", seed.scientificName());
        details.put("chineseName", seed.chineseName());
        details.put("classification", seed.classification());
        details.put("status", seed.status());
        details.put("desc", seed.description());
        details.put("taxonomyId", seed.taxonomyId());
        node.setDetails(details);
        return node;
    }

    private GraphNodeDto createTaxonomyNode(TaxonomySeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.displayName());
        node.setValue(seed.value());
        node.setCategory(3);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Taxonomy");
        details.put("level", seed.level());
        details.put("name", seed.taxonName());
        if (seed.parentId() != null) {
            details.put("parentId", seed.parentId());
        }
        node.setDetails(details);
        return node;
    }

    private GraphNodeDto createLocationNode(LocationSeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.displayName());
        node.setValue(seed.value());
        node.setCategory(2);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Location");
        details.put("level", seed.level());
        details.put("name", seed.locationName());
        if (seed.parentId() != null) {
            details.put("parentId", seed.parentId());
        }
        node.setDetails(details);
        return node;
    }

    private GraphNodeDto createHostNode(HostSeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.chineseName() + "\n" + abbreviateScientificName(seed.scientificName()));
        node.setValue(seed.value());
        node.setCategory(4);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Host");
        details.put("scientificName", seed.scientificName());
        details.put("chineseName", seed.chineseName());
        details.put("classification", seed.classification());
        details.put("hostType", seed.hostType());
        node.setDetails(details);
        return node;
    }

    private GraphNodeDto createReferenceNode(ReferenceSeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.title() + "\n(" + seed.year() + ")");
        node.setValue(seed.value());
        node.setCategory(1);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Reference");
        details.put("guid", seed.id());
        details.put("title", seed.title());
        details.put("authors", seed.authors());
        details.put("year", seed.year());
        details.put("refType", seed.refType());
        details.put("topic", seed.topic());
        node.setDetails(details);
        return node;
    }

    private GraphNodeDto createFileNode(FileSeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.name());
        node.setValue(seed.value());
        node.setCategory(5);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "File");
        details.put("guid", seed.id());
        details.put("name", seed.name());
        details.put("fileType", seed.fileType());
        details.put("fileSize", seed.fileSize());
        details.put("url", seed.url());
        details.put("referenceId", seed.referenceId());
        node.setDetails(details);
        return node;
    }

    private GraphNodeDto createImageNode(ImageSeed seed) {
        GraphNodeDto node = new GraphNodeDto();
        node.setId(seed.id());
        node.setName(seed.title());
        node.setValue(seed.value());
        node.setCategory(6);
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Image");
        details.put("guid", seed.id());
        details.put("title", seed.title());
        details.put("imagePath", seed.imagePath());
        details.put("description", seed.description());
        details.put("speciesId", seed.speciesId());
        node.setDetails(details);
        return node;
    }

    private GraphLinkDto createLink(String source, String target, String type, String status) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", type);
        if (status != null) {
            details.put("status", status);
        }
        return new GraphLinkDto(source, target, details, null);
    }

    private GraphLinkDto createHostsOnLink(String source, String target, String interaction, String parts) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "HOSTS_ON");
        details.put("interaction", interaction);
        details.put("parts", parts);
        Map<String, Object> lineStyle = new LinkedHashMap<>();
        lineStyle.put("color", "#f1948a");
        lineStyle.put("width", 1.5);
        return new GraphLinkDto(source, target, details, lineStyle);
    }

    private String abbreviateScientificName(String scientificName) {
        String[] parts = scientificName.trim().split("\\s+");
        if (parts.length < 2 || parts[0].isEmpty()) {
            return scientificName;
        }
        return parts[0].charAt(0) + ". " + String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
    }

    private List<String> ids(String... ids) {
        return List.of(ids);
    }

    private List<HostRelationSeed> hostRelations(HostRelationSeed... relations) {
        return List.of(relations);
    }

    private HostRelationSeed hostRelation(String hostId, String interaction, String parts) {
        return new HostRelationSeed(hostId, interaction, parts);
    }

    private TaxonomySeed taxonomy(String id, String displayName, int value, String level, String taxonName, String parentId) {
        return new TaxonomySeed(id, displayName, value, level, taxonName, parentId);
    }

    private LocationSeed location(String id, String displayName, int value, String level, String locationName, String parentId) {
        return new LocationSeed(id, displayName, value, level, locationName, parentId);
    }

    private HostSeed host(String id, String scientificName, String chineseName, String classification, String hostType, int value) {
        return new HostSeed(id, scientificName, chineseName, classification, hostType, value);
    }

    private ReferenceSeed reference(String id, String title, String authors, int year, String refType, String topic, int value) {
        return new ReferenceSeed(id, title, authors, year, refType, topic, value);
    }

    private SpeciesSeed species(String id, String scientificName, String chineseName, int value, String status,
                                String classification, String description, String taxonomyId,
                                List<String> locationIds, List<HostRelationSeed> hostRelations, List<String> referenceIds) {
        return new SpeciesSeed(id, scientificName, chineseName, value, status, classification, description,
                taxonomyId, locationIds, hostRelations, referenceIds);
    }

    private record TaxonomySeed(String id, String displayName, int value, String level, String taxonName,
                                String parentId) {
    }

    private record LocationSeed(String id, String displayName, int value, String level, String locationName,
                                String parentId) {
    }

    private record HostSeed(String id, String scientificName, String chineseName, String classification,
                            String hostType, int value) {
    }

    private record ReferenceSeed(String id, String title, String authors, int year, String refType, String topic,
                                 int value) {
    }

    private record FileSeed(String id, String referenceId, String name, String fileType, String fileSize,
                            String url, int value) {
    }

    private record ImageSeed(String id, String speciesId, String title, String imagePath, String description,
                             int value) {
    }

    private record HostRelationSeed(String hostId, String interaction, String parts) {
    }

    private record SpeciesSeed(String id, String scientificName, String chineseName, int value, String status,
                               String classification, String description, String taxonomyId,
                               List<String> locationIds, List<HostRelationSeed> hostRelations,
                               List<String> referenceIds) {
    }
}
