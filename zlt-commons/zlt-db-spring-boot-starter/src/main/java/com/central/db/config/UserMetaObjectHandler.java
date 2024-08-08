package com.central.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.central.common.model.LoginAppUser;
import com.central.common.utils.LoginUserUtils;
import com.central.db.properties.MybatisPlusAutoFillProperties;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Objects;

/**
 * 自定义填充公共字段
 *
 * @author lyric
 * @date 2018/12/11
 * <p>
 */
public class UserMetaObjectHandler implements MetaObjectHandler {
    private MybatisPlusAutoFillProperties autoFillProperties;

    public UserMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
        this.autoFillProperties = autoFillProperties;
    }

    /**
     * 是否开启了插入填充
     */
    @Override
    public boolean openInsertFill() {
        return autoFillProperties.getEnableInsertFill();
    }

    /**
     * 是否开启了更新填充
     */
    @Override
    public boolean openUpdateFill() {
        return autoFillProperties.getEnableUpdateFill();
    }

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object creatorId = getFieldValByName(autoFillProperties.getCreatorField(), metaObject);
        Object updateId = getFieldValByName(autoFillProperties.getUpdateField(), metaObject);
        if (creatorId == null || updateId == null) {
            LoginAppUser currentUser = LoginUserUtils.getCurrentUser(false);
            if (Objects.nonNull(currentUser)) {
                if (creatorId == null) {
                    setFieldValByName(autoFillProperties.getCreatorField(), currentUser.getId(), metaObject);
                }
                if (updateId == null) {
                    setFieldValByName(autoFillProperties.getUpdateField(), currentUser.getId(), metaObject);
                }
            }
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        LoginAppUser currentUser = LoginUserUtils.getCurrentUser(false);
        if (Objects.nonNull(currentUser)) {
            setFieldValByName(autoFillProperties.getUpdateField(), currentUser.getId(), metaObject);
        }
    }
}