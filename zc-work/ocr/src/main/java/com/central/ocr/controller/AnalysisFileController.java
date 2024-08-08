package com.central.ocr.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.central.common.model.PageResult;
import com.central.common.model.Result;
import com.central.ocr.model.AnalysisFile;
import com.central.ocr.model.AnalysisResult;
import com.central.ocr.service.IAnalysisFileService;
import com.central.ocr.service.IAnalysisResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author lyric
 * @date 2024-08-05 11:24:43
 */
@Slf4j
@RestController
@RequestMapping("/analysis/file")
@Tag(name = "ocr文件控制层")
public class AnalysisFileController {

    @Autowired
    private IAnalysisFileService analysisFileService;

    @Autowired
    private IAnalysisResultService analysisResultService;

    /**
     * 列表
     */
    @Operation(summary = "查询列表")
    @Parameters({
            @Parameter(name = "page", description = "分页起始位置", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "limit", description = "分页结束位置", required = true, in = ParameterIn.QUERY)
    })
    @GetMapping
    public PageResult list(@RequestParam Map<String, Object> params) {
        return analysisFileService.findList(params);
    }

    /**
     * 查询
     */
    @Operation(summary = "查询")
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        AnalysisFile model = analysisFileService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 根据解析id查询识别结果
     */
    @Operation(summary = "根据解析id查询识别结果")
    @GetMapping("/result/{id}")
    public Result findResultById(@PathVariable Long id) {
        List<AnalysisResult> list = analysisResultService.list(new QueryWrapper<AnalysisResult>().eq("analysis_id", id).orderByAsc("sort_num"));
        return Result.succeed(list, "查询成功");
    }

    /**
     * 新增or更新
     */
    @Operation(summary = "保存")
    @PostMapping
    public Result save(@RequestBody AnalysisFile analysisFile) {
        analysisFileService.saveOrUpdate(analysisFile);
        return Result.succeed("保存成功");
    }

    /**
     * 删除
     */
    @Operation(summary = "删除")
    @Parameter(name = "id", description = "id", in = ParameterIn.PATH, required = true)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        analysisFileService.removeById(id);
        return Result.succeed("删除成功");
    }

    /**
     * 根据fileId识别文件
     */
    @Operation(summary = "根据fileId识别文件")
    @Parameter(name = "fileId", description = "文件id", in = ParameterIn.QUERY, required = true)
    @PostMapping("/{fileId}")
    public Result analysisFileByFileId(@PathVariable String fileId) throws ExecutionException, InterruptedException {
        analysisFileService.analysisFileByFileId(fileId);
        return Result.succeed("识别完成");
    }
}
