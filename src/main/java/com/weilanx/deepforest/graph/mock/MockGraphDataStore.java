// src/main/java/com/weilanx/deepforest/graph/mock/MockGraphDataStore.java
package com.weilanx.deepforest.graph.mock;

import com.weilanx.deepforest.graph.dto.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 知识图谱模拟数据存储
 * 包含丰富的林业病虫害知识图谱数据
 */
@Component
public class MockGraphDataStore {

    /**
     * 获取完整的知识图谱数据
     */
    public GraphDataDto getGraphData() {
        return new GraphDataDto(
                getNodes(),
                getLinks(),
                getCategories(),
                getSpeciesConfirmationStatus()
        );
    }

    /**
     * 获取节点分类
     */
    private List<GraphCategoryDto> getCategories() {
        return Arrays.asList(
                new GraphCategoryDto("Species", Map.of("color", "#5dade2")),
                new GraphCategoryDto("Reference", Map.of("color", "#48c9b0")),
                new GraphCategoryDto("Location", Map.of("color", "#f39c12")),
                new GraphCategoryDto("Taxonomy", Map.of("color", "#e74c3c")),
                new GraphCategoryDto("Host", Map.of("color", "#a569bd")),
                new GraphCategoryDto("File", Map.of("color", "#1abc9c")),
                new GraphCategoryDto("Image", Map.of("color", "#3498db"))
        );
    }

