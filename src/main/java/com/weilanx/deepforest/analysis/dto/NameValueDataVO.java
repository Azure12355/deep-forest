package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称-值 对的视图对象 (View Object)
 * 常用于饼图、柱状图等的数据项
 * 与前端 analysis/types/index.tsx 中的 NameValueData 对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameValueDataVO {

    /**
     * 名称/标签 (例如: "昆虫纲 (Insecta)", "已确认")
     */
    private String name;

    /**
     * 数值
     */
    private Number value; // 使用 Number 以支持整数和浮点数
}