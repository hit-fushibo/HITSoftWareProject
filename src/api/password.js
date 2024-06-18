import requests from "@/util/request.js"

//提供修改密码的接口

export const updatePwdService=(updateJson)=>{
    return requests.patch('/user/updatePwd',updateJson);
}