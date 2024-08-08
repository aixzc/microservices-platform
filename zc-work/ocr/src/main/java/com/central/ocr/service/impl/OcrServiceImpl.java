package com.central.ocr.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.ocr_api20210707.AsyncClient;
import com.aliyun.sdk.service.ocr_api20210707.models.RecognizeAdvancedRequest;
import com.aliyun.sdk.service.ocr_api20210707.models.RecognizeAdvancedResponse;
import com.central.ocr.properties.OcrProperties;
import com.central.ocr.service.IOcrService;
import com.central.ocr.vo.OcrAnalysisDataVo;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class OcrServiceImpl implements IOcrService, InitializingBean {

    @Autowired
    private OcrProperties ocrProperties;

    private AsyncClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(ocrProperties.getAccessKeyId())
                .accessKeySecret(ocrProperties.getAccessKeySecret())
                .build());
        client = AsyncClient.builder()
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(ocrProperties.getEndpoint())
                ).build();
    }

    @Override
    public OcrAnalysisDataVo loadByImgUrl(String imgUrl) throws ExecutionException, InterruptedException {
        RecognizeAdvancedRequest recognizeAdvancedRequest = RecognizeAdvancedRequest.builder()
                .url(imgUrl)
                .build();
        CompletableFuture<RecognizeAdvancedResponse> response = client.recognizeAdvanced(recognizeAdvancedRequest);
        RecognizeAdvancedResponse resp = response.get();
        Assert.state(!resp.getStatusCode().equals("200"), "ocr识别失败");
        return JSON.toJavaObject(JSON.parseObject(resp.getBody().getData()), OcrAnalysisDataVo.class);
    }

}
