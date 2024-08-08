package com.central.ocr.controller;

import com.central.common.model.Result;
import com.central.ocr.service.IOcrService;
import com.central.ocr.vo.OcrAnalysisDataVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/ocr")
@Tag(name = "ocr识别模块api")
public class OcrController {

    @Resource
    private IOcrService ocrService;

    @GetMapping
    @Operation(summary = "根据图片url获取图片信息")
    public Result<OcrAnalysisDataVo> loadByImgUrl(String imgUrl) throws ExecutionException, InterruptedException {
        return Result.succeed(ocrService.loadByImgUrl(imgUrl));
    }
}
