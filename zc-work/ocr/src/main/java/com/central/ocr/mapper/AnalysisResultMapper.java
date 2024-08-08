package com.central.ocr.mapper;

import com.central.ocr.model.AnalysisResult;
import com.central.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lyric
 * @date 2024-08-05 13:52:43
 */
@Mapper
public interface AnalysisResultMapper extends SuperMapper<AnalysisResult> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<AnalysisResult> findList(Page<AnalysisResult> page, @Param("p") Map<String, Object> params);
}
