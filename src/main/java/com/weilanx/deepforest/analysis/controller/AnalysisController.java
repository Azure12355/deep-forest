package com.weilanx.deepforest.analysis.controller;

import com.weilanx.deepforest.analysis.dto.DashboardDataVO;
import com.weilanx.deepforest.analysis.service.AnalysisService;
import com.weilanx.deepforest.common.BaseResponse; // 引入通用响应类
import com.weilanx.deepforest.common.ResultUtils; // 引入响应工具类
import jakarta.annotation.Resource; // 使用 jakarta 的 Resource 注解
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据分析接口控制器
 * 提供与数据分析仪表盘相关的 API 端点
 */
@RestController // 标记为 RESTful 控制器，自动将返回对象序列化为 JSON
@RequestMapping("/analysis") // 定义该控制器下所有接口的基础路径
public class AnalysisController {

    @Resource // 注入 AnalysisService 实例
    private AnalysisService analysisService;

    /**
     * 获取数据分析仪表盘数据的 API 端点
     * 对应前端请求 GET /api/analysis/dashboard
     *
     * @return BaseResponse<DashboardDataVO> 包装后的仪表盘数据响应
     */
    @GetMapping("/dashboard") // 处理 GET 请求到 /api/analysis/dashboard
    public BaseResponse<DashboardDataVO> getDashboardData() {
        // 调用 Service 层获取数据
        DashboardDataVO dashboardData = analysisService.getDashboardData();
        // 使用 ResultUtils 包装成功响应
        return ResultUtils.success(dashboardData);
        // 如果需要处理异常，可以使用 try-catch 块，并在 catch 中返回 ResultUtils.error(...)
    }

    // 未来可以添加其他分析相关的 API 端点
}