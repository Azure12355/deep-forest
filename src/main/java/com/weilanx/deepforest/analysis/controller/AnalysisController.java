// src/main/java/com/weilanx/deepforest/analysis/controller/AnalysisController.java
package com.weilanx.deepforest.analysis.controller;

import com.weilanx.deepforest.analysis.dto.*;
import com.weilanx.deepforest.analysis.mock.MockAnalysisDataStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据分析仪表盘 API 控制器
 */
@RestController
@RequestMapping("/analysis") // 基础路径
@Slf4j
public class AnalysisController {

    @Resource
    private MockAnalysisDataStore mockAnalysisDataStore; // 注入模拟数据源

    /**
     * 获取核心指标数据
     * GET /api/analysis/dashboard/metrics
     */
    @GetMapping("/dashboard/metrics")
    public ResponseEntity<List<MetricDataDto>> getMetrics() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getMetrics());
        } catch (Exception e) {
            log.error("获取指标数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取物种分类层级分布数据
     * GET /api/analysis/dashboard/charts/species-taxonomy
     */
    @GetMapping("/dashboard/charts/species-taxonomy")
    public ResponseEntity<List<NameValueDataDto>> getSpeciesTaxonomy() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getSpeciesTaxonomy());
        } catch (Exception e) {
            log.error("获取物种分类数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取物种确认状态数据
     * GET /api/analysis/dashboard/charts/species-status
     */
    @GetMapping("/dashboard/charts/species-status")
    public ResponseEntity<List<NameValueDataDto>> getSpeciesStatus() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getSpeciesStatus());
        } catch (Exception e) {
            log.error("获取物种状态数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取物种收录增长趋势数据
     * GET /api/analysis/dashboard/charts/species-growth
     */
    @GetMapping("/dashboard/charts/species-growth")
    public ResponseEntity<TimeSeriesDataDto> getSpeciesGrowth() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getSpeciesGrowth());
        } catch (Exception e) {
            log.error("获取物种增长数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取物种地理分布数据
     * GET /api/analysis/dashboard/charts/geo-distribution
     */
    @GetMapping("/dashboard/charts/geo-distribution")
    public ResponseEntity<List<GeoDistributionDataDto>> getGeoDistribution() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getGeoDistribution());
        } catch (Exception e) {
            log.error("获取地理分布数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取 Top 5 寄主植物数据
     * GET /api/analysis/dashboard/charts/top-hosts
     */
    @GetMapping("/dashboard/charts/top-hosts")
    public ResponseEntity<TopHostDataDto> getTopHosts() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getTopHosts());
        } catch (Exception e) {
            log.error("获取 Top 宿主数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取参考文献增长趋势数据
     * GET /api/analysis/dashboard/charts/reference-growth
     */
    @GetMapping("/dashboard/charts/reference-growth")
    public ResponseEntity<TimeSeriesDataDto> getReferenceGrowth() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getReferenceGrowth());
        } catch (Exception e) {
            log.error("获取文献增长数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取参考文献类型数据
     * GET /api/analysis/dashboard/charts/reference-types
     */
    @GetMapping("/dashboard/charts/reference-types")
    public ResponseEntity<List<NameValueDataDto>> getReferenceTypes() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getReferenceTypes());
        } catch (Exception e) {
            log.error("获取文献类型数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取关联文件类型数据
     * GET /api/analysis/dashboard/charts/file-types
     */
    @GetMapping("/dashboard/charts/file-types")
    public ResponseEntity<List<NameValueDataDto>> getFileTypes() {
        try {
            return ResponseEntity.ok(mockAnalysisDataStore.getFileTypes());
        } catch (Exception e) {
            log.error("获取文件类型数据失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}