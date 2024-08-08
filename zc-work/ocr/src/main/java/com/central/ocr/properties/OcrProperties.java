package com.central.ocr.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
@Setter
@Getter
@ConfigurationProperties(prefix = "ocr")
@RefreshScope
public class OcrProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;

}
