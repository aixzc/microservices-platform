import request from '/@/utils/request';
import model from '/@/api/common/model';

/**
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * ocr api接口集合
 */
export function useOcrApi() {
    return {
        getList: (params: any) => {
            return request({
                url: model.ocr.name + 'analysis/file',
                method: 'get',
                params
            });
        },
        ocrAnalysis: (fileId: string) => {
            return request({
                url: model.ocr.name + `analysis/file/${fileId}`,
                method: 'post'
            });
        },
    };
}