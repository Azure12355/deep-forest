// src/main/java/com/weilanx/deepforest/graph/controller/GraphController.java
package com.weilanx.deepforest.graph.controller;

import com.weilanx.deepforest.graph.dto.GraphDataDto;
import com.weilanx.deepforest.graph.mock.MockGraphDataStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识图谱 API 控制器
 * 提供知识图谱可视化所需的数据接口
 */
@RestController
@RequestMapping("/graph")
@Slf4j
public class GraphController {

    @Resource
    private MockGraphDataStore mockGraphDataStore;

    /**
     * 获取完整的知识图谱数据
     * GET /api/graph/data
     *
     * @return 包含节点、关系、分类和物种状态的完整图谱数据
     */
    @GetMapping("/data")
    public ResponseEntity<GraphDataDto> getGraphData() {
        try {
            GraphDataDto graphData = mockGraphDataStore.getGraphData();
            log.info("成功获取知识图谱数据: {} 个节点, {} 条关系",
                    graphData.getNodes().size(),
                    graphData.getLinks().size());
            return ResponseEntity.ok(graphData);
        } catch (Exception e) {
                log.error("获取知识图谱数据失败", e);
                return ResponseEntity.internalServerError().build();
            }
    }
}
