package com.central.common.feign;

import com.central.common.constant.ServiceNameConstants;
import com.central.common.feign.fallback.FileServiceFallbackFactory;
import com.central.common.model.FileInfo;
import com.central.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = ServiceNameConstants.FILE_SERVICE, fallbackFactory = FileServiceFallbackFactory.class, dismiss404 = true)
public interface FileService {

    /**
     * feign rpc访问远程/files/{id}接口
     * 查询文件
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/files/{id}")
    Result<FileInfo> selectByFileId(@PathVariable("id") String id);
}
