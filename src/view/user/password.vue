<script lang="ts" setup>
import { ref } from 'vue'
const passwordInfo = ref({
    originPwd:"",
    newPwd:"",
    confirmPwd:""
})
const checkRePwd = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== passwordInfo.value.newPwd) {
        callback(new Error("两次密码不匹配"))
    } else {
        callback()
    }
}
const rules = {
    originPwd:[
        { required: true, message: '请输入原密码', trigger: 'blur' }

    ],
    newPwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' }
    ],
    confirmPwd: [
        { validator: checkRePwd, trigger: "blur" }
    ]
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" style="font-weight: bolder;">
                <span>修改密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="passwordInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="原密码" prop="originPwd">
                        <el-input v-model="passwordInfo.originPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPwd">
                        <el-input v-model="passwordInfo.newPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="确认新密码" prop="confirmPwd">
                        <el-input v-model="passwordInfo.confirmPwd"></el-input>
                    </el-form-item>
                    
                    <el-form-item>
                        <el-button type="primary">提交修改</el-button>
                    </el-form-item></el-form>
            </el-col>
        </el-row>
    </el-card>
</template>