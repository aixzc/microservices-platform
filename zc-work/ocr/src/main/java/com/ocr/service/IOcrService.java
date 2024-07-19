package com.ocr.service;

import java.util.concurrent.ExecutionException;

public interface IOcrService {

    /**
     * 根据图片解析文字
     *
     * @return
     */
    String loadByImgUrl(String imgUrl) throws ExecutionException, InterruptedException;
}
