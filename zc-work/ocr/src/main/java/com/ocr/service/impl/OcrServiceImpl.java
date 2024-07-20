package com.ocr.service.impl;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.ocr_api20210707.AsyncClient;
import com.aliyun.sdk.service.ocr_api20210707.models.RecognizeHandwritingRequest;
import com.aliyun.sdk.service.ocr_api20210707.models.RecognizeHandwritingResponse;
import com.google.gson.Gson;
import com.ocr.properties.OcrProperties;
import com.ocr.service.IOcrService;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class OcrServiceImpl implements IOcrService, InitializingBean {

    @Resource
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
    public String loadByImgUrl(String imgUrl) throws ExecutionException, InterruptedException {

        RecognizeHandwritingRequest recognizeHandwritingRequest = RecognizeHandwritingRequest.builder().url(imgUrl).needRotate(false)
                .build();
        CompletableFuture<RecognizeHandwritingResponse> response = client.recognizeHandwriting(recognizeHandwritingRequest);
        RecognizeHandwritingResponse resp = response.get();
        System.out.println(new Gson().toJson(resp));
        client.close();
        return new Gson().toJson(resp);
    }

}
