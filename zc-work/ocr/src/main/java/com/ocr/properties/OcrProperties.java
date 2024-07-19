package com.ocr.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
@Setter
@Getter
@ConfigurationProperties(prefix = "ocr.config")
@RefreshScope
public class OcrProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;

}
