package com.central.ocr.service;

import com.central.ocr.vo.OcrAnalysisDataVo;

import java.util.concurrent.ExecutionException;

public interface IOcrService {

    /**
     * 根据图片解析文字
     *
     * @return
     */
    OcrAnalysisDataVo loadByImgUrl(String imgUrl) throws ExecutionException, InterruptedException;
}
