import {Session} from "/@/utils/storage";
import {initFrontEndControlRoutes} from "/@/router/frontEnd";
import {initBackEndControlRoutes} from "/@/router/backEnd";
import {useThemeConfig} from '/@/stores/themeConfig';
import {storeToRefs} from 'pinia';
import {clientObj, useLoginApi} from '/@/api/system/login';

const storesThemeConfig = useThemeConfig();
const {themeConfig} = storeToRefs(storesThemeConfig);


export async function signIn(form: object, client: clientObj | undefined) {
    const res = await useLoginApi().signIn(form, client);
    // 存储 token 到浏览器缓存
    Session.set('token', res.access_token);
    // 存储 当前系统id
    Session.set('system', client != undefined ? client.clientId : themeConfig.value.systemClientId);
    if (!themeConfig.value.isRequestRoutes) {
        // 前端控制路由，2、请注意执行顺序
        return await initFrontEndControlRoutes();
    } else {
        // 模拟后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
        // 添加完动态路由，再进行 router 跳转，否则可能报错 No match found for location with path "/"
        // 执行完 initBackEndControlRoutes，再执行 signInSuccess
        return await initBackEndControlRoutes();
    }
}