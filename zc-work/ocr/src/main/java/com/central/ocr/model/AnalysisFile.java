package com.central.ocr.model;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2024-08-05 11:24:43
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("ocr_analysis_file")
public class AnalysisFile extends SuperEntity<AnalysisFile> {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer analysisFileId;
    private Integer creatorId;
    private String remark;
    @TableLogic
    private Integer deleted;
    private Integer updateId;

    @TableField(exist = false)
    private String fileName;

    @TableField(exist = false)
    private String filePath;
}
