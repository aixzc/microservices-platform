package com.central.generator.service;

import com.central.common.model.PageResult;
import com.central.common.service.ISuperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zlt
 */
@Service
public interface SysGeneratorService extends ISuperService<Map<String, Object>> {
    PageResult queryList(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    byte[] generatorCode(String[] tableNames);
}
