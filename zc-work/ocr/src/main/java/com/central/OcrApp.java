package com.central;

import com.central.common.lb.annotation.EnableFeignInterceptor;
import com.central.ocr.properties.OcrProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableFeignInterceptor
@EnableConfigurationProperties(OcrProperties.class)
@MapperScan(basePackages = "com.central.ocr.mapper")
public class OcrApp {
    public static void main(String[] args) {
        SpringApplication.run(OcrApp.class, args);
    }
}
