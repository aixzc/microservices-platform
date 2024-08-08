package com.central.ocr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.central.ocr.model.AnalysisResult;
import com.central.common.model.PageResult;
import com.central.common.service.ISuperService;

import java.util.Map;

/**
 *
 *
 * @author lyric
 * @date 2024-08-05 13:52:43
 */
public interface IAnalysisResultService extends ISuperService<AnalysisResult> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<AnalysisResult> findList(Map<String, Object> params);
}

