//定制请求实例

import axios from "axios";
import { usrTokenStore } from "@/stores/token.js"
import router from '@/router'
import {ElMessage} from "element-plus"

const baseURL = "/api"
const instance = axios.create({ baseURL })

//响应拦截器
instance.interceptors.response.use(
    result => {
        //如果业务状态码为0，代表本次操作成功
        if (result.data.code == 0) {
            return result.data;
        }//代码走到这里，代表业务状态码不是0，本次操作失败
        ElMessage.error(result.data.message || '服务异常');
        return Promise.reject(result.data);//异步的状态转化成失败的状态
    },
    err => {
        console.log(err)
        //如果响应状态码时401，代表未登录，给出对应的提示，并跳转到登录页
        if (err.response.status === 401) {
            ElMessage.error('请先登录！ ')
            router.push('/login')
        } else {
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;

//请求拦截器

instance.interceptors.request.use(
    (config) => {
        //在发送请求之前做什么
        let tokenStore = usrTokenStore()
        //如果token中有值，在携带
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        return config
    },
    (err) => {
        //如果请求错误做什么
        Promise.reject(err)
    }
)