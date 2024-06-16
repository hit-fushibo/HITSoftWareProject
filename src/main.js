import { createApp } from 'vue' //导入Vue
import ElementPlus from 'element-plus' //导入element-plus
import 'element-plus/dist/index.css' //导入element-plus样式
import App from './App.vue' //导入App.vue
import locale from "element-plus/dist/locale/zh-cn.js"

const app = createApp(App) //创建组件实例

app.use(ElementPlus,{locale}) //使用element-plus
app.mount('#app')