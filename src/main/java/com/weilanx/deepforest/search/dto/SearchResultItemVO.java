package com.weilanx.deepforest.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 单条搜索结果视图对象
 * 用于封装返回给前端列表的单个条目信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 使用 Builder 模式方便在 Mock 中构建对象
public class SearchResultItemVO {
    private String id;             // 唯一ID
    private String type;           // 类型: "species" 或 "document"
    private String icon;           // 建议的图标标识符 (如 "faLeaf", "faBookOpen")
    private String title;          // 标题 (物种中文名或文献标题)
    private String scientificName; // 学名 (物种特有)
    private String classification; // 分类 (物种特有)
    private String status;         // 状态文字 (物种特有)
    private String statusType;     // 状态类型用于样式 (物种特有: confirmed, pending, default)
    private String author;         // 作者 (文献特有)
    private String description;    // 描述摘要
    private List<String> tags;     // 标签列表
    private String detailLink;     // 指向详情页的相对路径 (前端使用)
}