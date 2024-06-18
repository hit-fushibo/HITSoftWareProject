import requests from "@/util/request.js"


//获取树

export const getTreeService = (uidJson) => {
    //借助urlSearchParams完成传递
    const params = new URLSearchParams();
    for (let key in uidJson) {
        params.append(key, uidJson[key]);
    }

    return requests.post("/tree/getTree",params);
}