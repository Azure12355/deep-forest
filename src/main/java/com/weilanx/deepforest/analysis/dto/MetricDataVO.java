package com.weilanx.deepforest.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指标卡片视图对象 (View Object)
 * 用于封装返回给前端的单个指标卡片数据
 * 与前端 analysis/types/index.tsx 中的 MetricData 对应
 */
@Data // Lombok 注解，自动生成 getter, setter, toString, equals, hashCode 方法
@NoArgsConstructor // Lombok 注解，生成无参构造函数
@AllArgsConstructor // Lombok 注解，生成包含所有参数的构造函数
public class MetricDataVO {

    /**
     * 唯一标识符 (例如: "species", "refs")
     */
    private String id;

    /**
     * 图标名称 (例如: "leaf", "book-open")
     * 需要与前端 FontAwesome 或其他图标库的名称对应
     */
    private String icon;

    /**
     * 指标标签/名称 (例如: "物种总数")
     */
    private String label;

    /**
     * 指标值 (可以是数字或字符串，使用 Object 增加灵活性)
     * (例如: 1853, "88.5")
     */
    private Object value;

    /**
     * 单位 (可选, 例如: "GB")
     */
    private String unit;

    /**
     * 周期标签 (可选, 例如: "本周新增", "已收录")
     */
    private String periodLabel;

    /**
     * 周期值 (可选, 例如: 150)
     */
    private Object periodValue;

    /**
     * 用于前端设置背景渐变的 CSS 类名 (例如: "gradient-green")
     */
    private String gradientClass;
}