package com.weilanx.deepforest.analysis.service;

import com.weilanx.deepforest.analysis.dto.DashboardDataVO;
import com.weilanx.deepforest.analysis.mock.MockAnalysisData;
import org.springframework.stereotype.Service;

/**
 * 数据分析服务层
 * 负责处理数据分析相关的业务逻辑
 */
@Service // 标记为 Spring 服务组件
public class AnalysisService {

    /**
     * 获取仪表盘数据
     * 目前直接从 Mock 数据类获取，后续可替换为数据库查询等真实逻辑
     *
     * @return 包含所有仪表盘数据的 DashboardDataVO 对象
     */
    public DashboardDataVO getDashboardData() {
        // 调用 Mock 数据生成器获取模拟数据
        // TODO: 将来替换为从数据库、缓存或其他数据源获取真实数据的逻辑
        return MockAnalysisData.generateMockDashboardData();
    }

    // 未来可以添加其他分析相关的服务方法
    // 例如：getSpeciesDetailAnalysis(String speciesId) 等
}