package com.central.ocr.mapper;

import com.central.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.central.ocr.model.AnalysisFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lyric
 * @date 2024-08-05 11:24:43
 */
@Mapper
public interface AnalysisFileMapper extends SuperMapper<AnalysisFile> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<AnalysisFile> findList(Page<AnalysisFile> page, @Param("p") Map<String, Object> params);
}
