package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称-值 对数据传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameValueDataDto {
    private String name;
    private Number value; // 使用 Number 兼容整数和浮点数
}