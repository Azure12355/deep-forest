package com.weilanx.deepforest.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 物种详细信息视图对象
 * 对应前端 src/search/detail/types.ts 中的 SpeciesDetailData
 * 使用 @JsonInclude(JsonInclude.Include.NON_NULL) 避免序列化 null 字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // 序列化时不包含 null 值的字段
public class SpeciesDetailVO {
    private String id;
    private String chineseName;
    private String scientificName;
    private String authorship;
    private String status;      // "confirmed", "pending", "unknown"
    private String statusText;  // "已确认", "待审核", "未知"
    private String iconClass;   // 可选的建议图标类名

    // 基本信息
    private String englishName;
    private String englishAbbr;
    private String taxonomicUnit;
    private String riskCode;
    private String guid;
    private String description;
    private String sources;

    // 嵌套信息对象
    private BiologyInfoVO biology;
    private MorphologyInfoVO morphology;
    private DistributionVO distribution;
    private HostInfoVO host;
    private TransmissionInfoVO transmission;
    private ManagementInfoVO management;
    private List<ReferenceInfoVO> references;
    private List<TaxonomyItemVO> taxonomy;
    private List<ImageVO> images;
    private List<OtherNameVO> otherNames;
    private MetadataVO metadata;

    // --- 嵌套 VO 定义 ---

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BiologyInfoVO {
        private String properties;
        private String stages;
        private String visibility;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MorphologyInfoVO {
        private String characteristics;
        private List<String> detectionMethods;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DistributionVO {
        private String description;
        private List<DistributionAreaVO> areas;
        private String statusDescription;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DistributionAreaVO {
        private String region;
        private List<String> locations;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HostInfoVO {
        private String rangeDescription;
        private List<HostItemVO> hosts;
        private String affectedParts;
        private String intensity;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class HostItemVO {
        private String name;
        private String scientificName;
        private String type; // "primary", "secondary", "occasional"
        private String category;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TransmissionInfoVO {
        private List<MediumInfoVO> mediums;
        private String pathwayDescription;
        private String ecoImpact;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MediumInfoVO {
        private String name;
        private String type; // "Vector", "Other"
        private String method;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ManagementInfoVO {
        private String summary;
        private List<String> methods;
        private String remark;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReferenceInfoVO {
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

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TaxonomyItemVO {
        private String rank;
        private String name;
        private Boolean isCurrent;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ImageVO {
        private String id;
        private String src;
        private String alt;
        private String caption;
        private String type;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OtherNameVO {
        private String type;
        private String name;
        private String year;

        public OtherNameVO(String s, String fallWebworm) {
            this.type = s;
            this.name = fallWebworm;
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MetadataVO {
        private String creator;
        private String createdAt;
        private String editor;
        private String updatedAt;
        private String reviewer;
        private String reviewedAt;
    }
}