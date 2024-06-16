//定制请求实例

import axios from "axios";

const baseURL="http://localhost:9090"
const instance=axios.create({baseURL})

instance.interceptors.response.use(
    result=>{
        return result.data;
    },
    err=>{
        alert('服务异常');
        return Promise.reject(err);
    }
)

export default instance;