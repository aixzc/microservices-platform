import axios, {AxiosInstance} from 'axios';
import {ElMessage, ElMessageBox} from 'element-plus';
import {Session} from '/@/utils/storage';
import qs from 'qs';

// 配置新建一个 axios 实例
const service: AxiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 50000,
    headers: {'Content-Type': 'application/json'},
    paramsSerializer: {
        serialize(params) {
            return qs.stringify(params, {allowDots: true});
        },
    },
});

// 添加请求拦截器
service.interceptors.request.use(
    (config) => {
        const token = Session.get('token');
        console.log('config', config)
        // 在发送请求之前做些什么 token
        if ((config.headers['Authorization'] == undefined && token != undefined) ||
            (config.headers['Authorization'] != undefined && !config.headers['Authorization'].startsWith('Basic'))) {
            config.headers!['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

// 添加响应拦截器
service.interceptors.response.use(
    (response) => {
        const res = response.data;
        if (res.resp_code && res.resp_code !== 0) {
            console.log(22222)
            // `token` 过期或者账号已在别处登录
            if (res.resp_code === 401 || res.resp_code === 4001) {
                console.log(33333)
                Session.clear(); // 清除浏览器全部临时缓存
                window.location.href = '/'; // 去登录页
                ElMessageBox.alert('你已被登出，请重新登录', '提示', {});
            }
            ElMessageBox.alert(res.resp_msg, '提示', {})
            return Promise.reject(service.interceptors.response);
        } else {
            if (res.resp_code && res.resp_code === 0 && res.resp_msg && res.resp_msg.length > 0) ElMessage.success(res.resp_msg);
            if (res.datas) return res.datas;
            return res;
        }
    },
    (error) => {
        console.log(55555)
        // 对响应错误做点什么
        if (error.message.indexOf('timeout') != -1) {
            ElMessage.error('网络超时');
        } else if (error.message == 'Network Error') {
            ElMessage.error('网络连接错误');
        } else if (error.response.status == '401') {
            ElMessage.error('登陆已失效，请重新登陆');
            Session.clear(); // 清除浏览器全部临时缓存
            window.location.href = '/'; // 去登录页
        } else {
            if (error.response.data && error.response.data.resp_msg) ElMessage.error(error.response.data.resp_msg);
            else console.log('token有问题');
            // ElMessage.error('内部错误，请联系管理员');
        }
    }
);

// 导出 axios 实例
export default service;
