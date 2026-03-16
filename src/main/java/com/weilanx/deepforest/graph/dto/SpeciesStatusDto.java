// src/main/java/com/weilanx/deepforest/graph/dto/SpeciesStatusDto.java
package com.weilanx.deepforest.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物种确认状态 DTO
 * 用于统计不同状态下的物种数量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesStatusDto {
    /**
     * 状态名称（如：已确认、待审核、有疑问）
     */
    private String name;

    /**
     * 该状态下的物种数量
     */
    private Integer value;
}
