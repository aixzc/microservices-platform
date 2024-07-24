import request from '/@/utils/request';
import model from '/@/api/common/model';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 应用api接口集合
 */
export function useClientApi() {
    return {
        getAll: () => {
            return request({
                url: model.uaa.name + 'clients/all',
                method: 'get',
            });
        }
    };
}