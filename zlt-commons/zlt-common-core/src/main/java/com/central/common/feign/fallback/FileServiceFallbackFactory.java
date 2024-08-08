package com.central.common.feign.fallback;

import com.central.common.feign.FileService;
import com.central.common.feign.UserService;
import com.central.common.model.Result;
import com.central.common.model.SysRole;
import com.central.common.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collections;
import java.util.List;

/**
 * userService降级工场
 *
 * @author zlt
 * @date 2019/1/18
 */
@Slf4j
public class FileServiceFallbackFactory implements FallbackFactory<FileService> {
    @Override
    public FileService create(Throwable throwable) {
        return new FileService() {
            @Override
            public Result selectByFileId(String id) {
                log.error("根据文件id查询失败:{}", id, throwable);
                return Result.failed("查询失败");
            }
        };
    }
}
