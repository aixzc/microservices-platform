import request from '/@/utils/request';
import model from '/@/api/common/model';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 用户api接口集合
 */
export function useUserApi() {
    return {
        getCurrentUser: () => {
            return request({
                url: model.user.name + 'users/current',
                method: 'get',
            });
        },
        getUserRoles: (userId: bigint) => {
            return request({
                url: model.user.name + `users/${userId}/roles`,
                method: 'get',
            });
        }
    };
}