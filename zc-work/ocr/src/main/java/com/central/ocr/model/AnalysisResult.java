package com.central.ocr.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.central.common.model.SuperEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author lyric
 * @date 2024-08-05 13:52:43
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("ocr_analysis_result")
public class AnalysisResult extends SuperEntity<AnalysisResult> {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer analysisId;
    private String resultContent;
    private Integer sortNum;
    @TableLogic
    private Integer deleted;
}
