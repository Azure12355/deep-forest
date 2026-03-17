package com.weilanx.deepforest.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 物种详细信息 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDetailVO {

    private String id;
    private String chineseName;
    private String scientificName;
    private String authorship;
    private String status; // confirmed, pending, unknown
    private String statusText;
    private String iconClass;

    // 基本信息
    private String englishName;
    private String englishAbbr;
    private String taxonomicUnit;
    private String riskCode;
    private String guid;
    private String description;
    private String sources;

    // 生物学特性
    private Biology biology;

    // 形态学与检测
    private Morphology morphology;

    // 地理分布
    private Distribution distribution;

    // 寄主信息
    private Host host;

    // 传播途径与影响
    private Transmission transmission;

    // 管理与防治
    private Management management;

    // 相关文献
    private List<Reference> references;

    // 分类地位
    private List<Taxonomy> taxonomy;

    // 相关图片
    private List<Image> images;

    // 其他名称
    private List<OtherName> otherNames;

    // 元数据
    private Metadata metadata;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Biology {
        private String properties;
        private String stages;
        private String visibility;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Morphology {
        private String characteristics;
        private List<String> detectionMethods;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Distribution {
        private String description;
        private List<Area> areas;
        private String statusDescription;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Area {
            private String region;
            private List<String> locations;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Host {
        private String rangeDescription;
        private List<HostItem> hosts;
        private String affectedParts;
        private String intensity;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class HostItem {
            private String name;
            private String scientificName;
            private String type; // primary, secondary, occasional
            private String category;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transmission {
        private List<Medium> mediums;
        private String pathwayDescription;
        private String ecoImpact;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Medium {
            private String name;
            private String type;
            private String method;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Management {
        private String summary;
        private List<String> methods;
        private String remark;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reference {
        private String id;
        private String title;
        private String authors;
        private String source;
        private Integer year;
        private List<String> tags;
        private String doi;
        private String link;
        private String pdfPath;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Taxonomy {
        private String rank;
        private String name;
        private Boolean isCurrent;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {
        private String id;
        private String src;
        private String alt;
        private String caption;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherName {
        private String type;
        private String name;
        private String year;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private String creator;
        private String createdAt;
        private String editor;
        private String updatedAt;
        private String reviewer;
        private String reviewedAt;
    }
}
