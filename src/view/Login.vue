<script lang="ts" setup>
import { User, Lock } from "@element-plus/icons-vue"
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'
//控制注册和登录表单显示
const isRegister = ref(false)
//绑定数据
const login_id = ref("")
const login_pwd = ref("")

const singUp_phone = ref("")
const singUp_pwd = ref("")
const singUp_pwd_confirm = ref("")
//设置校验规则
interface registerRuleForm {
    phone: string
    pwd: string
    pwd_confirm: string
}

interface loginRuleForm {
    id: string
    pwd: string
}

const checkPhone = (rule: any, value: any, callback: any) => {
    const phonePattern = /^\d{11}$/;
    if (!phonePattern.test(value)) {
        callback(new Error('手机号只能为11位数字'))
    } else {
        callback()
    }
}

//校验二次密码
const checkRePwd = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== singUp_pwd.value) {
        callback(new Error("两次密码不匹配"))
    } else {
        callback()
    }
}

const registerRules = reactive<FormRules<registerRuleForm>>({
    phone: [
        { required: true, message: "请输入手机号", trigger: "blur" },
        { validator: checkPhone, trigger: "blur" }
    ],
    pwd: [
        { required: true, message: "请输入密码", trigger: "blur" },
    ],
    pwd_confirm: [
        { validator: checkRePwd, trigger: "blur" }
    ]
})

const checkId = (rule: any, value: any, callback: any) => {
    if (value.length !== 11 && value.length !== 9) {
        callback(new Error('登录id只能为9位uid或11位手机号'))
    } else {
        callback()
    }
}

const loginRules = reactive<FormRules<loginRuleForm>>({
    id: [
        { required: true, message: "请输入uid/手机号", trigger: "blur" },
        { validator: checkId, trigger: "blur" }
    ],
    pwd: [
        { required: true, message: "请输入密码", trigger: "blur" },
    ]
})


import {userRegisterService} from "@/api/user.js"

const singUp = async () => {
    let phone = singUp_phone.value
    let pwd = singUp_pwd.value
    let pwd_confirm = singUp_pwd_confirm.value
    const phonePattern = /^\d{11}$/;
    if (!phonePattern.test(phone)) {
        alert("手机号必须为11位数字")
        return
    }
    if (pwd !== pwd_confirm) {
        alert("两次密码需要相同")
        return
    }
    let registerData={
        userPhone:phone,
        password:pwd
    }
    console.log(registerData)
    let response = await userRegisterService(registerData)
    if(response.code!==0){
        alert(response.message);
    }
    else {
        alert("注册成功")
    }
}

const login = async ()=>{
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
        alert("登录id只能为9位uid或11位手机号")
        return
    }
    if (pwd.length === 0) {
        alert("请输入密码")
        return
    }
    let loginData={
        id:id,
        password:pwd,
        pori:pori
    }
    let response = await userRegisterService(loginData)
    if(response.code!==0){
        alert(response.message);
    }
    else {
        alert("登录成功")
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