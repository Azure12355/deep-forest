// src/main/java/com/weilanx/deepforest/mock/MockDataStore.java
package com.weilanx.deepforest.mock;

import com.weilanx.deepforest.chat.dto.AttachmentDto; // 确保引入附件 DTO
import com.weilanx.deepforest.chat.dto.ChatMessageDto;
import com.weilanx.deepforest.chat.dto.HistoryGroupDto;
import com.weilanx.deepforest.chat.dto.HistoryItemDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 模拟数据存储 (替代数据库)
 * 注意：这是一个非常基础的内存存储，仅用于演示。
 * 生产环境需要替换为数据库实现。
 * CopyOnWriteArrayList 用于多线程读写历史记录，ConcurrentHashMap 用于消息存储。
 *
 * !! 更新：已使用更多与林业病虫害相关的模拟数据 !!
 */
@Component
public class MockDataStore {

    // 存储所有消息，Key 是 chatId
    private final Map<String, List<ChatMessageDto>> chatMessages = new ConcurrentHashMap<>();
    // 存储聊天标题，Key 是 chatId
    private final Map<String, String> chatTitles = new ConcurrentHashMap<>();
    // 存储聊天创建或最后更新时间，Key 是 chatId
    private final Map<String, Long> chatTimestamps = new ConcurrentHashMap<>();

