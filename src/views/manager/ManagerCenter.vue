<template>
    <div class="admin-container">
      <!-- 侧边导航 -->
      <el-aside width="240px" class="admin-side">
        <div class="logo-area">
          <img src="@/assets/logo.png" class="logo">
          <h2>物业管理系统</h2>
        </div>
        <el-menu
          default-active="1"
          router
          class="admin-menu"
          teleported
        >
          <el-menu-item index="/managercenter/dashboard">
            <el-icon><DataLine /></el-icon>
            <span>数据看板</span>
          </el-menu-item>
          <el-menu-item index="/managercenter/feemanage">
            <el-icon><Tickets /></el-icon>
            <span>订单管理</span>
          </el-menu-item>
          <el-menu-item index="/managercenter/housemanage">
            <el-icon><OfficeBuilding /></el-icon>
            <span>房屋管理</span>
          </el-menu-item>
          <el-menu-item index="/managercenter/contractmanage">
            <el-icon><Document /></el-icon>
            <span>合同管理</span>
          </el-menu-item>
          <el-menu-item index="/managercenter/announcementmanage">
            <el-icon><Bell /></el-icon>
            <span>公告管理</span>
          </el-menu-item>
          <el-menu-item index="/managercenter/repairmanage">
            <el-icon><Tools /></el-icon>
            <span>报修管理</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
  
      <!-- 主内容区 -->
      <div class="admin-main">
        <!-- 顶部操作栏 -->
        <header class="admin-header">
          <div class="header-left">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/managercenter' }">物业管理</el-breadcrumb-item>
              <el-breadcrumb-item>{{ currentRouteName }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="header-right">
            <el-dropdown>
              <span class="user-info">
                <el-avatar :size="32" :src="user.avatar" />
                <span class="user-name">{{ user.name }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人设置</el-dropdown-item>
                  <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </header>
  
        <router-view class="content-area" />
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import router from '@/router/index'
  import { OfficeBuilding, DataLine, Bell, Tools, Document, Tickets } from '@element-plus/icons-vue'
  import { ElMessage } from 'element-plus'
  import api from '@/config/axios'
  
  const route = useRoute()
  const userinfo = ref()

  const user = ref({})
  
  const currentRouteName = computed(() => {
    return route.name
  })
  
  const logout = () => {
    router.push('/login')
  }

  onMounted(async () => {
    const userinfos = await api.post('user/getUserInfo')
    if(userinfos.data.code != '0'){
      ElMessage.error(userinfo.data.message)
      return;
    }
    userinfo.value = userinfos.data.data
    if(userinfo.value.role === 1){
      ElMessage.warning('角色身份不匹配,正在重定向至用户中心')
      router.push('/usercenter')
      return;
    }
    user.value.name = userinfo.value.nick_name;
    user.value.avatar = userinfo.value.photo;
})
  </script>
  
  <style lang="scss" scoped>
  .admin-container {
    display: flex;
    min-height: 100vh;
    background: #f0f2f5;
    overflow: hidden;
  
    .admin-side {
      background: #001529;
      transition: width 0.3s;
      transition: none !important; 
  
      .logo-area {
        display: flex;
        align-items: center;
        height: 64px;
        padding: 0 20px;
        background: #002140;
  
        .logo {
          width: 36px;
          height: 36px;
          margin-right: 12px;
        }
  
        h2 {
          color: #fff;
          font-size: 18px;
          margin: 0;
        }
      }
  
      .admin-menu {
        border-right: none;
        background: transparent;
  
        :deep(.el-menu-item) {
          color: rgba(255,255,255,0.65);
          height: 48px;
          line-height: 48px;
  
          &.is-active {
            background-color: #1890ff !important;
            color: #fff;
          }
  
          &:hover {
            background-color: rgba(255,255,255,0.1);
          }
        }
      }
    }
  
    .admin-main {
      flex: 1;
      min-width: 0;
  
      .admin-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 64px;
        padding: 0 24px;
        background: #fff;
        box-shadow: 0 1px 4px rgba(0,21,41,.08);
  
        .user-info {
          display: flex;
          align-items: center;
          cursor: pointer;
  
          .user-name {
            margin-left: 12px;
            font-size: 14px;
          }
        }
      }
  
      .content-area {
        padding: 24px;
        min-height: calc(100vh - 64px);
      }
    }
  }
  </style>