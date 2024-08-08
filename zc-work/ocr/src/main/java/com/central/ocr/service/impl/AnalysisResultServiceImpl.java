package com.central.ocr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.central.common.model.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.common.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.central.ocr.model.AnalysisResult;
import com.central.ocr.mapper.AnalysisResultMapper;
import com.central.ocr.service.IAnalysisResultService;

/**
 *
 *
 * @author lyric
 * @date 2024-08-05 13:52:43
 */
@Slf4j
@Service
public class AnalysisResultServiceImpl extends SuperServiceImpl<AnalysisResultMapper, AnalysisResult> implements IAnalysisResultService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<AnalysisResult> findList(Map<String, Object> params){
        Page<AnalysisResult> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<AnalysisResult> list  =  baseMapper.findList(page, params);
        return PageResult.<AnalysisResult>builder().data(list).code(0).count(page.getTotal()).build();
    }
}
