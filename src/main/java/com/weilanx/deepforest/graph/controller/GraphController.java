package com.weilanx.deepforest.graph.controller;

import com.weilanx.deepforest.common.BaseResponse;
import com.weilanx.deepforest.common.ResultUtils;
import com.weilanx.deepforest.graph.dto.GraphDataVO;
import com.weilanx.deepforest.graph.service.GraphService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识图谱接口控制器
 * 提供与知识图谱数据相关的 API 端点
 */
@RestController // 标记为 RESTful 控制器
@RequestMapping("/graph") // 定义基础路径
public class GraphController {

    @Resource // 注入 GraphService
    private GraphService graphService;

    /**
     * 获取知识图谱数据的 API 端点
     * 对应前端请求 GET /api/graph/data
     *
     * @return BaseResponse<GraphDataVO> 包装后的图谱数据响应
     */
    @GetMapping("/data")
    public BaseResponse<GraphDataVO> getGraphData() {
        // 调用 Service 层获取图谱数据
        GraphDataVO graphData = graphService.getGraphData();
        // 使用通用响应工具类包装成功响应
        return ResultUtils.success(graphData);
        // 可以添加 try-catch 来处理潜在的异常，并返回错误响应
        // catch (Exception e) {
        //     log.error("获取图谱数据失败", e);
        //     return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "获取图谱数据失败");
        // }
    }

    // 未来可以添加其他接口，例如：
    // @GetMapping("/neighbors/{nodeId}")
    // public BaseResponse<GraphDataVO> getNeighbors(@PathVariable String nodeId) { ... }
    //
    // @PostMapping("/query")
    // public BaseResponse<Object> executeCypherQuery(@RequestBody String cypherQuery) { ... }
}