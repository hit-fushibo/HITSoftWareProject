<script lang="ts" setup>
import { User, Lock } from "@element-plus/icons-vue"
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { usrTokenStore } from "@/stores/token.js"
import { userRegisterService, userLoginService } from "@/api/user.js"


//调用useTokenStore用于保存JWT令牌
const tokenStore = usrTokenStore();

//控制注册和登录表单显示
let isRegister = ref(false)
//绑定登录表单数据
const login_id = ref("")
const login_pwd = ref("")
//绑定注册表单数据
const singUp_phone = ref("")
const singUp_pwd = ref("")
const singUp_pwd_confirm = ref("")
//路由管理
const router = useRouter();

const singUp = async () => {
    //获得数据
    let phone = singUp_phone.value
    let pwd = singUp_pwd.value
    let pwd_confirm = singUp_pwd_confirm.value
    //数据校验
    const phonePattern = /^\d{11}$/;
    if (!phonePattern.test(phone)) {
        ElMessage.error("手机号必须为11位数字")
        return
    }
    if (pwd !== pwd_confirm) {
        ElMessage.error("两次密码需要相同")
        return
    }
    //请求数据封装
    let registerData = {
        userPhone: phone,
        password: pwd
    }
    //向后端发送注册请求
    let response = await userRegisterService(registerData)
    //处理后端响应
    if (response.code !== 0) {
        //重复手机号
        ElMessage.error(response.message);
    }
    else {
        //成功注册
        ElMessage.success("注册成功")
        login_id.value = phone
        login_pwd.value = pwd
        isRegister.value = false
    }
}



const login = async () => {
    let id = login_id.value
    let pwd = login_pwd.value
    let pori = 0
    if (id.length === 11) {
        pori = 0
    }
    else if (id.length === 9) {
        pori = 1
    }
    else {
        ElMessage.error("登录id只能为9位uid或11位手机号")
        return
    }
    if (pwd.length === 0) {
        ElMessage.error("请输入密码")
        return
    }
    let loginData = {
        id: id,
        password: pwd,
        pori: pori
    }
    let response = await userLoginService(loginData)
    if (response.code !== 0) {
        ElMessage.error(response.message);
    }
    else {
        //借助路由完成跳转
        tokenStore.setToken(response.data);
        ElMessage.success("登录成功")
        router.push('/')
    }

}

</script>

<template>
    <div>
        <el-row class="login-page">
            <el-col :span="12" class="bg"></el-col>
            <el-col :span="6" :offset="3" class="form">
                <!-- 注册表单 -->
                <el-form ref="from" size="large" autocomplete="off" v-if="isRegister">
                    <el-form-item>
                        <h1>注册</h1>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="User" v-model="singUp_phone" placeholder="请输入手机号"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="Lock" v-model="singUp_pwd" placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="Lock" v-model="singUp_pwd_confirm" placeholder="请再次输入密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button class="button" type="primary" auto-insert-space @click="singUp">
                            注册
                        </el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-link type="info" :underline="true" @click="isRegister = false">
                            返回
                        </el-link>
                    </el-form-item>
                </el-form>

                <!-- 登录表单 -->
                <el-form ref="from" size="large" autocomplete="off" v-if="!isRegister">
                    <el-form-item>
                        <h1>登录</h1>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="User" v-model="login_id" placeholder="请输入uid/手机号"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="Lock" v-model="login_pwd" placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button class="button" type="primary" auto-insert-space @click="login">
                            登录
                        </el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-link type="info" :underline="true" @click="isRegister = true">
                            注册
                        </el-link>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background:
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>