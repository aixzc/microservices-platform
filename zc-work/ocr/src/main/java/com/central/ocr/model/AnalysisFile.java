package com.central.ocr.model;

import com.baomidou.mybatisplus.annotation.*;
import com.central.common.model.SuperEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author lyric
 * @date 2024-08-05 11:24:43
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("ocr_analysis_file")
public class AnalysisFile extends SuperEntity<AnalysisFile> {
    @Serial
    private static final long serialVersionUID = 1L;
    private String analysisFileId;
    private String remark;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;

    @TableField(exist = false)
    private String fileName;

    @TableField(exist = false)
    private String filePath;
}
