package com.central.ocr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.central.common.model.PageResult;
import com.central.common.service.ISuperService;
import com.central.ocr.model.AnalysisFile;

import java.util.Map;

/**
 *
 *
 * @author lyric
 * @date 2024-08-05 11:24:43
 */
public interface IAnalysisFileService extends IService<AnalysisFile> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<AnalysisFile> findList(Map<String, Object> params);

    /**
     * 根据文件id解析文字
     */
    void analysisFileByFileId(String fileId);
}

