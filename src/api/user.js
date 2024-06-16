import requests from "@/util/request.js"


//这里不需要对请求加异步，异步处理在.vue中处理

//提供注册接口
export const userRegisterService=(registerData)=>{
    //借助urlSearchParams完成传递
    const params= new URLSearchParams();
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return requests.post('/user/register',params);
}

