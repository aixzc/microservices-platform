package com.central.ocr.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.central.common.feign.FileService;
import com.central.common.model.FileInfo;
import com.central.common.model.PageResult;
import com.central.common.model.Result;
import com.central.common.service.impl.SuperServiceImpl;
import com.central.ocr.mapper.AnalysisFileMapper;
import com.central.ocr.model.AnalysisFile;
import com.central.ocr.model.AnalysisResult;
import com.central.ocr.service.IAnalysisFileService;
import com.central.ocr.service.IAnalysisResultService;
import com.central.ocr.service.IOcrService;
import com.central.ocr.vo.OcrAnalysisDataVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author lyric
 * @date 2024-08-05 11:24:43
 */
@Slf4j
@Service
public class AnalysisFileServiceImpl extends SuperServiceImpl<AnalysisFileMapper, AnalysisFile> implements IAnalysisFileService {

    @Resource
    private IOcrService ocrService;

    @Resource
    private FileService fileService;

    @Resource
    private IAnalysisResultService analysisResultService;

    /**
     * 列表
     *
     * @param params
     * @return
     */
    @Override
    public PageResult<AnalysisFile> findList(Map<String, Object> params) {
        Page<AnalysisFile> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<AnalysisFile> list = baseMapper.findList(page, params);
        return PageResult.<AnalysisFile>builder().data(list).code(0).count(page.getTotal()).build();
    }

    @Override
    @Transactional
    public void analysisFileByFileId(String fileId) throws ExecutionException, InterruptedException {
        Result<FileInfo> result = fileService.selectByFileId(fileId);
        Assert.state(result.getResp_code() == 0, "查询文件失败");
        FileInfo fileInfo = result.getDatas();
        OcrAnalysisDataVo vo = ocrService.loadByImgUrl(fileInfo.getPath());
        //插入解析主表
        AnalysisFile analysisFile = new AnalysisFile();
        analysisFile.setAnalysisFileId(fileId);
        baseMapper.insert(analysisFile);
        List<String> list = Arrays.asList(StrUtil.split(vo.getContent(), 400));
        if (CollUtil.isNotEmpty(list)) {
            List<AnalysisResult> analysisResults = new ArrayList<>();
            for (String content : list) {
                AnalysisResult analysisResult = new AnalysisResult();
                analysisResult.setAnalysisId(analysisFile.getId().intValue());
                analysisResult.setResultContent(content);
                analysisResult.setSortNum(list.indexOf(content));
                analysisResults.add(analysisResult);
            }
            if (CollUtil.isNotEmpty(analysisResults)) {
                analysisResultService.saveBatch(analysisResults);
            }
        }
    }
}
