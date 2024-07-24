import request from '/@/utils/request';
import model from '/@/api/common/model';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 文件api接口集合
 */
export function useFileApi() {
    return {
        getUrl: (data: object) => {
            return request({
                url: model.file.name + 'url',
                method: 'post',
                data
            });
        }
    };
}