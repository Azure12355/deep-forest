package com.weilanx.deepforest.chat.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "ark.ai")
public class ArkConfigurationProperties {
    private String apiKey;
    private String baseUrl;
    private String model;
}
