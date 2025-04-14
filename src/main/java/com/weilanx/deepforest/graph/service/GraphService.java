package com.weilanx.deepforest.graph.service;

import com.weilanx.deepforest.graph.dto.GraphDataVO;
import com.weilanx.deepforest.graph.mock.MockGraphData; // 确保引入正确的 Mock 类
import org.springframework.stereotype.Service;

/**
 * 知识图谱服务层
 * 负责处理图谱数据的获取和相关业务逻辑
 */
@Service // 标记为 Spring 服务组件
public class GraphService {

    /**
     * 获取知识图谱数据
     * 目前直接从 Mock 数据类获取，后续可接入 Neo4j 或其他图数据库
     *
     * @return 包含所有图谱数据的 GraphDataVO 对象
     */
    public GraphDataVO getGraphData() {
        // 调用 Mock 数据生成器获取模拟数据
        // TODO: 将来替换为从 Neo4j 数据库查询并转换为 VO 的逻辑
        return MockGraphData.generateMockData();
    }

    // 未来可以添加其他图谱相关的服务方法
    // 例如：根据节点 ID 获取邻居、执行 Cypher 查询等
    // public GraphDataVO getNeighbors(String nodeId) { ... }
    // public Object executeCypher(String cypherQuery) { ... }
}