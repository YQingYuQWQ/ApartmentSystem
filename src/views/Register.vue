<template>
  <div class="register-container">
    <div class="register-box">
      <h1 class="title">加入智慧社区</h1>
      <div class="sub-title">开启您的智能生活体验</div>

      <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" label-position="top"
        @submit.prevent="handleRegister">
        <!-- 两列布局 -->
        <div class="form-columns">
          <div class="column">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="registerForm.username" placeholder="请输入4-16位字母数字组合" prefix-icon="User" clearable />
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" type="password" placeholder="至少6位，包含字母和数字" show-password
                prefix-icon="Lock" />
            </el-form-item>

            <el-form-item label="确认密码" prop="checkPassword">
              <el-input v-model="registerForm.checkPassword" type="password" placeholder="请再次输入密码" show-password
                prefix-icon="Lock" />
            </el-form-item>
          </div>

          <div class="column">
            <el-form-item label="昵称" prop="nick_name">
              <el-input v-model="registerForm.nickname" placeholder="如何称呼您？" prefix-icon="Avatar" clearable />
            </el-form-item>

            <el-form-item label="邮箱" prop="email">
              <el-input v-model="registerForm.email" placeholder="请输入常用邮箱" prefix-icon="Message" clearable />
            </el-form-item>

            <el-form-item label="手机号" prop="phone">
              <el-input v-model="registerForm.phone" placeholder="11位手机号码" prefix-icon="Iphone" clearable />
            </el-form-item>
          </div>
        </div>

        <!-- 固定角色 -->
        <el-form-item label="用户角色" v-show="false">
          <el-input :value="roleMap[registerForm.role]" disabled prefix-icon="UserFilled" />
        </el-form-item>

        <el-button type="primary" class="register-btn" native-type="submit" :loading="loading">
          立即注册
        </el-button>

        <div class="login-link">
          已有账号？<el-link type="primary" @click="goToLogin">立即登录</el-link><p></p>
          <el-link type="primary" @click="goToHome">返回首页</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import router from '@/router/index'
import api from '@/config/axios'

const registerFormRef = ref()
const loading = ref(false)

// 表单数据
const registerForm = reactive({
  username: '',
  password: '',
  checkPassword: '',
  email: '',
  phone: '',
  nick_name: '',
  role: 1 // 固定为普通用户
})

// 角色映射
const roleMap = {
  1: '普通用户'
}

// 验证规则
const validateUsername = (rule, value, callback) => {
  if (!/^[a-zA-Z0-9]{4,16}$/.test(value)) {
    callback(new Error('用户名需为4-16位字母数字组合'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (!/(?=.*[a-zA-Z])(?=.*\d).{6,}/.test(value)) {
    callback(new Error('密码需至少6位且包含字母和数字'))
  } else {
    if (registerForm.checkPassword !== '') {
      registerFormRef.value.validateField('checkPassword')
    }
    callback()
  }
}

const validateCheckPassword = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const registerRules = reactive({
  username: [
    { required: true, trigger: 'blur', validator: validateUsername }
  ],
  password: [
    { required: true, trigger: 'blur', validator: validatePassword }
  ],
  checkPassword: [
    { required: true, trigger: 'blur', validator: validateCheckPassword }
  ],
  email: [
    { required: true, trigger: 'blur', message: '请输入邮箱地址' },
    { type: 'email', message: '请输入正确的邮箱格式' }
  ],
  phone: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 12, message: '昵称长度2-12个字符', trigger: 'blur' }
  ]
})

const handleRegister = () => {
  registerFormRef.value.validate(valid => {
    if (valid) {
      loading.value = true
      api.post('user/register', {
        username: registerForm.username,
        password: registerForm.password,
        email: registerForm.email,
        phone: registerForm.phone,
        nick_name: registerForm.nick_name,
        role: registerForm.role
      })
        .then(response => {
          loading.value = false
          if (response.data.code === 0) {
            ElMessage.success('注册成功')
            router.push('/login')
          } else {
            ElMessage.error(response.data.message || '注册失败')
          }
        })
        .catch(error => {
          loading.value = false
          ElMessage.error('请求失败，请稍后再试')
          console.error(error)
        })
    }
  })
}

const goToLogin = () => {
  router.push('/login')
}

const goToHome = () => {
  router.push('/')
}
</script>

<style lang="scss" scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
  opacity: 0.9;
  
  .register-box {
    background: rgba(255, 255, 255, 0.96);
    padding: 40px 50px;
    border-radius: 20px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 800px;

    .title {
      text-align: center;
      color: #2c3e50;
      font-size: 28px;
      margin-bottom: 8px;
    }

    .sub-title {
      text-align: center;
      color: #666;
      margin-bottom: 40px;
      font-size: 16px;
    }

    .form-columns {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 30px;

      @media (max-width: 768px) {
        grid-template-columns: 1fr;
      }
    }

    :deep(.el-form-item__label) {
      font-weight: 500;
      color: #444;
      padding-bottom: 8px;
    }

    .el-input {
      :deep(.el-input__wrapper) {
        border-radius: 8px;
        padding: 0 15px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
        transition: all 0.3s;

        &.is-focus {
          box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
        }
      }
    }

    .register-btn {
      width: 100%;
      margin-top: 20px;
      height: 48px;
      font-size: 16px;
      letter-spacing: 2px;
      background: linear-gradient(45deg, #667eea, #764ba2);
      border: none;
      transition: all 0.3s;

      &:hover {
        opacity: 0.9;
        transform: translateY(-2px);
      }
    }

    .login-link {
      text-align: center;
      margin-top: 20px;
      color: #666;

      .el-link {
        vertical-align: baseline;
      }
    }
  }
}
</style>