    /**
     * 获取所有节点
     */
    private List<GraphNodeDto> getNodes() {
        List<GraphNodeDto> nodes = new ArrayList<>();

        // ============ 物种节点 (Species - category 0) ============
        // 线虫类
        nodes.add(createSpeciesNode("sp-001", "松材线虫\nB. xylophilus", 25, "confirmed",
                "Bursaphelenchus xylophilus", "松材线虫", "线虫动物门 > 线虫纲 > 滑刃目 > 滑刃科 > 伞滑刃属",
                "松材线虫是一种毁灭性的森林病原线虫，主要危害松树，是国际上公认的最重要的森林病害之一。原产于北美，1982年首次在中国南京发现。"));
        nodes.add(createSpeciesNode("sp-002", "拟松材线虫\nB. mucronatus", 15, "confirmed",
                "Bursaphelenchus mucronatus", "拟松材线虫", "线虫动物门 > 线虫纲 > 滑刃目 > 滑刃科 > 伞滑刃属",
                "拟松材线虫与松材线虫形态相似，但致病性较弱，主要分布于亚洲地区。"));
        nodes.add(createSpeciesNode("sp-003", "椰子红环线虫\nB. cocophilus", 12, "confirmed",
                "Bursaphelenchus cocophilus", "椰子红环线虫", "线虫动物门 > 线虫纲 > 滑刃目 > 滑刃科 > 伞滑刃属",
                "椰子红环线虫是椰子和油棕的重要病原，可导致椰子树死亡。"));

        // 昆虫类 - 鳞翅目
        nodes.add(createSpeciesNode("sp-004", "美国白蛾\nH. cunea", 22, "confirmed",
                "Hyphantria cunea", "美国白蛾", "节肢动物门 > 昆虫纲 > 鳞翅目 > 灯蛾科 > 白蛾属",
                "美国白蛾是一种世界性检疫害虫，原产于北美，1979年传入中国。食性杂，可危害300多种植物。"));
        nodes.add(createSpeciesNode("sp-005", "舞毒蛾\nL. dispar", 18, "confirmed",
                "Lymantria dispar", "舞毒蛾", "节肢动物门 > 昆虫纲 > 鳞翅目 > 毒蛾科 > 毒蛾属",
                "舞毒蛾是重要的森林害虫，幼虫取食500多种植物的叶片，是世界性的森林害虫。"));
        nodes.add(createSpeciesNode("sp-006", "松毛虫\nD. punctatus", 16, "confirmed",
                "Dendrolimus punctatus", "马尾松毛虫", "节肢动物门 > 昆虫纲 > 鳞翅目 > 枯叶蛾科 > 松毛虫属",
                "松毛虫是中国南方松林的主要害虫，每年发生2-4代，幼虫取食松针。"));
        nodes.add(createSpeciesNode("sp-007", "杨小舟蛾\nM. troglodyta", 14, "confirmed",
                "Micromelalopha troglodyta", "杨小舟蛾", "节肢动物门 > 昆虫纲 > 鳞翅目 > 舟蛾科 > 小舟蛾属",
                "杨小舟蛾是杨树的重要食叶害虫，在中国广泛分布。"));
        nodes.add(createSpeciesNode("sp-008", "杨扇舟蛾\nC. anachoreta", 13, "confirmed",
                "Clostera anachoreta", "杨扇舟蛾", "节肢动物门 > 昆虫纲 > 鳞翅目 > 舟蛾科 > 扇舟蛾属",
                "杨扇舟蛾幼虫取食杨树、柳树叶片，是城市绿化树种的主要害虫之一。"));

        // 昆虫类 - 鞘翅目
        nodes.add(createSpeciesNode("sp-009", "光肩星天牛\nA. glabripennis", 20, "confirmed",
                "Anoplophora glabripennis", "光肩星天牛", "节肢动物门 > 昆虫纲 > 鞘翅目 > 天牛科 > 星天牛属",
                "光肩星天牛是一种危害极大的蛀干害虫，主要危害杨、柳、榆等多种阔叶树。"));
        nodes.add(createSpeciesNode("sp-010", "松褐天牛\nM. alternatus", 18, "confirmed",
                "Monochamus alternatus", "松褐天牛", "节肢动物门 > 昆虫纲 > 鞘翅目 > 天牛科 > 墨天牛属",
                "松褐天牛是松材线虫的主要传播媒介，其成虫携带线虫在松树间传播。"));
        nodes.add(createSpeciesNode("sp-011", "红脂大小蠹\nD. valens", 15, "confirmed",
                "Dendroctonus valens", "红脂大小蠹", "节肢动物门 > 昆虫纲 > 鞘翅目 > 小蠹科 > 大小蠹属",
                "红脂大小蠹原产于北美，1998年在中国山西首次发现，主要危害油松、华山松等。"));
        nodes.add(createSpeciesNode("sp-012", "杨干象\nC. lapith", 12, "confirmed",
                "Cryptorhynchus lapathi", "杨干象", "节肢动物门 > 昆虫纲 > 鞘翅目 > 象甲科 > 隐喙象属",
                "杨干象是杨树幼苗和幼树的重要蛀干害虫。"));

        // 昆虫类 - 半翅目
        nodes.add(createSpeciesNode("sp-013", "松突圆蚧\nH. pitysophila", 14, "confirmed",
                "Hemiberlesia pitysophila", "松突圆蚧", "节肢动物门 > 昆虫纲 > 半翅目 > 盾蚧科 > 突圆蚧属",
                "松突圆蚧是松树的重要害虫，1982年传入中国，主要危害马尾松。"));
        nodes.add(createSpeciesNode("sp-014", "湿地松粉蚧\nO. acuta", 12, "confirmed",
                "Oracella acuta", "湿地松粉蚧", "节肢动物门 > 昆虫纲 > 半翅目 > 粉蚧科 > 松粉蚧属",
                "湿地松粉蚧原产于美国，1988年传入中国，主要危害湿地松、火炬松等。"));

        // 真菌病害
        nodes.add(createSpeciesNode("sp-015", "松针褐斑病菌\nL. acicola", 14, "confirmed",
                "Lecanosticta acicola", "松针褐斑病菌", "真菌界 > 子囊菌门 > 座囊菌纲 > 球座菌目 > 球座菌科",
                "松针褐斑病菌引起松针褐斑病，是松树的重要病害之一。"));
        nodes.add(createSpeciesNode("sp-016", "杨树溃疡病菌\nB. dothidea", 13, "confirmed",
                "Botryosphaeria dothidea", "杨树溃疡病菌", "真菌界 > 子囊菌门 > 座囊菌纲 > 葡萄座腔菌目 > 葡萄座腔菌科",
                "杨树溃疡病是杨树的常见病害，导致树干溃疡、枯梢。"));
        nodes.add(createSpeciesNode("sp-017", "松枯梢病菌\nS. sapinea", 12, "confirmed",
                "Sphaeropsis sapinea", "松枯梢病菌", "真菌界 > 子囊菌门 > 座囊菌纲 > 球座菌目 > 球座菌科",
                "松枯梢病菌引起松树枯梢病，严重影响松树生长。"));

        // 待审核/有疑问物种
        nodes.add(createSpeciesNode("sp-018", "待审核物种A\nPending sp. A", 8, "pending",
                "Species pending A", "待审核物种A", "待确认",
                "该物种正在审核中，需要进一步鉴定。"));
        nodes.add(createSpeciesNode("sp-019", "有疑问物种B\nQuestionable sp. B", 6, "questionable",
                "Species questionable B", "有疑问物种B", "待确认",
                "该物种的鉴定存在疑问，需要更多标本确认。"));
        nodes.add(createSpeciesNode("sp-020", "新发现物种C\nNew sp. C", 7, "pending",
                "New species C", "新发现物种C", "待确认",
                "新发现的物种，正在描述和鉴定过程中。"));

        // ============ 分类单元节点 (Taxonomy - category 3) ============
        nodes.add(createTaxonomyNode("tx-001", "伞滑刃属\nBursaphelenchus", 8, "Genus", "Bursaphelenchus"));
        nodes.add(createTaxonomyNode("tx-002", "灯蛾科\nArctiidae", 7, "Family", "Arctiidae"));
        nodes.add(createTaxonomyNode("tx-003", "毒蛾科\nLymantriidae", 6, "Family", "Lymantriidae"));
        nodes.add(createTaxonomyNode("tx-004", "天牛科\nCerambycidae", 8, "Family", "Cerambycidae"));
        nodes.add(createTaxonomyNode("tx-005", "小蠹科\nScolytidae", 5, "Family", "Scolytidae"));
        nodes.add(createTaxonomyNode("tx-006", "鳞翅目\nLepidoptera", 10, "Order", "Lepidoptera"));
        nodes.add(createTaxonomyNode("tx-007", "鞘翅目\nColeoptera", 9, "Order", "Coleoptera"));
        nodes.add(createTaxonomyNode("tx-008", "半翅目\nHemiptera", 6, "Order", "Hemiptera"));

        // ============ 地理分布节点 (Location - category 2) ============
        nodes.add(createLocationNode("loc-001", "中国", 15, "Country", "中国"));
        nodes.add(createLocationNode("loc-002", "日本", 10, "Country", "日本"));
        nodes.add(createLocationNode("loc-003", "韩国", 8, "Country", "韩国"));
        nodes.add(createLocationNode("loc-004", "北美", 12, "Continent", "North America"));
        nodes.add(createLocationNode("loc-005", "欧洲", 9, "Continent", "Europe"));
        nodes.add(createLocationNode("loc-006", "东南亚", 7, "Continent", "Southeast Asia"));
        // 中国省份
        nodes.add(createLocationNode("loc-007", "江苏", 12, "Province", "江苏省"));
        nodes.add(createLocationNode("loc-008", "浙江", 11, "Province", "浙江省"));
        nodes.add(createLocationNode("loc-009", "山东", 10, "Province", "山东省"));
        nodes.add(createLocationNode("loc-010", "广东", 13, "Province", "广东省"));
        nodes.add(createLocationNode("loc-011", "云南", 14, "Province", "云南省"));
        nodes.add(createLocationNode("loc-012", "四川", 12, "Province", "四川省"));

        // ============ 寄主植物节点 (Host - category 4) ============
        nodes.add(createHostNode("host-001", "马尾松\nPinus massoniana", 18, "Pinus massoniana", "马尾松", "松科 > 松属", "primary"));
        nodes.add(createHostNode("host-002", "湿地松\nPinus elliottii", 15, "Pinus elliottii", "湿地松", "松科 > 松属", "primary"));
        nodes.add(createHostNode("host-003", "火炬松\nPinus taeda", 12, "Pinus taeda", "火炬松", "松科 > 松属", "primary"));
        nodes.add(createHostNode("host-004", "杨树\nPopulus spp.", 20, "Populus", "杨树", "杨柳科 > 杨属", "primary"));
        nodes.add(createHostNode("host-005", "柳树\nSalix spp.", 14, "Salix", "柳树", "杨柳科 > 柳属", "primary"));
        nodes.add(createHostNode("host-006", "榆树\nUlmus spp.", 10, "Ulmus", "榆树", "榆科 > 榆属", "secondary"));
        nodes.add(createHostNode("host-007", "栎树\nQuercus spp.", 11, "Quercus", "栎树", "壳斗科 > 栎属", "secondary"));
        nodes.add(createHostNode("host-008", "桦树\nBetula spp.", 9, "Betula", "桦树", "桦木科 > 桦木属", "secondary"));
        nodes.add(createHostNode("host-009", "苹果\nMalus domestica", 12, "Malus domestica", "苹果", "蔷薇科 > 苹果属", "agricultural"));
        nodes.add(createHostNode("host-010", "玉米\nZea mays", 8, "Zea mays", "玉米", "禾本科 > 玉米属", "agricultural"));

        // ============ 文献节点 (Reference - category 1) ============
        nodes.add(createReferenceNode("ref-001", "松材线虫研究进展 (2023)", 15,
                "松材线虫研究进展", "张三, 李四, 王五", 2023, "10.1016/j.forestry.2023.001", "期刊文章", "biology"));
        nodes.add(createReferenceNode("ref-002", "美国白蛾防控手册", 12,
                "美国白蛾防控手册", "赵六", 2022, null, "技术报告", "control"));
        nodes.add(createReferenceNode("ref-003", "中国森林昆虫志", 14,
                "中国森林昆虫志", "中国科学院", 2020, "978-7-03-000000-0", "书籍", "taxonomy"));
        nodes.add(createReferenceNode("ref-004", "天牛科分类学研究 (2021)", 11,
                "中国天牛科分类学研究", "钱七, 孙八", 2021, "10.1000/j.entomol.2021.001", "期刊文章", "taxonomy"));
        nodes.add(createReferenceNode("ref-005", "松毛虫生物防治技术", 10,
                "松毛虫生物防治技术研究", "周九", 2022, "10.2000/j.biocontrol.2022.001", "期刊文章", "control"));
        nodes.add(createReferenceNode("ref-006", "杨树病害诊断与防治", 13,
                "杨树病害诊断与防治", "吴十", 2021, "978-7-5038-0000-0", "书籍", "pathology"));
        nodes.add(createReferenceNode("ref-007", "入侵害虫风险评估模型", 9,
                "入侵害虫风险评估模型研究", "郑十一, 王十二", 2023, "10.3000/j.ecology.2023.001", "期刊文章", "ecology"));
        nodes.add(createReferenceNode("ref-008", "森林病虫害监测技术", 8,
                "森林病虫害监测技术研究进展", "李十三", 2022, "10.4000/j.monitoring.2022.001", "会议论文", "monitoring"));

        // ============ 文件节点 (File - category 5) ============
        nodes.add(createFileNode("file-001", "松材线虫研究进展.pdf", 5, "PDF", "2.5 MB", "/files/ref-001.pdf"));
        nodes.add(createFileNode("file-002", "美国白蛾防控手册.pdf", 4, "PDF", "1.8 MB", "/files/ref-002.pdf"));
        nodes.add(createFileNode("file-003", "中国森林昆虫志.pdf", 6, "PDF", "15.2 MB", "/files/ref-003.pdf"));
        nodes.add(createFileNode("file-004", "松毛虫防治数据.xlsx", 3, "XLSX", "0.5 MB", "/files/data-001.xlsx"));
        nodes.add(createFileNode("file-005", "杨树病害图片.zip", 4, "ZIP", "25.0 MB", "/files/images-001.zip"));

        // ============ 图片节点 (Image - category 6) ============
        nodes.add(createImageNode("img-001", "松材线虫成虫", 4, "/images/sp-001-adult.jpg", "松材线虫成虫显微镜照片"));
        nodes.add(createImageNode("img-002", "松材线虫危害状", 5, "/images/sp-001-damage.jpg", "松材线虫危害松树症状"));
        nodes.add(createImageNode("img-003", "美国白蛾成虫", 4, "/images/sp-004-adult.jpg", "美国白蛾成虫照片"));
        nodes.add(createImageNode("img-004", "美国白蛾幼虫", 5, "/images/sp-004-larva.jpg", "美国白蛾幼虫群集照片"));
        nodes.add(createImageNode("img-005", "光肩星天牛成虫", 4, "/images/sp-009-adult.jpg", "光肩星天牛成虫照片"));
        nodes.add(createImageNode("img-006", "天牛危害状", 4, "/images/sp-009-damage.jpg", "光肩星天牛危害状"));
        nodes.add(createImageNode("img-007", "松毛虫幼虫", 4, "/images/sp-006-larva.jpg", "松毛虫幼虫照片"));
        nodes.add(createImageNode("img-008", "杨树溃疡病", 4, "/images/disease-001.jpg", "杨树溃疡病症状"));

        return nodes;
    }

