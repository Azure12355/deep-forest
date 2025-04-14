package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关系线的样式视图对象 (可选)
 * 如果后端需要为特定关系指定样式，可以使用此类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineStyleVO {
    /**
     * 线的颜色
     */
    private String color;
    /**
     * 线的宽度
     */
    private Number width;
    /**
     * 透明度 (0-1)
     */
    private Number opacity;
    /**
     * 曲度 (0-1)
     */
    private Number curveness;
}