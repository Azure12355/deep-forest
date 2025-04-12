package com.weilanx.deepforest.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {
    private String id;
    private String type; // "image" 或 "file"
    private String name;
    private String url; // 后端提供的访问 URL
}