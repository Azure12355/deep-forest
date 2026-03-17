package com.weilanx.deepforest.search.controller;

import com.weilanx.deepforest.common.BaseResponse;
import com.weilanx.deepforest.common.ResultUtils;
import com.weilanx.deepforest.search.dto.PageVO;
import com.weilanx.deepforest.search.dto.SearchResultItemVO;
import com.weilanx.deepforest.search.dto.SpeciesDetailVO;
import com.weilanx.deepforest.search.mock.MockSearchDataStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 搜索 API 控制器
 * 提供物种和文献的搜索与详情查询功能
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private MockSearchDataStore mockSearchDataStore;

    /**
     * 搜索物种或文献
     */
    @GetMapping
    public BaseResponse<PageVO<SearchResultItemVO>> search(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String classification,
            @RequestParam(required = false) String status) {

        try {
            PageVO<SearchResultItemVO> result = mockSearchDataStore.search(
                    query, page, pageSize, type, classification, status);

            log.info("搜索完成: 关键词='{}', 类型='{}', 第{}/{}页, 共{}条结果",
                    query, type, page, result.getTotalPages(), result.getTotal());

            return ResultUtils.success(result);
        } catch (Exception e) {
            log.error("搜索失败", e);
            return ResultUtils.error(50000, "搜索失败: " + e.getMessage());
        }
    }

    /**
     * 获取物种详细信息
     */
    @GetMapping("/species/{id}")
    public BaseResponse<SpeciesDetailVO> getSpeciesDetail(@PathVariable String id) {
        try {
            SpeciesDetailVO detail = mockSearchDataStore.getDetailById(id);

            if (detail == null) {
                log.warn("未找到物种详情: {}", id);
                return ResultUtils.error(40400, "未找到指定ID的物种信息");
            }

            log.info("获取物种详情成功: {} - {}", id, detail.getChineseName());
            return ResultUtils.success(detail);
        } catch (Exception e) {
            log.error("获取物种详情失败: {}", id, e);
            return ResultUtils.error(50000, "获取物种详情失败: " + e.getMessage());
        }
    }
}
