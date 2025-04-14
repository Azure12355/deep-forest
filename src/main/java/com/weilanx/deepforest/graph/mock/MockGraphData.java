package com.weilanx.deepforest.graph.mock;

import com.weilanx.deepforest.graph.dto.*;

import java.util.*;

/**
 * 提供知识图谱的 Mock（模拟）数据 (大幅扩展版)
 * 用于在数据库未接入或开发初期提供测试数据
 * 增加了节点和关系的多样性与数量，旨在模拟更真实的复杂图谱。
 */
public class MockGraphData {

    /**
     * 生成包含大量且多样化数据的模拟图谱
     * @return GraphDataVO 包含模拟数据的对象
     */
    public static GraphDataVO generateMockData() {
        // --- 1. 定义节点分类 ---
        List<GraphCategoryVO> categories = Arrays.asList(
                new GraphCategoryVO("Species", new ItemStyleVO("#5dade2")),   // 0: 物种 - 蓝色
                new GraphCategoryVO("Reference", new ItemStyleVO("#48c9b0")), // 1: 文献 - 绿色
                new GraphCategoryVO("Location", new ItemStyleVO("#f39c12")),  // 2: 位置 - 橙色
                new GraphCategoryVO("Taxonomy", new ItemStyleVO("#e74c3c")),  // 3: 分类 - 红色
                new GraphCategoryVO("Host", new ItemStyleVO("#a569bd")),    // 4: 寄主 - 紫色
                new GraphCategoryVO("File", new ItemStyleVO("#1abc9c")),     // 5: 文件 - 青色
                new GraphCategoryVO("Image", new ItemStyleVO("#3498db")),    // 6: 图片 - 另一种蓝色
                new GraphCategoryVO("PestControl", new ItemStyleVO("#f1c40f")), // 7: 防控措施 - 黄色
                new GraphCategoryVO("Chemical", new ItemStyleVO("#95a5a6")), // 8: 化学药剂 - 灰色
                new GraphCategoryVO("BiologicalAgent", new ItemStyleVO("#2ecc71")), // 9: 生物制剂/天敌 - 鲜绿
                new GraphCategoryVO("Symptom", new ItemStyleVO("#e67e22")) // 10: 症状 - 橘红
        );

        // --- 2. 定义节点 (大幅增加数量和种类) ---
        List<GraphNodeVO> nodes = new ArrayList<>(Arrays.asList(
                // --- 核心物种 (增加) ---
                new GraphNodeVO("sp1", "松材线虫\nB. xylophilus", 35, 0, createDetails("Species", "guid-sp1", "Bursaphelenchus xylophilus", "松材线虫", "线虫动物门", "已确认", "一种毁灭性森林病害。", Map.of("pathogenicity", "高", "introduced", true))),
                new GraphNodeVO("sp2", "美国白蛾\nH. cunea", 28, 0, createDetails("Species", "guid-sp2", "Hyphantria cunea", "美国白蛾", "昆虫纲", "已确认", "一种杂食性害虫。", Map.of("feedingHabit", "杂食性", "generationsPerYear", "2-3"))),
                new GraphNodeVO("sp3", "松墨天牛\nM. alternatus", 22, 0, createDetails("Species", "guid-sp3", "Monochamus alternatus", "松墨天牛", "昆虫纲", "已确认", "松材线虫的主要传播媒介。", Map.of("role", "vector"))),
                new GraphNodeVO("sp4", "红脂大小蠹\nD. valens", 18, 0, createDetails("Species", "guid-sp4", "Dendroctonus valens", "红脂大小蠹", "昆虫纲", "已确认", "危害松树韧皮部。", Map.of("damagePart", "韧皮部"))),
                new GraphNodeVO("sp5", "杨扇舟蛾\nC. anastomosis", 16, 0, createDetails("Species", "guid-sp5", "Clostera anastomosis", "杨扇舟蛾", "昆虫纲", "已确认", "危害杨柳科植物叶片。", Map.of("damagePart", "叶片"))),
                new GraphNodeVO("sp6", "春尺蠖\nA. crenaria", 14, 0, createDetails("Species", "guid-sp6", "Apocheima cinerarius", "春尺蠖", "昆虫纲", "已确认", "早春重要食叶害虫。", Map.of("emergencePeriod", "早春"))),
                new GraphNodeVO("sp7", "舞毒蛾\nL. dispar", 20, 0, createDetails("Species", "guid-sp7", "Lymantria dispar", "舞毒蛾", "昆虫纲", "有疑问", "广泛危害多种林木。", Map.of("feedingHabit", "杂食性", "spread", "迅速"))), // 有疑问状态
                new GraphNodeVO("sp8", "栗山天牛\nM. saltuarius", 11, 0, createDetails("Species", "guid-sp8", "Massicus raddei", "栗山天牛", "昆虫纲", "已确认", "危害壳斗科植物。", Map.of("targetFamily", "壳斗科"))),
                new GraphNodeVO("sp9", "苹果蠹蛾\nC. pomonella", 9, 0, createDetails("Species", "guid-sp9", "Cydia pomonella", "苹果蠹蛾", "昆虫纲", "已确认", "危害苹果、梨等水果。", Map.of("damagePart", "果实"))),
                new GraphNodeVO("sp10", "锈色棕榈象\nR. ferrugineus", 13, 0, createDetails("Species", "guid-sp10", "Rhynchophorus ferrugineus", "锈色棕榈象", "昆虫纲", "已确认", "危害棕榈科植物。", Map.of("targetFamily", "棕榈科", "quarantine", true))), // 检疫性害虫
                new GraphNodeVO("sp_pending1", "待审核天牛A", 8, 0, createDetails("Species", "guid-sp-p1", "Pending Cerambycid A", "待审核天牛A", "昆虫纲?", "待审核", "用户提交，疑似新种。")), // 待审核
                new GraphNodeVO("sp_pending2", "待审核小蠹B", 7, 0, createDetails("Species", "guid-sp-p2", "Pending Bark Beetle B", "待审核小蠹B", "昆虫纲?", "待审核", "形态特征需要复核。")), // 待审核

                // --- 寄主植物 (增加) ---
                new GraphNodeVO("host1", "松树\nPinus", 25, 4, createDetails("Host", "guid-host1", "Pinus", "松树", "松科", "已确认", null, Map.of("commonTypes", "马尾松, 油松, 赤松"))),
                new GraphNodeVO("host2", "杨树\nPopulus", 20, 4, createDetails("Host", "guid-host2", "Populus", "杨树", "杨柳科", "已确认", null, Map.of("commonTypes", "毛白杨, 意大利杨"))),
                new GraphNodeVO("host3", "柳树\nSalix", 18, 4, createDetails("Host", "guid-host3", "Salix", "柳树", "杨柳科", "已确认")),
                new GraphNodeVO("host4", "苹果\nMalus", 10, 4, createDetails("Host", "guid-host4", "Malus domestica", "苹果", "蔷薇科", "已确认")),
                new GraphNodeVO("host5", "栎树\nQuercus", 14, 4, createDetails("Host", "guid-host5", "Quercus", "栎树", "壳斗科", "已确认", null, Map.of("commonTypes", "麻栎, 橡子树"))), // 壳斗科
                new GraphNodeVO("host6", "榆树\nUlmus", 12, 4, createDetails("Host", "guid-host6", "Ulmus", "榆树", "榆科", "已确认")),
                new GraphNodeVO("host7", "桦树\nBetula", 9, 4, createDetails("Host", "guid-host7", "Betula", "桦树", "桦木科", "已确认")),
                new GraphNodeVO("host8", "棕榈\nArecaceae", 8, 4, createDetails("Host", "guid-host8", null, "棕榈科植物", "棕榈科", "已确认")), // 棕榈科
                new GraphNodeVO("host9", "核桃\nJuglans", 7, 4, createDetails("Host", "guid-host9", "Juglans regia", "核桃", "胡桃科", "已确认")),
                new GraphNodeVO("host10", "梨树\nPyrus", 6, 4, createDetails("Host", "guid-host10", "Pyrus", "梨树", "蔷薇科", "已确认")),

                // --- 文献 (增加) ---
                new GraphNodeVO("ref1", "松材线虫研究进展 (2023)", 15, 1, createDetails("Reference", "guid-ref1", null, "松材线虫研究进展", null, null, "综述了松材线虫的生物学、传播和防治。", Map.of("authors", "张三, 李四", "year", 2023, "journal", "林业科学", "doi", "10.xxxx/jfs.2023.01"))),
                new GraphNodeVO("ref2", "美国白蛾防控手册", 12, 1, createDetails("Reference", "guid-ref2", null, "美国白蛾防控手册", null, null, "介绍了识别、监测和综合防治技术。", Map.of("authors", "王五", "year", 2022, "publisher", "中国林业出版社", "isbn", "978-x-xxxx-xxxx-x"))),
                new GraphNodeVO("ref3", "松墨天牛的生物学研究", 11, 1, createDetails("Reference", "guid-ref3", null, "松墨天牛的生物学研究", null, null, "研究了生命周期、习性及与松材线虫的关系。", Map.of("authors", "赵六", "year", 2021, "journal", "昆虫学报", "pages", "110-118"))),
                new GraphNodeVO("ref4", "红脂大小蠹生物防治探索", 10, 1, createDetails("Reference", "guid-ref4", null, "红脂大小蠹生物防治探索", null, null, "探讨了利用天敌进行生物防治的可能性。", Map.of("authors", "孙七", "year", 2020, "conference", "全国森林保护学年会"))),
                new GraphNodeVO("ref5", "杨扇舟蛾发生规律与预测", 9, 1, createDetails("Reference", "guid-ref5", null, "杨扇舟蛾发生规律与预测", null, null, "基于气象因子分析了其发生规律。", Map.of("authors", "周八", "year", 2023, "journal", "植物保护"))),
                new GraphNodeVO("ref6", "中国北方春尺蠖灾害研究", 8, 1, createDetails("Reference", "guid-ref6", null, "中国北方春尺蠖灾害研究", null, null, "分析了春尺蠖在北方地区的危害特点。", Map.of("authors", "吴九", "year", 2019, "publisher", "气象出版社"))),
                new GraphNodeVO("ref7", "舞毒蛾综合治理技术", 10, 1, createDetails("Reference", "guid-ref7", null, "舞毒蛾综合治理技术", null, null, "包括物理、生物和化学防治方法。", Map.of("authors", "郑十", "year", 2021, "journal", "林业科技通讯"))),
                new GraphNodeVO("ref8", "栗山天牛寄主选择性研究", 7, 1, createDetails("Reference", "guid-ref8", null, "栗山天牛寄主选择性研究", null, null, "测试了其对不同壳斗科植物的选择性。", Map.of("authors", "陈十一", "year", 2020, "journal", "生态学杂志"))),
                new GraphNodeVO("ref9", "苹果蠹蛾信息素诱捕技术", 6, 1, createDetails("Reference", "guid-ref9", null, "苹果蠹蛾信息素诱捕技术", null, null, "优化了信息素诱捕器的使用参数。", Map.of("authors", "冯十二", "year", 2022, "journal", "果树学报"))),
                new GraphNodeVO("ref10", "锈色棕榈象早期检测方法", 8, 1, createDetails("Reference", "guid-ref10", null, "锈色棕榈象早期检测方法", null, null, "比较了声学检测和化学诱捕的效率。", Map.of("authors", "蒋十三", "year", 2023, "conference", "国际植物检疫大会"))),
                new GraphNodeVO("ref11", "林业常见病虫害图谱", 14, 1, createDetails("Reference", "guid-ref11", null, "林业常见病虫害图谱", null, null, "包含大量病虫害图片和描述。", Map.of("authors", "林业出版社编辑部", "year", 2018, "publisher", "中国林业出版社"))),
                new GraphNodeVO("ref12", "中国森林昆虫名录", 11, 1, createDetails("Reference", "guid-ref12", null, "中国森林昆虫名录", null, null, "系统收录了中国森林中的昆虫种类。", Map.of("authors", "萧刚柔 等", "year", 2006, "publisher", "科学出版社"))),

                // --- 分类 (增加) ---
                new GraphNodeVO("tx1", "线虫属\nBursaphelenchus", 7, 3, createDetails("Taxonomy", "guid-tx1", null, "Bursaphelenchus", null, null, null, Map.of("level", "Genus", "parent", "tx_family_Aphelenchoididae"))),
                new GraphNodeVO("tx2", "天牛科\nCerambycidae", 8, 3, createDetails("Taxonomy", "guid-tx2", null, "Cerambycidae", null, null, null, Map.of("level", "Family", "parent", "tx_order_Coleoptera"))),
                new GraphNodeVO("tx3", "鳞翅目\nLepidoptera", 10, 3, createDetails("Taxonomy", "guid-tx3", null, "Lepidoptera", null, null, null, Map.of("level", "Order", "parent", "tx_class_Insecta"))),
                new GraphNodeVO("tx4", "小蠹科\nScolytinae", 6, 3, createDetails("Taxonomy", "guid-tx4", null, "Scolytinae", null, null, null, Map.of("level", "Subfamily", "parent", "tx_family_Curculionidae"))), // 小蠹科现在常作为象甲科的亚科
                new GraphNodeVO("tx5", "舟蛾科\nNotodontidae", 5, 3, createDetails("Taxonomy", "guid-tx5", null, "Notodontidae", null, null, null, Map.of("level", "Family", "parent", "tx_order_Lepidoptera"))),
                new GraphNodeVO("tx6", "尺蛾科\nGeometridae", 5, 3, createDetails("Taxonomy", "guid-tx6", null, "Geometridae", null, null, null, Map.of("level", "Family", "parent", "tx_order_Lepidoptera"))),
                new GraphNodeVO("tx7", "毒蛾科\nLymantriidae", 7, 3, createDetails("Taxonomy", "guid-tx7", null, "Lymantriidae", null, null, null, Map.of("level", "Family", "parent", "tx_order_Lepidoptera"))), // 有些分类系统将其并入裳蛾科 Erebidae
                new GraphNodeVO("tx8", "卷蛾科\n Tortricidae", 4, 3, createDetails("Taxonomy", "guid-tx8", null, "Tortricidae", null, null, null, Map.of("level", "Family", "parent", "tx_order_Lepidoptera"))),
                new GraphNodeVO("tx_class_Insecta", "昆虫纲\nInsecta", 15, 3, createDetails("Taxonomy", "guid-tx_class_Insecta", null, "Insecta", null, null, null, Map.of("level", "Class"))),
                new GraphNodeVO("tx_order_Coleoptera", "鞘翅目\nColeoptera", 12, 3, createDetails("Taxonomy", "guid-tx_order_Coleoptera", null, "Coleoptera", null, null, null, Map.of("level", "Order", "parent", "tx_class_Insecta"))),
                new GraphNodeVO("tx_family_Curculionidae", "象甲科\nCurculionidae", 9, 3, createDetails("Taxonomy", "guid-tx_family_Curculionidae", null, "Curculionidae", null, null, null, Map.of("level", "Family", "parent", "tx_order_Coleoptera"))),

                // --- 位置 (增加) ---
                new GraphNodeVO("loc1", "中国\nChina", 40, 2, createDetails("Location", "guid-loc1", null, "中国", null, null, null, Map.of("level", "Country", "continent", "Asia"))),
                new GraphNodeVO("loc2", "北美\nNorth America", 25, 2, createDetails("Location", "guid-loc2", null, "北美", null, null, null, Map.of("level", "Continent"))),
                new GraphNodeVO("loc3", "江苏省\nJiangsu", 15, 2, createDetails("Location", "guid-loc3", null, "江苏省", null, null, null, Map.of("level", "Province", "country", "中国"))),
                new GraphNodeVO("loc4", "辽宁省\nLiaoning", 14, 2, createDetails("Location", "guid-loc4", null, "辽宁省", null, null, null, Map.of("level", "Province", "country", "中国"))),
                new GraphNodeVO("loc5", "山东省\nShandong", 13, 2, createDetails("Location", "guid-loc5", null, "山东省", null, null, null, Map.of("level", "Province", "country", "中国"))),
                new GraphNodeVO("loc6", "广东省\nGuangdong", 12, 2, createDetails("Location", "guid-loc6", null, "广东省", null, null, null, Map.of("level", "Province", "country", "中国"))),
                new GraphNodeVO("loc7", "云南省\nYunnan", 11, 2, createDetails("Location", "guid-loc7", null, "云南省", null, null, null, Map.of("level", "Province", "country", "中国"))),
                new GraphNodeVO("loc8", "日本\nJapan", 10, 2, createDetails("Location", "guid-loc8", null, "日本", null, null, null, Map.of("level", "Country", "continent", "Asia"))),
                new GraphNodeVO("loc9", "欧洲\nEurope", 18, 2, createDetails("Location", "guid-loc9", null, "欧洲", null, null, null, Map.of("level", "Continent"))),
                new GraphNodeVO("loc10", "美国\nUSA", 16, 2, createDetails("Location", "guid-loc10", null, "美国", null, null, null, Map.of("level", "Country", "continent", "北美"))),

                // --- 文件和图片 (增加) ---
                new GraphNodeVO("file1", "松材线虫进展.pdf", 4, 5, createDetails("File", "guid-file1", null, "松材线虫进展.pdf", null, null, null, Map.of("url", "/files/ref1.pdf", "fileSize", "2.5 MB"))),
                new GraphNodeVO("file2", "白蛾手册.pdf", 4, 5, createDetails("File", "guid-file2", null, "白蛾手册.pdf", null, null, null, Map.of("url", "/files/ref2.pdf", "fileSize", "5.1 MB"))),
                new GraphNodeVO("file3", "天牛生物学.docx", 3, 5, createDetails("File", "guid-file3", null, "天牛生物学.docx", null, null, null, Map.of("url", "/files/ref3.docx", "fileSize", "0.8 MB"))),
                new GraphNodeVO("img1", "白蛾成虫图.jpg", 5, 6, createDetails("Image", "guid-img1", null, "白蛾成虫图.jpg", null, null, null, Map.of("imagePath", "/images/sp2_adult.jpg", "description", "美国白蛾成虫照片"))),
                new GraphNodeVO("img2", "松树枯萎状.png", 6, 6, createDetails("Image", "guid-img2", null, "松树枯萎状.png", null, null, null, Map.of("imagePath", "/images/sp1_symptom.png", "description", "受松材线虫危害的松树"))),
                new GraphNodeVO("img3", "松墨天牛特写.jpg", 5, 6, createDetails("Image", "guid-img3", null, "松墨天牛特写.jpg", null, null, null, Map.of("imagePath", "/images/sp3_closeup.jpg", "description", "松墨天牛成虫"))),
                new GraphNodeVO("img4", "杨扇舟蛾幼虫.webp", 4, 6, createDetails("Image", "guid-img4", null, "杨扇舟蛾幼虫.webp", null, null, null, Map.of("imagePath", "/images/sp5_larva.webp", "description", "杨扇舟蛾幼虫群集危害"))),
                new GraphNodeVO("img5", "红脂大小蠹蛀孔.jpg", 4, 6, createDetails("Image", "guid-img5", null, "红脂大小蠹蛀孔.jpg", null, null, null, Map.of("imagePath", "/images/sp4_hole.jpg", "description", "红脂大小蠹在松树干上的蛀孔和流脂"))),

                // --- 防控措施 (增加) ---
                new GraphNodeVO("ctrl1", "诱捕器诱杀", 10, 7, createDetails("PestControl", "guid-ctrl1", null, "诱捕器诱杀", null, null, "利用信息素或食物诱剂诱捕成虫。", Map.of("methodType", "物理防治", "targetStage", "成虫"))),
                new GraphNodeVO("ctrl2", "喷洒药剂", 15, 7, createDetails("PestControl", "guid-ctrl2", null, "喷洒药剂", null, null, "使用化学药剂进行大面积防治。", Map.of("methodType", "化学防治", "scope", "大面积"))),
                new GraphNodeVO("ctrl3", "生物防治", 12, 7, createDetails("PestControl", "guid-ctrl3", null, "生物防治", null, null, "利用天敌或病原微生物控制害虫。", Map.of("methodType", "生物防治"))),
                new GraphNodeVO("ctrl4", "砍伐移除病株", 8, 7, createDetails("PestControl", "guid-ctrl4", null, "砍伐移除病株", null, null, "移除受感染严重的树木以阻止蔓延。", Map.of("methodType", "营林措施"))),
                new GraphNodeVO("ctrl5", "灯光诱杀", 7, 7, createDetails("PestControl", "guid-ctrl5", null, "灯光诱杀", null, null, "利用害虫趋光性进行诱杀。", Map.of("methodType", "物理防治", "targetStage", "成虫"))),
                new GraphNodeVO("ctrl6", "树干注药", 9, 7, createDetails("PestControl", "guid-ctrl6", null, "树干注药", null, null, "将药剂注入树干进行内部防治。", Map.of("methodType", "化学防治", "target", "蛀干害虫/线虫"))),

                // --- 化学药剂 (增加) ---
                new GraphNodeVO("chem1", "阿维菌素", 10, 8, createDetails("Chemical", "guid-chem1", null, "阿维菌素", null, null, "广谱杀虫、杀螨、杀线虫剂。", Map.of("chemicalType", "生物源农药", "activeIngredient", "Abamectin"))),
                new GraphNodeVO("chem2", "噻虫啉", 9, 8, createDetails("Chemical", "guid-chem2", null, "噻虫啉", null, null, "烟碱类杀虫剂。", Map.of("chemicalType", "化学合成农药", "activeIngredient", "Thiacloprid"))),
                new GraphNodeVO("chem3", "甲维盐", 8, 8, createDetails("Chemical", "guid-chem3", null, "甲氨基阿维菌素苯甲酸盐", null, null, "高效杀虫剂，常用于鳞翅目。", Map.of("chemicalType", "生物源农药", "activeIngredient", "Emamectin benzoate"))),
                new GraphNodeVO("chem4", "吡虫啉", 7, 8, createDetails("Chemical", "guid-chem4", null, "吡虫啉", null, null, "常用烟碱类杀虫剂。", Map.of("chemicalType", "化学合成农药", "activeIngredient", "Imidacloprid"))),
                new GraphNodeVO("chem5", "苏云金杆菌 (Bt)", 11, 8, createDetails("Chemical", "guid-chem5", null, "苏云金杆菌 (Bt)", null, null, "一种生物杀虫剂，对鳞翅目有效。", Map.of("chemicalType", "微生物农药", "activeIngredient", "Bacillus thuringiensis"))), // Bt也算广义药剂

                // --- 生物制剂/天敌 (新增类型) ---
                new GraphNodeVO("bio1", "花绒寄甲\nD. formosanus", 9, 9, createDetails("BiologicalAgent", "guid-bio1", "Dastarcus formosanus", "花绒寄甲", "昆虫纲", "已确认", "多种天牛的有效寄生性天敌。")),
                new GraphNodeVO("bio2", "白僵菌\nB. bassiana", 8, 9, createDetails("BiologicalAgent", "guid-bio2", "Beauveria bassiana", "白僵菌", "真菌界", "已确认", "一种广谱昆虫病原真菌。")),
                new GraphNodeVO("bio3", "管氏肿腿蜂\nS. sinensis", 7, 9, createDetails("BiologicalAgent", "guid-bio3", "Sclerodermus sinensis", "管氏肿腿蜂", "昆虫纲", "已确认", "寄生多种蛀干害虫。")),

                // --- 症状 (新增类型) ---
                new GraphNodeVO("sym1", "针叶变黄、萎蔫", 10, 10, createDetails("Symptom", "guid-sym1", null, "针叶变黄、萎蔫", null, null, "松树感染松材线虫的典型早期症状。")),
                new GraphNodeVO("sym2", "树干流脂", 8, 10, createDetails("Symptom", "guid-sym2", null, "树干流脂", null, null, "小蠹或天牛蛀食树干可能导致流脂。")),
                new GraphNodeVO("sym3", "叶片被啃食呈网状", 9, 10, createDetails("Symptom", "guid-sym3", null, "叶片被啃食呈网状", null, null, "美国白蛾等网幕类害虫的危害状。")),
                new GraphNodeVO("sym4", "蛀孔和排泄物", 7, 10, createDetails("Symptom", "guid-sym4", null, "蛀孔和排泄物", null, null, "蛀干害虫在树干上留下的痕迹。"))
        ));


        // --- 3. 定义关系 (大幅增加数量和多样性) ---
        List<GraphLinkVO> links = new ArrayList<>(Arrays.asList(
                // --- 物种 <-> 分类 ---
                new GraphLinkVO("sp1", "tx1", createRelationDetails("IS_CLASSIFIED_AS"), null), // 松材线虫 -> 线虫属
                new GraphLinkVO("sp3", "tx2", createRelationDetails("IS_CLASSIFIED_AS"), null), // 松墨天牛 -> 天牛科
                new GraphLinkVO("sp8", "tx2", createRelationDetails("IS_CLASSIFIED_AS"), null), // 栗山天牛 -> 天牛科
                new GraphLinkVO("sp_pending1", "tx2", createRelationDetails("IS_CLASSIFIED_AS", Map.of("confidence", "low")), new LineStyleVO("#f39c12", 1.0, 0.7, 0.2)), // 待审核天牛 -> 天牛科 (不确定)
                new GraphLinkVO("sp2", "tx3", createRelationDetails("IS_CLASSIFIED_AS"), null), // 美国白蛾 -> 鳞翅目
                new GraphLinkVO("sp5", "tx5", createRelationDetails("IS_CLASSIFIED_AS"), null), // 杨扇舟蛾 -> 舟蛾科
                new GraphLinkVO("sp6", "tx6", createRelationDetails("IS_CLASSIFIED_AS"), null), // 春尺蠖 -> 尺蛾科
                new GraphLinkVO("sp7", "tx7", createRelationDetails("IS_CLASSIFIED_AS"), null), // 舞毒蛾 -> 毒蛾科
                new GraphLinkVO("sp9", "tx8", createRelationDetails("IS_CLASSIFIED_AS"), null), // 苹果蠹蛾 -> 卷蛾科
                new GraphLinkVO("sp4", "tx4", createRelationDetails("IS_CLASSIFIED_AS"), null), // 红脂大小蠹 -> 小蠹科
                new GraphLinkVO("sp_pending2", "tx4", createRelationDetails("IS_CLASSIFIED_AS", Map.of("confidence", "medium")), new LineStyleVO("#f39c12", 1.0, 0.7, 0.2)), // 待审核小蠹 -> 小蠹科 (不确定)
                new GraphLinkVO("sp10", "tx_family_Curculionidae", createRelationDetails("IS_CLASSIFIED_AS"), null), // 棕榈象 -> 象甲科
                // 分类层级关系
                new GraphLinkVO("tx2", "tx_order_Coleoptera", createRelationDetails("IS_SUBCLASS_OF"), null), // 天牛科 -> 鞘翅目
                new GraphLinkVO("tx4", "tx_family_Curculionidae", createRelationDetails("IS_SUBCLASS_OF"), null), // 小蠹科 -> 象甲科
                new GraphLinkVO("tx_family_Curculionidae", "tx_order_Coleoptera", createRelationDetails("IS_SUBCLASS_OF"), null), // 象甲科 -> 鞘翅目
                new GraphLinkVO("tx5", "tx3", createRelationDetails("IS_SUBCLASS_OF"), null), // 舟蛾科 -> 鳞翅目
                new GraphLinkVO("tx6", "tx3", createRelationDetails("IS_SUBCLASS_OF"), null), // 尺蛾科 -> 鳞翅目
                new GraphLinkVO("tx7", "tx3", createRelationDetails("IS_SUBCLASS_OF"), null), // 毒蛾科 -> 鳞翅目
                new GraphLinkVO("tx8", "tx3", createRelationDetails("IS_SUBCLASS_OF"), null), // 卷蛾科 -> 鳞翅目
                new GraphLinkVO("tx_order_Coleoptera", "tx_class_Insecta", createRelationDetails("IS_SUBCLASS_OF"), null), // 鞘翅目 -> 昆虫纲
                new GraphLinkVO("tx3", "tx_class_Insecta", createRelationDetails("IS_SUBCLASS_OF"), null), // 鳞翅目 -> 昆虫纲

                // --- 物种 <-> 寄主 --- (增加多样性)
                new GraphLinkVO("sp1", "host1", createRelationDetails("HOSTS_ON", Map.of("severity", "极高", "part", "全株")), new LineStyleVO("#c0392b", 2.5, null, null)), // 红色最粗线
                new GraphLinkVO("sp3", "host1", createRelationDetails("HOSTS_ON", Map.of("role", "vector", "part", "枝干")), new LineStyleVO("#e74c3c", 1.8, null, null)),
                new GraphLinkVO("sp4", "host1", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "韧皮部")), new LineStyleVO("#e67e22", 1.5, null, null)),
                new GraphLinkVO("sp2", "host2", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "叶片")), new LineStyleVO("#d35400", 1.5, null, null)),
                new GraphLinkVO("sp2", "host3", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "叶片")), new LineStyleVO("#d35400", 1.5, null, null)),
                new GraphLinkVO("sp2", "host6", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "叶片")), null),
                new GraphLinkVO("sp2", "host7", createRelationDetails("HOSTS_ON", Map.of("severity", "低", "part", "叶片")), null),
                new GraphLinkVO("sp5", "host2", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "叶片")), null),
                new GraphLinkVO("sp5", "host3", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "叶片")), null),
                new GraphLinkVO("sp6", "host2", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "叶片")), null),
                new GraphLinkVO("sp6", "host5", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "叶片")), null),
                new GraphLinkVO("sp6", "host6", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "叶片")), null),
                new GraphLinkVO("sp7", "host5", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "叶片")), null), // 舞毒蛾危害栎树
                new GraphLinkVO("sp7", "host2", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "叶片")), null), // 舞毒蛾危害杨树
                new GraphLinkVO("sp7", "host7", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "叶片")), null), // 舞毒蛾危害桦树
                new GraphLinkVO("sp8", "host5", createRelationDetails("HOSTS_ON", Map.of("severity", "中等", "part", "枝干")), null), // 栗山天牛危害栎树
                new GraphLinkVO("sp9", "host4", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "果实")), null), // 苹果蠹蛾危害苹果
                new GraphLinkVO("sp9", "host10", createRelationDetails("HOSTS_ON", Map.of("severity", "高", "part", "果实")), null), // 苹果蠹蛾危害梨树
                new GraphLinkVO("sp10", "host8", createRelationDetails("HOSTS_ON", Map.of("severity", "极高", "part", "茎干")), new LineStyleVO("#c0392b", 2.0, null, null)), // 棕榈象危害棕榈
                new GraphLinkVO("sp_question", "host2", createRelationDetails("POSSIBLY_HOSTS_ON"), new LineStyleVO("#f39c12", 1.0, 0.8, 0.2)),

                // --- 物种 <-> 位置 --- (增加分布)
                new GraphLinkVO("sp1", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "广泛分布")), null),
                new GraphLinkVO("sp1", "loc3", createRelationDetails("DISTRIBUTED_IN", Map.of("firstReportedYear", 2001)), null),
                new GraphLinkVO("sp1", "loc5", createRelationDetails("DISTRIBUTED_IN", Map.of("firstReportedYear", 1998)), null),
                new GraphLinkVO("sp1", "loc6", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp1", "loc7", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp1", "loc8", createRelationDetails("DISTRIBUTED_IN", Map.of("origin", true)), null), // 标记为起源地之一
                new GraphLinkVO("sp2", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "入侵并扩散")), null),
                new GraphLinkVO("sp2", "loc2", createRelationDetails("DISTRIBUTED_IN", Map.of("origin", true)), null), // 标记为起源地
                new GraphLinkVO("sp2", "loc4", createRelationDetails("DISTRIBUTED_IN", Map.of("firstReportedYear", 1979)), null),
                new GraphLinkVO("sp2", "loc5", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp2", "loc9", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "入侵")), null),
                new GraphLinkVO("sp3", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "广泛分布")), null),
                new GraphLinkVO("sp3", "loc8", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp4", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "入侵")), null),
                new GraphLinkVO("sp4", "loc2", createRelationDetails("DISTRIBUTED_IN", Map.of("origin", true)), null),
                new GraphLinkVO("sp4", "loc10", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp5", "loc1", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp5", "loc9", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp6", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("region", "北方")), null),
                new GraphLinkVO("sp7", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "入侵")), null),
                new GraphLinkVO("sp7", "loc2", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp7", "loc9", createRelationDetails("DISTRIBUTED_IN", Map.of("origin", true)), null),
                new GraphLinkVO("sp8", "loc1", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp8", "loc8", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp9", "loc1", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp9", "loc9", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp9", "loc2", createRelationDetails("DISTRIBUTED_IN"), null),
                new GraphLinkVO("sp10", "loc1", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "入侵", "quarantine", true)), null),
                new GraphLinkVO("sp10", "loc6", createRelationDetails("DISTRIBUTED_IN", Map.of("status", "严重发生", "quarantine", true)), null),
                new GraphLinkVO("sp_pending1", "loc7", createRelationDetails("REPORTED_IN"), null),
                // 位置层级关系
                new GraphLinkVO("loc3", "loc1", createRelationDetails("IS_PART_OF"), null),
                new GraphLinkVO("loc4", "loc1", createRelationDetails("IS_PART_OF"), null),
                new GraphLinkVO("loc5", "loc1", createRelationDetails("IS_PART_OF"), null),
                new GraphLinkVO("loc6", "loc1", createRelationDetails("IS_PART_OF"), null),
                new GraphLinkVO("loc7", "loc1", createRelationDetails("IS_PART_OF"), null),
                new GraphLinkVO("loc10", "loc2", createRelationDetails("IS_PART_OF"), null),

                // --- 物种 <-> 文献 ---
                new GraphLinkVO("sp1", "ref1", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp3", "ref1", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp3", "ref3", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp2", "ref2", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp4", "ref4", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp5", "ref5", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp6", "ref6", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp7", "ref7", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp8", "ref8", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp9", "ref9", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp10", "ref10", createRelationDetails("MENTIONED_IN"), null),
                // 图谱和名录 MENTION 多个物种
                new GraphLinkVO("sp1", "ref11", createRelationDetails("MENTIONED_IN", Map.of("page", "102")), null),
                new GraphLinkVO("sp2", "ref11", createRelationDetails("MENTIONED_IN", Map.of("page", "155")), null),
                new GraphLinkVO("sp3", "ref11", createRelationDetails("MENTIONED_IN", Map.of("page", "88")), null),
                new GraphLinkVO("sp4", "ref11", createRelationDetails("MENTIONED_IN", Map.of("page", "95")), null),
                new GraphLinkVO("sp5", "ref11", createRelationDetails("MENTIONED_IN", Map.of("page", "160")), null),
                new GraphLinkVO("sp1", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp2", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp3", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp4", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp5", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp6", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp7", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp8", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp9", "ref12", createRelationDetails("MENTIONED_IN"), null),
                new GraphLinkVO("sp10", "ref12", createRelationDetails("MENTIONED_IN"), null),

                // --- 文献 <-> 文件/图片 ---
                new GraphLinkVO("ref1", "file1", createRelationDetails("HAS_FILE"), null),
                new GraphLinkVO("ref2", "file2", createRelationDetails("HAS_FILE"), null),
                new GraphLinkVO("ref3", "file3", createRelationDetails("HAS_FILE"), null),
                new GraphLinkVO("ref11", "img1", createRelationDetails("CONTAINS_IMAGE"), null), // 图谱包含图片
                new GraphLinkVO("ref11", "img2", createRelationDetails("CONTAINS_IMAGE"), null),
                new GraphLinkVO("ref11", "img3", createRelationDetails("CONTAINS_IMAGE"), null),
                new GraphLinkVO("ref11", "img4", createRelationDetails("CONTAINS_IMAGE"), null),
                new GraphLinkVO("ref11", "img5", createRelationDetails("CONTAINS_IMAGE"), null),

                // --- 物种 <-> 图片 ---
                new GraphLinkVO("sp1", "img2", createRelationDetails("HAS_IMAGE", Map.of("type", "symptom")), null),
                new GraphLinkVO("sp2", "img1", createRelationDetails("HAS_IMAGE", Map.of("type", "adult")), null),
                new GraphLinkVO("sp3", "img3", createRelationDetails("HAS_IMAGE", Map.of("type", "adult")), null),
                new GraphLinkVO("sp5", "img4", createRelationDetails("HAS_IMAGE", Map.of("type", "larva")), null),
                new GraphLinkVO("sp4", "img5", createRelationDetails("HAS_IMAGE", Map.of("type", "damage")), null),

                // --- 物种 <-> 防控措施 ---
                new GraphLinkVO("sp1", "ctrl2", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "medium")), null),
                new GraphLinkVO("sp1", "ctrl4", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "high")), null), // 砍伐对线虫有效
                new GraphLinkVO("sp1", "ctrl6", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "high")), null), // 树干注药对线虫有效
                new GraphLinkVO("sp2", "ctrl1", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "low")), null),
                new GraphLinkVO("sp2", "ctrl2", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "high")), null),
                new GraphLinkVO("sp2", "ctrl3", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "medium")), null), // 白蛾可生物防治
                new GraphLinkVO("sp2", "ctrl5", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "medium")), null), // 白蛾可用灯诱
                new GraphLinkVO("sp3", "ctrl1", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "medium")), null),
                new GraphLinkVO("sp3", "ctrl3", createRelationDetails("CONTROLLED_BY"), null), // 天牛可生物防治
                new GraphLinkVO("sp3", "ctrl4", createRelationDetails("CONTROLLED_BY"), null), // 砍伐也针对天牛
                new GraphLinkVO("sp4", "ctrl1", createRelationDetails("CONTROLLED_BY"), null), // 小蠹可用诱捕器
                new GraphLinkVO("sp4", "ctrl3", createRelationDetails("CONTROLLED_BY"), null), // 小蠹可生物防治
                new GraphLinkVO("sp5", "ctrl2", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp5", "ctrl3", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp6", "ctrl2", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp7", "ctrl1", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp7", "ctrl2", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp7", "ctrl3", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp8", "ctrl3", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp8", "ctrl4", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp9", "ctrl1", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "high")), null), // 苹果蠹蛾信息素诱捕有效
                new GraphLinkVO("sp9", "ctrl2", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp10", "ctrl1", createRelationDetails("CONTROLLED_BY"), null),
                new GraphLinkVO("sp10", "ctrl4", createRelationDetails("CONTROLLED_BY", Map.of("effectiveness", "critical")), null), // 棕榈象关键措施是砍伐

                // --- 防控措施 <-> 药剂/生物制剂 ---
                new GraphLinkVO("ctrl2", "chem1", createRelationDetails("USES_CHEMICAL", Map.of("dosage", "参考说明")), new LineStyleVO("#bdc3c7", 0.8, 0.5, null)),
                new GraphLinkVO("ctrl2", "chem2", createRelationDetails("USES_CHEMICAL"), new LineStyleVO("#bdc3c7", 0.8, 0.5, null)),
                new GraphLinkVO("ctrl2", "chem3", createRelationDetails("USES_CHEMICAL"), new LineStyleVO("#bdc3c7", 0.8, 0.5, null)),
                new GraphLinkVO("ctrl2", "chem4", createRelationDetails("USES_CHEMICAL"), new LineStyleVO("#bdc3c7", 0.8, 0.5, null)),
                new GraphLinkVO("ctrl2", "chem5", createRelationDetails("USES_CHEMICAL", Map.of("note", "对鳞翅目幼虫")), new LineStyleVO("#bdc3c7", 0.8, 0.5, null)), // 喷洒 Bt
                new GraphLinkVO("ctrl6", "chem1", createRelationDetails("USES_CHEMICAL"), new LineStyleVO("#bdc3c7", 0.8, 0.5, null)), // 树干注射阿维菌素
                new GraphLinkVO("ctrl3", "bio1", createRelationDetails("USES_AGENT"), new LineStyleVO("#27ae60", 1.2, 0.7, null)), // 生物防治使用花绒寄甲
                new GraphLinkVO("ctrl3", "bio2", createRelationDetails("USES_AGENT"), new LineStyleVO("#27ae60", 1.2, 0.7, null)), // 生物防治使用白僵菌
                new GraphLinkVO("ctrl3", "bio3", createRelationDetails("USES_AGENT"), new LineStyleVO("#27ae60", 1.2, 0.7, null)), // 生物防治使用肿腿蜂

                // --- 物种 <-> 药剂/生物制剂 (直接关系) ---
                new GraphLinkVO("sp1", "chem1", createRelationDetails("AFFECTED_BY"), new LineStyleVO("#7f8c8d", 0.7, 0.6, null)), // 阿维菌素影响线虫
                new GraphLinkVO("sp2", "chem3", createRelationDetails("AFFECTED_BY", Map.of("stage", "幼虫")), new LineStyleVO("#7f8c8d", 0.7, 0.6, null)), // 甲维盐影响白蛾幼虫
                new GraphLinkVO("sp2", "chem5", createRelationDetails("AFFECTED_BY", Map.of("stage", "幼虫")), new LineStyleVO("#7f8c8d", 0.7, 0.6, null)), // Bt 影响白蛾幼虫
                new GraphLinkVO("sp5", "chem5", createRelationDetails("AFFECTED_BY", Map.of("stage", "幼虫")), new LineStyleVO("#7f8c8d", 0.7, 0.6, null)), // Bt 影响杨扇舟蛾
                new GraphLinkVO("sp6", "chem5", createRelationDetails("AFFECTED_BY", Map.of("stage", "幼虫")), new LineStyleVO("#7f8c8d", 0.7, 0.6, null)), // Bt 影响春尺蠖
                new GraphLinkVO("sp7", "chem5", createRelationDetails("AFFECTED_BY", Map.of("stage", "幼虫")), new LineStyleVO("#7f8c8d", 0.7, 0.6, null)), // Bt 影响舞毒蛾
                new GraphLinkVO("sp3", "bio1", createRelationDetails("PREDATED_OR_PARASITIZED_BY"), new LineStyleVO("#2ecc71", 1.0, 0.8, null)), // 花绒寄甲寄生松墨天牛
                new GraphLinkVO("sp8", "bio1", createRelationDetails("PREDATED_OR_PARASITIZED_BY"), new LineStyleVO("#2ecc71", 1.0, 0.8, null)), // 花绒寄甲寄生栗山天牛
                new GraphLinkVO("sp2", "bio2", createRelationDetails("INFECTED_BY"), new LineStyleVO("#2ecc71", 1.0, 0.8, null)), // 白僵菌感染白蛾
                new GraphLinkVO("sp4", "bio3", createRelationDetails("PREDATED_OR_PARASITIZED_BY"), new LineStyleVO("#2ecc71", 1.0, 0.8, null)), // 肿腿蜂寄生小蠹

                // --- 物种 <-> 症状 ---
                new GraphLinkVO("sp1", "sym1", createRelationDetails("CAUSES_SYMPTOM"), new LineStyleVO("#e67e22", 1.0, 0.7, null)),
                new GraphLinkVO("sp1", "sym2", createRelationDetails("CAUSES_SYMPTOM"), new LineStyleVO("#e67e22", 1.0, 0.7, null)), // 线虫感染后树势衰弱也可能间接导致流脂
                new GraphLinkVO("sp3", "sym4", createRelationDetails("CAUSES_SYMPTOM", Map.of("type", "exit hole")), new LineStyleVO("#e67e22", 1.0, 0.7, null)), // 天牛导致蛀孔
                new GraphLinkVO("sp4", "sym2", createRelationDetails("CAUSES_SYMPTOM"), new LineStyleVO("#e67e22", 1.0, 0.7, null)), // 小蠹导致流脂
                new GraphLinkVO("sp4", "sym4", createRelationDetails("CAUSES_SYMPTOM", Map.of("type", "bore dust")), new LineStyleVO("#e67e22", 1.0, 0.7, null)), // 小蠹导致排泄物（蛀屑）
                new GraphLinkVO("sp2", "sym3", createRelationDetails("CAUSES_SYMPTOM"), new LineStyleVO("#e67e22", 1.0, 0.7, null)), // 白蛾导致网状叶
                new GraphLinkVO("sp5", "sym3", createRelationDetails("CAUSES_SYMPTOM", Map.of("severity", "high")), new LineStyleVO("#e67e22", 1.0, 0.7, null)) // 杨扇舟蛾也导致网状叶
        ));

        // --- 4. 计算物种状态分布 ---
        Map<String, Integer> statusCounts = new HashMap<>();
        for (GraphNodeVO node : nodes) {
            if (node.getCategory() == 0 && node.getDetails() != null && node.getDetails().containsKey("status")) { // Category 0 is Species
                String status = (String) node.getDetails().get("status");
                statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
            }
        }
        List<SpeciesStatusVO> speciesConfirmationStatus = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            speciesConfirmationStatus.add(new SpeciesStatusVO(entry.getKey(), entry.getValue()));
        }

        // --- 5. 返回整合数据 ---
        return new GraphDataVO(nodes, links, categories, speciesConfirmationStatus);
    }

    /**
     * 辅助方法：创建节点详细信息 Map
     */
    private static Map<String, Object> createDetails(String type, String guid, String scientificName, String chineseName, String classification, String status, String desc, Map<String, Object> additionalProps) {
        Map<String, Object> details = new LinkedHashMap<>(); // 使用 LinkedHashMap 保持插入顺序
        details.put("type", type);
        if (guid != null) details.put("guid", guid);
        if (scientificName != null) details.put("scientificName", scientificName);
        if (chineseName != null) details.put("chineseName", chineseName);
        if (classification != null) details.put("classification", classification);
        if (status != null) details.put("status", status);
        if (desc != null && !desc.trim().isEmpty()) details.put("desc", desc);
        if (additionalProps != null) {
            details.putAll(additionalProps);
        }
        return details;
    }
    // 重载简化
    private static Map<String, Object> createDetails(String type, String guid, String scientificName, String chineseName, String classification, String status) {
        return createDetails(type, guid, scientificName, chineseName, classification, status, null, null);
    }
    private static Map<String, Object> createDetails(String type, String guid, String scientificName, String chineseName, String classification, String status, String desc) {
        return createDetails(type, guid, scientificName, chineseName, classification, status, desc, null);
    }


    /**
     * 辅助方法：创建关系详细信息 Map
     */
    private static Map<String, Object> createRelationDetails(String type, Map<String, Object> additionalProps) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("type", type);
        if (additionalProps != null) {
            details.putAll(additionalProps);
        }
        return details;
    }
    // 重载简化
    private static Map<String, Object> createRelationDetails(String type) {
        return createRelationDetails(type, null);
    }
}