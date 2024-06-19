import { createApp } from 'vue' //导入Vue
import ElementPlus from 'element-plus' //导入element-plus
import 'element-plus/dist/index.css' //导入element-plus样式
import App from './App.vue' //导入App.vue
import locale from "element-plus/dist/locale/zh-cn.js"//element-plus 中文化
import router from '@/router' //导入路由
import { createPinia } from "pinia" //导入pinia
import { createPersistedState } from 'pinia-persistedstate-plugin' //持久化pinia到本地存储

const pinia = createPinia()
const persist = createPersistedState();
pinia.use(persist);//使用插件持久化pinia到本地存储
const app = createApp(App) //创建组件实例

app.use(router)//前端路由
app.use(ElementPlus, { locale }) //使用element-plus
app.use(pinia) //使用pinia持久化数据
app.mount('#app')