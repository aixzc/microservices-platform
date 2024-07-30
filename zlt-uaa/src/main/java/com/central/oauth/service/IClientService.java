package com.central.oauth.service;

import com.central.common.model.PageResult;
import com.central.common.service.ISuperService;
import com.central.oauth.model.Client;

import java.util.List;
import java.util.Map;

/**
 * @author zlt
 * <p>
 * Blog: http://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
public interface IClientService extends ISuperService<Client> {
    void saveClient(Client clientDto) throws Exception;

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

    void delClient(long id);

    Client loadClientByClientId(String clientId);

    List<Client> findClientByUserId(Long userId);


}
