package com.central.ocr.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * ocr 识别完成后的data实体
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OcrAnalysisDataVo {

    private String algo_version;

    private String content;

    private Integer height;

    private Integer orgWidth;

    private Integer orgHeight;

    private String prism_version;

    private Integer prism_wnum;
}
