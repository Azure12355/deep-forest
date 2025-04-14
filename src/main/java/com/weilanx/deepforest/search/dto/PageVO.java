package com.weilanx.deepforest.search.dto; // 或放在 common 包下

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页响应视图对象
 * @param <T> 记录的类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
    /**
     * 当前页的记录列表
     */
    private List<T> records;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 当前页码
     */
    private long page;
    /**
     * 每页大小
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPages;

    /**
     * 计算总页数
     * @param total 总记录数
     * @param pageSize 每页大小
     * @return 总页数
     */
    public static long calculateTotalPages(long total, long pageSize) {
        if (pageSize <= 0) {
            return 0; // 或者抛出异常
        }
        return (total + pageSize - 1) / pageSize;
    }
}