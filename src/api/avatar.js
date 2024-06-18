import requests from "@/util/request.js"


//获取用户头像地址

export const getAvatarUrlService = () => {
    return requests.get('/user/userinfo');
}

//更新用户头像地址
export const updateAvatarService = (avatarUrl) => {
    let params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl)
    return requests.patch('/user/updateAvatar', params);
}