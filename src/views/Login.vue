<template>
  <div class="login-container">
    <div class="login-box">
      <h1 class="title">智慧社区管理平台</h1>
      <el-form 
        :model="loginForm" 
        :rules="rules" 
        ref="loginFormRef"
        class="login-form"
        @submit.prevent="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
            class="custom-input"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            class="custom-input"
          />
        </el-form-item>

        <el-form-item prop="role">
          <el-radio-group v-model="loginForm.role" class="role-group">
            <el-radio-button value='0'>超级管理员</el-radio-button>
            <el-radio-button value='1'>普通用户</el-radio-button>
            <el-radio-button value='2'>物业管理员</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-button
          type="primary"
          size="large"
          class="login-btn"
          native-type="submit"
          :loading="loading"
        >
          立即登录
        </el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import api from '@/config/axios.js'
import router from '@/router/index'

const loginFormRef = ref()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: '',
  role: '1'
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

const handleLogin = async () => {
  loginFormRef.value.validate(valid => {
    if (valid) {
      loading.value = true

      api.post(`user/login?username=${loginForm.username}&password=${loginForm.password}&role=${loginForm.role}`)
        .then(response => {
          loading.value = false
          if (response.data.code === 0) {
            localStorage.setItem('token', response.data.data)
            console.log('设置token：'+ response.data.data)
            ElMessage.success('登录成功')
            router.push('/')
          } else {
            ElMessage.error(response.data.message || '登录失败')
          }
        })
        .catch(error => {
          loading.value = false
          ElMessage.error('请求出错，请稍后重试')
          console.error('Login error:', error)
        })
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a237e 0%, #4a148c 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    width: 400px;
    height: 400px;
    background: linear-gradient(45deg, #7c4dff, #00bcd4);
    border-radius: 50%;
    top: -100px;
    right: -100px;
    opacity: 0.2;
  }

  &::after {
    content: '';
    position: absolute;
    width: 350px;
    height: 350px;
    background: linear-gradient(45deg, #ff4081, #f50057);
    border-radius: 50%;
    bottom: -100px;
    left: -100px;
    opacity: 0.2;
  }
}

.login-box {
  width: 480px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
  position: relative;
  z-index: 1;
}

.title {
  text-align: center;
  color: #303133;
  font-size: 28px;
  margin-bottom: 40px;
  letter-spacing: 2px;
}

.login-form {
  .role-group {
    width: 100%;
    display: flex;
    justify-content: space-between;

    :deep(.el-radio-button) {
      flex: 1;

      .el-radio-button__inner {
        width: 100%;
        transition: all 0.3s ease;
      }
    }
  }

  .login-btn {
    width: 100%;
    margin-top: 20px;
    font-size: 16px;
    letter-spacing: 2px;
    background: linear-gradient(45deg, #409eff, #36b5ff);
    border: none;
    height: 48px;

    &:hover {
      opacity: 0.9;
      transform: translateY(-2px);
    }
  }
}

.custom-input {
  :deep(.el-input__wrapper) {
    border-radius: 8px;
    padding: 0 15px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    
    &.is-focus {
      box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
    }
  }

  :deep(.el-input__inner) {
    height: 48px;
  }
}
</style>