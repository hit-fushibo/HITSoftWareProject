import { createRouter, createWebHistory } from "vue-router";

//导入组件
import LoginVue from "@/view/Login.vue"
import mainPage from "@/view/mainPage.vue"
import tree from "@/view/treeAndProcess/tree.vue"
import requestProcess from "@/view/treeAndProcess/requestProcess.vue";
import setting from "@/view/user/setting.vue";
import password from "@/view/user/password.vue"
import avatarUpload from "@/view/user/avatarUpload.vue";
import main from "@/view/main.vue"

//定义路由关系
const routes = [

    //页面路由
    { path: '/login', component: LoginVue },
    {
        path: '/',
        component: mainPage,
        redirect: '/treeAndProcess/tree',
        //mainPage子路由
        children: [
            { path: '/treeAndProcess/tree', component: tree },
            { path: '/treeAndProcess/requestProcess', component: requestProcess },
            { path: '/user/setting', component: setting },
            { path: '/user/password', component: password },
            { path: '/user/avatarUpload', component: avatarUpload }
        ]

    },
    { path: '/main', component: main }
]

//创建路由器

const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由器
export default router

