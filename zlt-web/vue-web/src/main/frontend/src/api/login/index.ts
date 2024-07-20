import request from '/@/utils/request';
import model from '/@/api/common/model';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 登录api接口集合
 * @method signIn 用户登录
 * @method signOut 用户退出登录
 */
export function useLoginApi() {
	return {
		signIn: (data: object) => {
			return request({
				url: '/user/signIn',
				method: 'post',
				data,
			});
		},
		signOut: (data: object) => {
			return request({
				url: '/user/signOut',
				method: 'post',
				data,
			});
		},
		//获取二维码
		getCheckImgUrl: (uuid: String) => {
			return request({
				url: model.uaa.name + 'validata/code/' + uuid,
				method: 'get'
			});
		},
	};
}