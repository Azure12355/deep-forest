package com.weilanx.deepforest.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 搜索结果项视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultItemVO {
    private String id;
    private String type; // "species" 或 "document"
    private String icon;
    private String title;
    private String scientificName;
    private String classification;
    private String status;
    private String statusType; // confirmed, pending, default
    private String author;
    private String description;
    private List<String> tags;
    private String detailLink;
}
