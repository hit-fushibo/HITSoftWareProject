<script setup>
import { ref } from 'vue'
const userInfo = ref({
    uid: 100000001,
    name: 'zhangsan',
    nickname: 'zs',
    phone: '12345678910',
    email: 'zs@163.com',
    mypage: 'http://localhost/mypage'
})
const rules = {
    name: [
        { required: true, message: '请输入用户昵称', trigger: 'blur' }
    ],
    nickname: [
        { required: true, message: '请输入用户昵称', trigger: 'blur' }
    ],
    email: [
        { required: true, message: '请输入用户邮箱', trigger: 'blur' },
        { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
    ],
    phone: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        {
            pattern: /^\d{11}$/,
            message: '手机号必须为11位数字',
            trigger: 'blur'
        }
    ],
    mypage: [
        { type: 'url', message: '网页地址格式不正确', trigger: 'blur' }
    ]
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" style="font-weight: bolder;">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="UID">
                        <el-input v-model="userInfo.uid" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="用户名" prop="name">
                        <el-input v-model="userInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称" prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    
                    <el-form-item label="用户邮箱" prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="用户手机号" prop="phone">
                        <el-input v-model="userInfo.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="个人主页" prop="mypage">
                        <el-input v-model="userInfo.mypage"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary">提交修改</el-button>
                    </el-form-item></el-form>
            </el-col>
        </el-row>
    </el-card>
</template>