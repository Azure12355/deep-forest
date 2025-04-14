package com.weilanx.deepforest.search.service;

import com.weilanx.deepforest.search.dto.PageVO;
import com.weilanx.deepforest.search.dto.SearchRequest;
import com.weilanx.deepforest.search.dto.SearchResultItemVO;
import com.weilanx.deepforest.search.dto.SpeciesDetailVO;
import com.weilanx.deepforest.search.mock.MockSearchData; // 引入 Mock 数据类
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 搜索服务层
 */
@Service
public class SearchService {

    // 注意：这里可以直接注入 MockSearchData Bean，因为它被 @Component 标记
    // 如果 MockSearchData 没有 @Component，则需要 new MockSearchData() 或使用静态方法
    @Resource
    private MockSearchData mockSearchData;

    /**
     * 执行搜索并返回分页结果
     * @param request 搜索请求参数
     * @return 分页后的搜索结果
     */
    public PageVO<SearchResultItemVO> search(SearchRequest request) {
        // 调用 Mock 数据类执行模拟搜索
        // TODO: 将来替换为调用 Elasticsearch 或数据库的真实搜索逻辑
        return mockSearchData.search(request);
    }

    /**
     * 根据 ID 获取物种详情
     * @param speciesId 物种 ID
     * @return 物种详情 VO，如果找不到则返回 null
     */
    public SpeciesDetailVO getSpeciesDetailById(String speciesId) {
        // 调用 Mock 数据类获取模拟详情
        // TODO: 将来替换为从数据库或知识图谱获取真实详情的逻辑
        return mockSearchData.getSpeciesDetailById(speciesId);
    }
}