    public MockDataStore() {
        // 初始化与林业病虫害相关的模拟数据
        long now = Instant.now().toEpochMilli();
        long yesterday = now - TimeUnit.DAYS.toMillis(1);
        long lastWeek = now - TimeUnit.DAYS.toMillis(7);
        long lastMonth = now - TimeUnit.DAYS.toMillis(30);
        long twoMonthsAgo = now - TimeUnit.DAYS.toMillis(60);
        long halfYearAgo = now - TimeUnit.DAYS.toMillis(180);
        long older = now - TimeUnit.DAYS.toMillis(400); // 更早

        // 聊天 1: 松材线虫病防治 (当天)
        String chatId1 = "forest-chat-1";
        String title1 = "松材线虫病怎么防治啊？";
        chatTitles.put(chatId1, title1);
        chatTimestamps.put(chatId1, now);
        chatMessages.put(chatId1, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg1-user", "user", title1, null, false, null, now - 10000),
                new ChatMessageDto("msg1-ai", "ai", "松材线虫病是一种毁灭性病害，主要通过天牛传播。防治关键在于：\n1. **疫木清理**：及时砍伐并无害化处理感病松树。\n2. **媒介昆虫防治**：在天牛羽化期进行药剂防治或诱捕。\n3. **预防保护**：对健康松树注射药剂进行预防。\n4. **生物防治**：研究和利用天敌控制天牛种群。\n请结合当地林业部门的指导进行具体操作。", null, false, null, now - 5000)
        )));

        // 聊天 2: 美国白蛾识别 (昨天)
        String chatId2 = "forest-chat-2";
        String title2 = "树上好多网，是美国白蛾吗？";
        chatTitles.put(chatId2, title2);
        chatTimestamps.put(chatId2, yesterday);
        chatMessages.put(chatId2, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg2-user", "user", title2, null, false, null, yesterday - 20000),
                new ChatMessageDto("msg2-ai", "ai", "树枝上出现大面积的白色网幕，里面有许多毛毛虫，这很可能是美国白蛾的幼虫巢穴。美国白蛾幼虫会啃食叶片，严重时能将整株树的叶子吃光。建议尽快剪除网幕并销毁，或者联系专业人员进行防治。", null, false, null, yesterday - 15000)
        )));

        // 聊天 3: 图片识别请求 (模拟) (上周)
        String chatId3 = "forest-chat-3";
        String title3 = "[图片] 叶子上有白点是什么病？";
        chatTitles.put(chatId3, title3);
        chatTimestamps.put(chatId3, lastWeek);
        // 模拟用户上传了图片附件 (注意: mockUrl 是虚构的)
        AttachmentDto mockAttachment = new AttachmentDto("user-file-mock-1", "image", "leaf_spot.jpg", "/api/files/mock/leaf_spot.jpg");
        chatMessages.put(chatId3, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg3-user", "user", "你看下这张图片，叶子上好多白点，是什么病？", null, false, List.of(mockAttachment), lastWeek - 30000),
                new ChatMessageDto("msg3-ai", "ai", "根据您提供的图片，叶片上的白色粉状斑点很像是**白粉病**的症状。白粉病是一种常见的真菌病害，在湿度较高、通风不良的环境下容易发生。建议加强通风，清除病叶，并可选用合适的杀菌剂进行喷洒防治。", null, false, null, lastWeek - 25000)
        )));

        // 聊天 4: 蚜虫生物防治 (上个月)
        String chatId4 = "forest-chat-4";
        String title4 = "有没有不用农药治蚜虫的方法？";
        chatTitles.put(chatId4, title4);
        chatTimestamps.put(chatId4, lastMonth);
        chatMessages.put(chatId4, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg4-user", "user", title4, null, false, null, lastMonth - 40000),
                new ChatMessageDto("msg4-ai", "ai", "当然有！蚜虫的生物防治方法包括：\n1. **保护和引诱天敌**：如瓢虫、草蛉、食蚜蝇等都是蚜虫的天敌，可以种植一些蜜源植物吸引它们。\n2. **人工释放天敌**：在蚜虫发生初期，可以购买并释放商品化的瓢虫或草蛉。\n3. **使用生物农药**：例如基于印楝素、苦参碱等植物源农药，或者某些微生物杀虫剂对蚜虫也有效果。", null, false, null, lastMonth - 35000)
        )));

        // 聊天 5: 虫害上报流程 (两个月前)
        String chatId5 = "forest-chat-5";
        String title5 = "发现一片树林虫子特别多，怎么上报？";
        chatTitles.put(chatId5, title5);
        chatTimestamps.put(chatId5, twoMonthsAgo);
        chatMessages.put(chatId5, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg5-user", "user", title5, null, false, null, twoMonthsAgo - 50000),
                new ChatMessageDto("msg5-ai", "ai", "发现疑似重大林业有害生物疫情时，请务必及时上报。通常可以通过以下途径：\n1. **联系当地林业主管部门**：这是最直接有效的方式，他们会派专业人员进行调查核实。\n2. **拨打举报电话**：部分地区设有林业有害生物举报热线。\n3. **通过官方网站或APP上报**：一些地区的林业部门提供了在线上报渠道。\n上报时请尽量提供详细信息，如发生地点、受害树种、危害症状、发现时间等，最好能附带照片或视频。", null, false, null, twoMonthsAgo - 45000)
        )));

        // 聊天 6: 橡树健康咨询 (半年前)
        String chatId6 = "forest-chat-6";
        String title6 = "我家院子里的橡树叶子变黄了，怎么办？";
        chatTitles.put(chatId6, title6);
        chatTimestamps.put(chatId6, halfYearAgo);
        chatMessages.put(chatId6, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg6-user", "user", title6, null, false, null, halfYearAgo - 60000),
                new ChatMessageDto("msg6-ai", "ai", "橡树叶子变黄可能由多种原因引起，比如季节性变化、水分胁迫（过多或过少）、营养缺乏、病虫害（如橡树黄化病、根腐病、蛀干害虫等）或环境因素。为了准确判断，需要更多信息：\n- 是所有叶子都变黄，还是部分？\n- 变黄是从叶脉开始还是叶尖？\n- 树干或根部有无异常？\n- 近期天气和浇水情况如何？\n建议您仔细观察，或者拍些清晰照片给我看下，也可以咨询专业的树木医生。", null, false, null, halfYearAgo - 55000)
        )));

        // 聊天 7: 白蚁预防 (更早)
        String chatId7 = "forest-chat-7";
        String title7 = "林区小木屋怎么防白蚁？";
        chatTitles.put(chatId7, title7);
        chatTimestamps.put(chatId7, older);
        chatMessages.put(chatId7, new CopyOnWriteArrayList<>(Arrays.asList(
                new ChatMessageDto("msg7-user", "user", title7, null, false, null, older - 70000),
                new ChatMessageDto("msg7-ai", "ai", "林区木结构建筑预防白蚁非常重要，可以采取以下措施：\n1. **物理屏障**：在地基周围设置砂粒屏障或不锈钢网。\n2. **化学屏障**：在建筑地基土壤进行化学处理，形成毒土屏障（请使用环保药剂并咨询专业人士）。\n3. **木材处理**：使用防腐防蚁处理过的木材，或对现有木材涂刷防蚁药剂。\n4. **环境治理**：保持木屋周围环境干燥、通风，清除杂物和枯枝落叶。\n5. **定期检查**：定期检查木结构是否有蚁路、分飞孔等白蚁活动迹象，早发现早处理。", null, false, null, older - 65000)
        )));
    }

    /**
     * 获取格式化的聊天历史记录 (模拟数据库查询和分组)
     * @return 按时间分组的历史记录
     */
    public List<HistoryGroupDto> getFormattedHistory() {
        List<HistoryItemDto> todayItems = new ArrayList<>();
        List<HistoryItemDto> recent7DaysItems = new ArrayList<>(); // 改为最近7天
        List<HistoryItemDto> recent30DaysItems = new ArrayList<>();
        List<HistoryItemDto> olderItems = new ArrayList<>();

        long now = Instant.now().toEpochMilli();
        long sevenDaysAgo = now - TimeUnit.DAYS.toMillis(7);
        long thirtyDaysAgo = now - TimeUnit.DAYS.toMillis(30);
        // 获取今天的开始时间戳 (简单方式)
        long todayStart = Instant.ofEpochMilli(now).truncatedTo(java.time.temporal.ChronoUnit.DAYS).toEpochMilli();


        // 遍历所有聊天记录
        chatTimestamps.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) // 按时间倒序
                .forEach(entry -> {
                    String chatId = entry.getKey();
                    Long timestamp = entry.getValue();
                    String title = chatTitles.getOrDefault(chatId, "无标题对话");
                    HistoryItemDto item = new HistoryItemDto(chatId, title);

                    if (timestamp >= todayStart) {
                        todayItems.add(item);
                    } else if (timestamp >= sevenDaysAgo) {
                        recent7DaysItems.add(item); // 新增7天分组
                    } else if (timestamp >= thirtyDaysAgo) {
                        recent30DaysItems.add(item);
                    } else {
                        olderItems.add(item);
                    }
                });

        List<HistoryGroupDto> groups = new ArrayList<>();
        if (!todayItems.isEmpty()) {
            groups.add(new HistoryGroupDto("今天", todayItems));
        }
        if (!recent7DaysItems.isEmpty()) {
            groups.add(new HistoryGroupDto("最近7天", recent7DaysItems));
        }
        if (!recent30DaysItems.isEmpty()) {
            groups.add(new HistoryGroupDto("最近30天", recent30DaysItems));
        }
        if (!olderItems.isEmpty()) {
            // 可以根据需要进一步细分，例如 "最近半年", "更早"
            groups.add(new HistoryGroupDto("更早之前", olderItems));
        }

        return groups;
    }

    /**
     * 根据 chatId 获取消息列表
     * @param chatId 聊天 ID
     * @return 消息列表，如果找不到则返回 null
     */
    public List<ChatMessageDto> getMessagesByChatId(String chatId) {
        return chatMessages.get(chatId);
    }

    /**
     * 保存一条消息到指定的聊天
     * @param chatId 聊天 ID
     * @param message 消息 DTO
     */
    public void addMessage(String chatId, ChatMessageDto message) {
        chatMessages.computeIfAbsent(chatId, k -> new CopyOnWriteArrayList<>()).add(message);
        // 更新聊天时间戳为最新消息时间
        chatTimestamps.put(chatId, message.getTimestamp());
    }

    /**
     * 创建新的聊天会话
     * @param initialUserMessage 用户的第一条消息内容，用于生成标题
     * @return 新创建的聊天 ID
     */
    public String createNewChat(String initialUserMessage) {
        // 使用更符合林业场景的前缀
        String newChatId = "forest-chat-" + System.nanoTime();
        long now = Instant.now().toEpochMilli();
        String title = initialUserMessage;
        // 稍微调整标题截断逻辑
        if (title == null || title.trim().isEmpty()) {
            title = "新的林业咨询"; // 默认标题
        } else if (title.length() > 40) { // 缩短截断长度
            title = title.substring(0, 40) + "...";
        }

        chatTitles.put(newChatId, title);
        chatTimestamps.put(newChatId, now);
        chatMessages.put(newChatId, new CopyOnWriteArrayList<>()); // 初始化空消息列表
        return newChatId;
    }
}