<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import { ref } from 'vue'
import avatar from '@/assets/logo.svg'
const uploadRef = ref()
//用户头像地址
let imgUrl = ref()
let avatarUrl='';

//获得用户头像地址
import { getAvatarUrlService } from "@/api/avatar"

const getAvatarUrl = async () => {
    let response = await getAvatarUrlService();
    console.log(response)
    if (response.data.usrPic && response.data.usrPic !== "") {
        imgUrl.value = "http://localhost:9090" + response.data.usrPic
        avatarUrl=response.data.usrPic
        console.log(imgUrl.value)
    }
}
//图片上传成功的回调
const uploadSuccess = (result) => {
    //回显图片
    imgUrl.value = "http://localhost:9090" +result.data
    avatarUrl=result.data
    console.log(imgUrl.value)
}

import {updateAvatarService} from "@/api/avatar"
const updateAvatar=async()=>{
    let response = await updateAvatarService(avatarUrl)
    ElMessage.success("成功上传")
    userInfoStore.info.userPic=imgUrl.value
}

import {usrTokenStore} from "@/stores/token"
import { ElMessage } from 'element-plus';
const tokenStore=usrTokenStore();
getAvatarUrl()

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header" style="font-weight: bolder;">
                <span>更换头像</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-upload ref="uploadRef" class="avatar-uploader" :show-file-list="false" :auto-upload="true"
                    action="/api/user/uploadAvatar" name="file" :headers="{ 'Authorization': tokenStore.token }" :limit="1"
                    :on-exceed="handleExceed"
                    :on-success="uploadSuccess">
                    <img v-if="imgUrl" :src="imgUrl" class="avatar" />
                    <img v-else :src="avatar" width="278" />
                </el-upload>
                <br />
                <el-button type="primary" :icon="Plus" size="large"
                    @click="uploadRef.$el.querySelector('input').click()">
                    选择图片
                </el-button>
                <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
                    上传头像
                </el-button>
            </el-col>
        </el-row>
    </el-card>
</template>
<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>