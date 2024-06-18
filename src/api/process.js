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

//增加自己的学生
export const addMyStudentService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/addMyStudent',params);
}
//增加自己的老师
export const addMyTeacherService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/addMyTeacher',params);
}
//增加他人的学生
export const addOthersStudentService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/addOthersStudent',params);
}
//增加他人老师
export const addOthersTeacherService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/addOthersTeacher',params);
}
//删除自己学生
export const delMyStudentService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/delMyStudent',params);
}
//删除自己老师
export const delMyTeacherService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/delMyTeacher',params);
}
//删除他人学生
export const delOthersStudentService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/delOthersStudent',params);
}
//删除他人老师
export const delOthersTeacherService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/delOthersTeacher',params);
}
//修改自己
export const modifyMyTreeService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/modifyMyTree',params);
}
//修改他人
export const modifyOthersTreeService=(postJson)=>{
    const params= new URLSearchParams();
    for(let key in postJson){
        params.append(key,postJson[key]);
    }
    return requests.post('/requests/modifyOthersTree',params);
}