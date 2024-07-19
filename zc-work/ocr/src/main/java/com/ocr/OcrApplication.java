package com.ocr;
import com.ocr.properties.OcrProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(OcrProperties.class)
public class OcrApplication {
    public static void main(String[] args) {
        SpringApplication.run(OcrApplication.class, args);
    }
}
