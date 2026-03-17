// src/main/java/com/weilanx/deepforest/analysis/controller/AnalysisController.java
package com.weilanx.deepforest.analysis.controller;

import com.weilanx.deepforest.analysis.dto.*;
import com.weilanx.deepforest.analysis.mock.MockAnalysisDataStore;
import com.weilanx.deepforest.common.BaseResponse;
import com.weilanx.deepforest.common.ResultUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据分析仪表盘 API 控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private MockAnalysisDataStore mockAnalysisDataStore;

    @GetMapping("/dashboard/metrics")
    public BaseResponse<List<MetricDataDto>> getMetrics() {
        return ResultUtils.success(mockAnalysisDataStore.getMetrics());
    }

    @GetMapping("/dashboard/charts/species-taxonomy")
    public BaseResponse<List<NameValueDataDto>> getSpeciesTaxonomy() {
        return ResultUtils.success(mockAnalysisDataStore.getSpeciesTaxonomy());
    }

    @GetMapping("/dashboard/charts/species-status")
    public BaseResponse<List<NameValueDataDto>> getSpeciesStatus() {
        return ResultUtils.success(mockAnalysisDataStore.getSpeciesStatus());
    }

    @GetMapping("/dashboard/charts/species-growth")
    public BaseResponse<TimeSeriesDataDto> getSpeciesGrowth() {
        return ResultUtils.success(mockAnalysisDataStore.getSpeciesGrowth());
    }

    @GetMapping("/dashboard/charts/geo-distribution")
    public BaseResponse<List<GeoDistributionDataDto>> getGeoDistribution() {
        return ResultUtils.success(mockAnalysisDataStore.getGeoDistribution());
    }

    @GetMapping("/dashboard/charts/top-hosts")
    public BaseResponse<TopHostDataDto> getTopHosts() {
        return ResultUtils.success(mockAnalysisDataStore.getTopHosts());
    }

    @GetMapping("/dashboard/charts/reference-growth")
    public BaseResponse<TimeSeriesDataDto> getReferenceGrowth() {
        return ResultUtils.success(mockAnalysisDataStore.getReferenceGrowth());
    }

    @GetMapping("/dashboard/charts/reference-types")
    public BaseResponse<List<NameValueDataDto>> getReferenceTypes() {
        return ResultUtils.success(mockAnalysisDataStore.getReferenceTypes());
    }

    @GetMapping("/dashboard/charts/file-types")
    public BaseResponse<List<NameValueDataDto>> getFileTypes() {
        return ResultUtils.success(mockAnalysisDataStore.getFileTypes());
    }
}