    /**
     * 获取所有关系
     */
    private List<GraphLinkDto> getLinks() {
        List<GraphLinkDto> links = new ArrayList<>();

        // ============ 分类关系 (IS_CLASSIFIED_AS) ============
        links.add(createLink("sp-001", "tx-001", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-002", "tx-001", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-003", "tx-001", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-004", "tx-002", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-005", "tx-003", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-006", "tx-006", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-007", "tx-006", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-008", "tx-006", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-009", "tx-004", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-010", "tx-004", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-011", "tx-005", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-012", "tx-007", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-013", "tx-008", "IS_CLASSIFIED_AS", null));
        links.add(createLink("sp-014", "tx-008", "IS_CLASSIFIED_AS", null));

        // 分类层级关系
        links.add(createLink("tx-001", "tx-006", "BELONGS_TO", null));
        links.add(createLink("tx-002", "tx-006", "BELONGS_TO", null));
        links.add(createLink("tx-003", "tx-006", "BELONGS_TO", null));
        links.add(createLink("tx-004", "tx-007", "BELONGS_TO", null));
        links.add(createLink("tx-005", "tx-007", "BELONGS_TO", null));

        // ============ 地理分布关系 (DISTRIBUTED_IN) ============
        links.add(createLink("sp-001", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-001", "loc-002", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-001", "loc-004", "DISTRIBUTED_IN", "native"));
        links.add(createLink("sp-002", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-002", "loc-002", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-003", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-004", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-004", "loc-004", "DISTRIBUTED_IN", "native"));
        links.add(createLink("sp-005", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-005", "loc-004", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-005", "loc-005", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-006", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-007", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-008", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-009", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-009", "loc-004", "DISTRIBUTED_IN", "native"));
        links.add(createLink("sp-009", "loc-005", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-010", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-010", "loc-002", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-010", "loc-003", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-011", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-011", "loc-004", "DISTRIBUTED_IN", "native"));
        links.add(createLink("sp-015", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-016", "loc-001", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-017", "loc-001", "DISTRIBUTED_IN", "present"));

        // 省份分布
        links.add(createLink("sp-001", "loc-007", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-001", "loc-008", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-001", "loc-009", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-004", "loc-007", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-004", "loc-008", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-004", "loc-010", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-006", "loc-007", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-006", "loc-008", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-006", "loc-009", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-006", "loc-011", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-009", "loc-007", "DISTRIBUTED_IN", "present"));
        links.add(createLink("sp-009", "loc-008", "DISTRIBUTED_IN", "present"));

        // ============ 寄主关系 (HOSTS_ON) ============
        links.add(createHostsOnLink("sp-001", "host-001", "primary", "全株"));
        links.add(createHostsOnLink("sp-001", "host-002", "primary", "全株"));
        links.add(createHostsOnLink("sp-001", "host-003", "secondary", "全株"));
        links.add(createHostsOnLink("sp-004", "host-004", "primary", "叶片"));
        links.add(createHostsOnLink("sp-004", "host-005", "primary", "叶片"));
        links.add(createHostsOnLink("sp-004", "host-006", "secondary", "叶片"));
        links.add(createHostsOnLink("sp-004", "host-007", "secondary", "叶片"));
        links.add(createHostsOnLink("sp-005", "host-004", "primary", "叶片"));
        links.add(createHostsOnLink("sp-005", "host-005", "secondary", "叶片"));
        links.add(createHostsOnLink("sp-005", "host-007", "primary", "叶片"));
        links.add(createHostsOnLink("sp-006", "host-001", "primary", "松针"));
        links.add(createHostsOnLink("sp-006", "host-002", "primary", "松针"));
        links.add(createHostsOnLink("sp-006", "host-003", "primary", "松针"));
        links.add(createHostsOnLink("sp-007", "host-004", "primary", "叶片"));
        links.add(createHostsOnLink("sp-008", "host-004", "primary", "叶片"));
        links.add(createHostsOnLink("sp-008", "host-005", "secondary", "叶片"));
        links.add(createHostsOnLink("sp-009", "host-004", "primary", "树干"));
        links.add(createHostsOnLink("sp-009", "host-005", "primary", "树干"));
        links.add(createHostsOnLink("sp-009", "host-006", "secondary", "树干"));
        links.add(createHostsOnLink("sp-009", "host-008", "secondary", "树干"));
        links.add(createHostsOnLink("sp-010", "host-001", "primary", "树干"));
        links.add(createHostsOnLink("sp-010", "host-002", "primary", "树干"));
        links.add(createHostsOnLink("sp-010", "host-003", "primary", "树干"));
        links.add(createHostsOnLink("sp-011", "host-001", "primary", "树干基部"));
        links.add(createHostsOnLink("sp-015", "host-001", "primary", "松针"));
        links.add(createHostsOnLink("sp-016", "host-004", "primary", "树干"));
        links.add(createHostsOnLink("sp-017", "host-001", "primary", "嫩梢"));
        links.add(createHostsOnLink("sp-017", "host-002", "primary", "嫩梢"));

        // ============ 文献引用关系 (MENTIONED_IN) ============
        links.add(createMentionedInLink("sp-001", "ref-001", "biology"));
        links.add(createMentionedInLink("sp-002", "ref-001", "biology"));
        links.add(createMentionedInLink("sp-004", "ref-002", "control"));
        links.add(createMentionedInLink("sp-004", "ref-003", "taxonomy"));
        links.add(createMentionedInLink("sp-005", "ref-003", "taxonomy"));
        links.add(createMentionedInLink("sp-006", "ref-003", "taxonomy"));
        links.add(createMentionedInLink("sp-006", "ref-005", "control"));
        links.add(createMentionedInLink("sp-009", "ref-003", "taxonomy"));
        links.add(createMentionedInLink("sp-009", "ref-004", "taxonomy"));
        links.add(createMentionedInLink("sp-010", "ref-001", "vector"));
        links.add(createMentionedInLink("sp-010", "ref-003", "taxonomy"));
        links.add(createMentionedInLink("sp-011", "ref-007", "ecology"));
        links.add(createMentionedInLink("sp-015", "ref-001", "pathology"));
        links.add(createMentionedInLink("sp-016", "ref-006", "pathology"));
        links.add(createMentionedInLink("sp-017", "ref-006", "pathology"));
        links.add(createMentionedInLink("host-004", "ref-006", "host"));
        links.add(createMentionedInLink("host-001", "ref-001", "host"));

        // ============ 文件关联关系 (HAS_FILE) ============
        links.add(createLink("ref-001", "file-001", "HAS_FILE", null));
        links.add(createLink("ref-002", "file-002", "HAS_FILE", null));
        links.add(createLink("ref-003", "file-003", "HAS_FILE", null));
        links.add(createLink("ref-005", "file-004", "HAS_FILE", null));
        links.add(createLink("ref-006", "file-005", "HAS_FILE", null));

        // ============ 图片关联关系 (HAS_IMAGE) ============
        links.add(createLink("sp-001", "img-001", "HAS_IMAGE", null));
        links.add(createLink("sp-001", "img-002", "HAS_IMAGE", null));
        links.add(createLink("sp-004", "img-003", "HAS_IMAGE", null));
        links.add(createLink("sp-004", "img-004", "HAS_IMAGE", null));
        links.add(createLink("sp-009", "img-005", "HAS_IMAGE", null));
        links.add(createLink("sp-009", "img-006", "HAS_IMAGE", null));
        links.add(createLink("sp-006", "img-007", "HAS_IMAGE", null));
        links.add(createLink("sp-016", "img-008", "HAS_IMAGE", null));

        // ============ 传播媒介关系 (TRANSMITTED_BY) ============
        links.add(createLink("sp-001", "sp-010", "TRANSMITTED_BY", null));

        // ============ 待审核物种关系 ============
        links.add(createLink("sp-018", "loc-001", "DISTRIBUTED_IN", null));
        links.add(createLink("sp-018", "host-004", "HOSTS_ON", null));
        links.add(createLink("sp-019", "loc-007", "DISTRIBUTED_IN", null));
        links.add(createLink("sp-019", "host-001", "HOSTS_ON", null));
        links.add(createLink("sp-020", "loc-011", "DISTRIBUTED_IN", null));
        links.add(createLink("sp-020", "tx-004", "IS_CLASSIFIED_AS", null));

        return links;
    }

    /**
     * 获取物种确认状态统计
     */
    private List<SpeciesStatusDto> getSpeciesConfirmationStatus() {
        return Arrays.asList(
                new SpeciesStatusDto("已确认", 17),
                new SpeciesStatusDto("待审核", 2),
                new SpeciesStatusDto("有疑问", 1)
        );
    }

    // ============ 辅助方法 ============

    private GraphNodeDto createSpeciesNode(String id, String name, int value, String status,
                                           String scientificName, String chineseName, String classification, String desc) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Species");
        details.put("guid", id);
        details.put("scientificName", scientificName);
        details.put("chineseName", chineseName);
        details.put("classification", classification);
        details.put("status", status);
        details.put("desc", desc);
        return new GraphNodeDto(id, name, value, 0, details);
    }

    private GraphNodeDto createTaxonomyNode(String id, String name, int value, String level, String taxonName) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Taxonomy");
        details.put("level", level);
        details.put("name", taxonName);
        return new GraphNodeDto(id, name, value, 3, details);
    }

    private GraphNodeDto createLocationNode(String id, String name, int value, String level, String locationName) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Location");
        details.put("level", level);
        details.put("name", locationName);
        return new GraphNodeDto(id, name, value, 2, details);
    }

    private GraphNodeDto createHostNode(String id, String name, int value, String scientificName,
                                        String chineseName, String classification, String hostType) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Host");
        details.put("scientificName", scientificName);
        details.put("chineseName", chineseName);
        details.put("classification", classification);
        details.put("hostType", hostType);
        return new GraphNodeDto(id, name, value, 4, details);
    }

