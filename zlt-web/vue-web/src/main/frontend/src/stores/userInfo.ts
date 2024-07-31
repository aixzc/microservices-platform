import {defineStore} from 'pinia';
import {Session} from '/@/utils/storage';
import {useUserApi} from '/@/api/system/user';
import {useFileApi} from '/@/api/system/file';
import boy from "/@/assets/define-boy.svg";
import girl from "/@/assets/define-girl.svg";

/**
 * 用户信息
 * @methods setUserInfos 设置用户信息
 */
export const useUserInfo = defineStore('userInfo', {
    state: (): UserInfosState => ({
        userInfos: {
            user: {},
            userName: '',
            photo: '',
            time: 0,
            roles: [],
            authBtnList: [],
        },
    }),
    actions: {
        async setUserInfos() {
            // 存储用户信息到浏览器缓存
            if (Session.get('userInfo')) {
                this.userInfos = Session.get('userInfo');
            } else {
                const userInfos = <UserInfos>await this.getApiUserInfo();
                this.userInfos = userInfos;
            }
        },
        // 模拟接口数据
        // https://gitee.com/lyt-top/vue-next-admin/issues/I5F1HP
        async getApiUserInfo() {
            return new Promise((resolve) => {
                setTimeout(async () => {
                    // 用户信息
                    const data = await useUserApi().getCurrentUser();
                    const userInfos = {
                        user: data,
                        photo: !data.headImgUrl ? data.sex == 0 ? boy : girl : await useFileApi().getUrl({path: data.headImgUrl}),
                        time: new Date().getTime(),
                        roles: data.roles,
                        authBtnList: data.permissions,
                    };
                    Session.set('userInfo', userInfos);
                    resolve(userInfos);
                }, 0);
            });
        },
    },
});
