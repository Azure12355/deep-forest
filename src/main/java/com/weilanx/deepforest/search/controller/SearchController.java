package com.weilanx.deepforest.search.controller;

import com.weilanx.deepforest.common.BaseResponse;
import com.weilanx.deepforest.common.ErrorCode; // 引入错误码
import com.weilanx.deepforest.common.ResultUtils;
import com.weilanx.deepforest.search.dto.PageVO;
import com.weilanx.deepforest.search.dto.SearchRequest;
import com.weilanx.deepforest.search.dto.SearchResultItemVO;
import com.weilanx.deepforest.search.dto.SpeciesDetailVO;
import com.weilanx.deepforest.search.service.SearchService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索接口控制器
 */
@RestController
@RequestMapping("/search") // 基础路径
public class SearchController {

    @Resource
    private SearchService searchService;

    /**
     * 执行搜索的 API 端点
     * 使用 @ModelAttribute 将 GET 请求的查询参数自动绑定到 SearchRequest 对象
     *
     * @param searchRequest 包含查询和分页参数的请求对象
     * @return 分页搜索结果
     */
    @GetMapping
    public BaseResponse<PageVO<SearchResultItemVO>> search(@ModelAttribute SearchRequest searchRequest) {
        PageVO<SearchResultItemVO> results = searchService.search(searchRequest);
        return ResultUtils.success(results);
        // 可以添加异常处理
    }

    /**
     * 获取物种详情的 API 端点
     *
     * @param speciesId 物种 ID (从路径中获取)
     * @return 物种详情信息
     */
    @GetMapping("/species/{speciesId}") // 定义带路径变量的端点
    public BaseResponse<SpeciesDetailVO> getSpeciesDetail(@PathVariable String speciesId) {
        SpeciesDetailVO detail = searchService.getSpeciesDetailById(speciesId);
        if (detail != null) {
            return ResultUtils.success(detail);
        } else {
            // 如果未找到，返回特定错误码和消息
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR, "未找到指定ID的物种信息");
        }
        // 可以添加异常处理
    }

    // 未来可以添加 /api/search/documents/{docId} 等其他详情接口
}