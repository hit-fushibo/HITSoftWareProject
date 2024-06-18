import requests from "@/util/request.js"


//获取用户头像地址

export const getAvatarUrlService=()=>{
    return requests.get('/user/userinfo');
}