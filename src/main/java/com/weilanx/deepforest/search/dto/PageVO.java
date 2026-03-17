package com.weilanx.deepforest.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页响应视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    private List<T> records;
    private long total;
    private int page;
    private int pageSize;
    private int totalPages;
}
