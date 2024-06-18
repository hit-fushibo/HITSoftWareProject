import requests from "@/util/request.js"

//获取全部申请
export const getAllRequestService=()=>{
    return requests.get("/requests/getAllRequests")
}

//同意请求
export const acceptRequestService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/acceptRequest',params);
}

//拒绝请求
export const refuseRequestService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/refuseRequest',params);
}