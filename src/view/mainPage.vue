<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    StarFilled,
    Setting
} from '@element-plus/icons-vue'
import avatar from '@/assets/logo.svg'
import { ref } from "vue"

const name = ref('default name')

let teachers = ref([])
let students = ref([])
let me = ref({})
let isMe = ref(true)

//dropDown条目被点击后，回调的函数
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
const router = useRouter()
const handleCommand = (command) => {
    if (command === 'logout') {
        //退出登录
        ElMessageBox.confirm(
            '你确认退出登录码？ ',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        ).then(async () => {
            //用户点击了确认
            //清空pinia中的token和个人信息
            //跳转到登录页
            router.push('/login')
        }).catch(() => {
            //用户点击了取消
            ElMessage({
                type: 'info',
                message: '取消退出',
            })
        })
    } else {
        //路由
        router.push('/user/' + command)
    }
}

</script>
<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu default-active="1" active-text-color="#ffd04b" router>
                <el-menu-item index="/treeAndProcess/tree">
                    <el-icon>
                        <StarFilled />
                    </el-icon>
                    <span>师承树</span>
                </el-menu-item>
                <el-menu-item index="/treeAndProcess/requestProcess">
                    <el-icon>
                        <User />
                    </el-icon>
                    <span>申请处理</span>
                </el-menu-item>
                <el-sub-menu>
                    <template #title>
                        <el-icon>
                            <Setting />
                        </el-icon>
                        <span>个人设置</span>
                    </template>
                    <el-menu-item index="/user/setting">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatarUpload">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/user/password">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>修改密码</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>您好： <strong>{{ name }}</strong></div>
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="setting" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatarUpload" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="password" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>
            </el-main><!-- 底部区域 -->
            <el-footer>师承树 ©2024 Created by HIT</el-footer>
        </el-container>
    </el-container>
</template>
<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #aac;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.svg') no-repeat center / 120px auto;
        }

        .el-menu {
            background-color: #aac;
            border-right: none;

            .el-menu-item {
                background-color: #aac;
            }

            .el-sub-menu {
                background-color: #aac;
            }

        }
    }

    .el-header {
        background-color: #99f;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>