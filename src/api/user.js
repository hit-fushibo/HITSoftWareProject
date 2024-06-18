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

//提供登录接口
export const userLoginService=(registerData)=>{
    //借助urlSearchParams完成传递
    const params= new URLSearchParams();
    for(let key in registerData){
        params.append(key,registerData[key]);
    }
    return requests.post('/user/login',params);
}

//提供获取用户信息的接口
export const getUserInfoService=()=>{
    return requests.get('/user/userinfo');
}

//提供更新用户信息的接口
export const updateUserInfoService=(userInfo)=>{
    return requests.put('/user/update',userInfo);
}

//提供用户搜索功能

export const searchUsersService=(searchJson)=>{
    //借助urlSearchParams完成传递
    const params= new URLSearchParams();
    for(let key in searchJson){
        params.append(key,searchJson[key]);
    }
    return requests.post('/user/searchUsers',params);
}
