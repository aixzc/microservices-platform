import request from '/@/utils/request';
import model from '/@/api/common/model';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 登录api接口集合
 * @method signIn 用户登录
 * @method checkToken 检查token是否有效
 * @method removeToken 使token失效
 */
export function useLoginApi() {
    return {
        signIn: (data: object) => {
            return request({
                url: model.uaa.name + 'oauth/token',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': 'Basic d2ViQXBwOndlYkFwcA=='
                },
                method: 'post',
                data,
            });
        },
        checkToken: (params: object) => {
            return request({
                url: model.uaa.name + 'oauth/check_token',
                method: 'post',
                params,
            });
        },
        removeToken: (params: object) => {
            return request({
                url: model.uaa.name + 'oauth/remove/token',
                method: 'post',
                params,
            });
        }
    };
}