    private GraphNodeDto createReferenceNode(String id, String name, int value, String title,
                                             String authors, int year, String doi, String refType, String topic) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Reference");
        details.put("guid", id);
        details.put("title", title);
        details.put("authors", authors);
        details.put("year", year);
        if (doi != null) details.put("doi", doi);
        details.put("refType", refType);
        details.put("topic", topic);
        return new GraphNodeDto(id, name, value, 1, details);
    }

    private GraphNodeDto createFileNode(String id, String name, int value, String fileType, String fileSize, String url) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "File");
        details.put("guid", id);
        details.put("name", name);
        details.put("fileType", fileType);
        details.put("fileSize", fileSize);
        details.put("url", url);
        return new GraphNodeDto(id, name, value, 5, details);
    }

    private GraphNodeDto createImageNode(String id, String name, int value, String imagePath, String description) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "Image");
        details.put("guid", id);
        details.put("title", name);
        details.put("imagePath", imagePath);
        details.put("description", description);
        return new GraphNodeDto(id, name, value, 6, details);
    }

    private GraphLinkDto createLink(String source, String target, String type, String status) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", type);
        if (status != null) details.put("status", status);
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

    private GraphLinkDto createMentionedInLink(String source, String target, String refType) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", "MENTIONED_IN");
        details.put("refType", refType);
        return new GraphLinkDto(source, target, details, null);
    }
}
