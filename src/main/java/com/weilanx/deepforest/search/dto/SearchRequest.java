package com.weilanx.deepforest.search.dto;

import lombok.Data;

/**
 * 搜索请求参数 DTO
 * 用于封装前端传递过来的搜索和筛选条件
 */
@Data
public class SearchRequest {
    private String query;       // 搜索关键词
    private Integer page = 1;   // 页码, 默认为 1
    private Integer pageSize = 10; // 每页大小, 默认为 10
    private String type;        // 类型过滤 (species, document)
    private String classification; // 物种分类
    private String status;        // 物种状态
    private String taxonomicLevel; // 分类层级
    private String continent;     // 大洲
    private String country;       // 国家
    private String province;      // 省份
    private String hostName;      // 寄主名称
    private String hostType;      // 寄主类型
    private String refType;       // 文献类型
    private Integer pubYear;       // 发表年份

    // Getter 方法，确保 page 和 pageSize 不为 null 或负数
    public int getPage() {
        return (page == null || page < 1) ? 1 : page;
    }

    public int getPageSize() {
        return (pageSize == null || pageSize < 1) ? 10 : pageSize;
    }
}