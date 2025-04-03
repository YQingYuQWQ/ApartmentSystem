<template>
  <div class="admin-container" :class="{ 'dark-mode': isDark }">
    <!-- 头部 -->
    <header class="admin-header">
      <div class="header-content">
        <h1 class="system-title">用户管理系统</h1>
        <el-button type="primary" round @click="goToHome">
          返回主页
        </el-button>
      </div>
    </header>

    <main class="main-content">
      <div class="action-bar">
        <el-input v-model="searchKey" placeholder="搜索用户..." clearable class="search-input" @keyup.enter="handleSearch">
          <template #prefix>
            <el-icon>
              <Search />
            </el-icon>
          </template>
        </el-input>
        <el-button type="primary" @click="showCreateDialog">
          <el-icon>
            <Plus />
          </el-icon>
          新建用户
        </el-button>
      </div>

      <el-table :data="paginatedData" v-loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />

        <el-table-column label="用户信息" width="220">
          <template #default="{ row }">
            <div class="user-info">
              <el-avatar :size="40" :src="row.photo">
                {{ (row.nick_name || row.username).charAt(0).toUpperCase() }}
              </el-avatar>
              <div class="user-details">
                <div class="username">{{ row.username }}</div>
                <div class="nickname">{{ row.nick_name || '-' }}</div>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="角色" width="120">
          <template #default="{ row }">
            <el-tag :type="roleTagType[row.role]">
              {{ roleMap[row.role] }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="phone" label="手机号" width="130" />

        <el-table-column label="时间" width="180">
          <template #default="{ row }">
            <div class="time-info">
              <div>创建：{{ formatTime(row.created_at) }}</div>
              <div>更新：{{ formatTime(row.updated_at) }}</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="small" @click="editUser(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="deleteUser(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination v-model:current-page="currentPage" :page-size="pageSize" :total="filteredUsers.length"
          layout="total, prev, pager, next" />
      </div>
    </main>

    <el-dialog v-model="dialogVisible" :title="dialogType === 'create' ? '新建用户' : '编辑用户'" width="600px">
      <el-form :model="formData" label-width="80px" :rules="formRules" ref="formRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="3-20位字母数字" clearable />
        </el-form-item>

        <el-form-item label="密码" prop="password" v-if="dialogType === 'create'">
          <el-input v-model="formData.password" type="password" show-password placeholder="至少6位字符" clearable />
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword" v-if="dialogType === 'create'">
          <el-input v-model="formData.confirmPassword" type="password" show-password placeholder="再次输入密码" clearable />
        </el-form-item>

        <el-form-item label="角色" prop="role">
          <el-select v-model="formData.role" placeholder="选择角色" clearable>
            <el-option v-for="(label, key) in roleMap" :key="key" :label="label" :value="Number(key)" />
          </el-select>
        </el-form-item>

        <el-form-item label="昵称" prop="nick_name">
          <el-input v-model="formData.nick_name" placeholder="输入显示名称" clearable />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="example@domain.com" clearable />
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" placeholder="11位手机号码" clearable />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'
import api from '@/config/axios'
import { Plus } from '@element-plus/icons-vue'
import router from '@/router'

const users = ref([])
const loading = ref(true)
const searchKey = ref('')
const currentPage = ref(1)
const pageSize = 10

const goToHome = () => router.push('/')

const roleMap = {
  0: '超级管理员',
  1: '普通用户',
  2: '物业经理'
}

const roleTagType = {
  0: 'danger',
  1: 'success',
  2: 'warning'
}

const formRef = ref(null)
const formRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度3-20个字符', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '只能包含字母、数字和下划线', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '至少6位字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== formData.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: ['blur', 'change']
    }
  ],
  role: [
    { required: true, message: '请选择用户角色', trigger: 'change' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
  ],
  phone: [
    { required: true, pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  nick_name: [
    { required: true, }
  ]
}

const dialogVisible = ref(false)
const dialogType = ref('create')
const formData = ref(getInitialFormData())

function getInitialFormData() {
  return {
    username: '',
    password: '',
    confirmPassword: '',
    role: 1,
    nick_name: '',
    email: '',
    phone: ''
  }
}

const formatTime = (time) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
}

const showCreateDialog = () => {
  dialogType.value = 'create'
  formData.value = getInitialFormData()
  dialogVisible.value = true
}

const handleSearch = () => {

}

const editUser = (user) => {
  dialogType.value = 'edit'
  formData.value = { ...user }
  dialogVisible.value = true
}

const deleteUser = async (user) => {
  try {
    await ElMessageBox.confirm(`确定删除用户 ${user.username}？`, '警告', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning',
    })
    const res = await api.post('user/deleteUser', { id: user.id })
    if (res.data.code != 0) {
      ElMessage.error(res.data.message)
      return;
    }
    ElMessage.success('删除成功')
    window.location.reload();
  } catch (error) {
    ElMessage.info('操作已取消')
  }
}

const submitForm = async () => {
  try {
    await formRef.value.validate()
    if (dialogType.value === 'create') {
      const body = { ...formData.value }
      const res = await api.post('user/createUser', body)
      if (res.data.code != 0) {
        ElMessage.error(res.data.message)
        return;
      }
    } else {
      const body = { ...formData.value }
      const res = await api.post('user/updateUser', body)
      if (res.data.code != 0) {
        ElMessage.error(res.data.message)
        return;
      }
    }
    dialogVisible.value = false
    ElMessage.success('操作成功')
    window.location.reload();
  } catch (error) {
    console.log('表单验证失败', error)
  }
}

onMounted(async () => {
  const userinfo = await api.get('user/getAllUser')
  if (userinfo.data.code != '0') {
    ElMessage.error(userinfo.data.message)
    return;
  }
  users.value = userinfo.data.data
  loading.value = false
})

const filteredUsers = computed(() =>
  users.value.filter(user =>
    Object.values(user).some(value =>
      String(value).toLowerCase().includes(searchKey.value.toLowerCase())
    )
  )
)

const paginatedData = computed(() =>
  filteredUsers.value.slice(
    (currentPage.value - 1) * pageSize,
    currentPage.value * pageSize
  )
)
</script>

<style lang="scss" scoped>
.admin-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--el-bg-color-page);
  transition: background 0.3s ease;

}



.admin-header {
  padding: 0 24px;
  height: 64px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid var(--el-border-color);
  background: var(--el-bg-color);

  .header-content {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .system-title {
      font-size: 1.25rem;
      font-weight: 600;
      color: var(--el-text-color-primary);
    }
  }
}

.main-content {
  flex: 1;
  padding: 24px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.action-bar {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;

  .search-input {
    width: 300px;

    :deep(.el-input__inner) {
      border-radius: 8px;
    }
  }
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;

  .user-details {
    .username {
      font-weight: 500;
      margin-bottom: 2px;
    }

    .nickname {
      font-size: 12px;
      color: var(--el-text-color-secondary);
    }
  }
}

.time-info {
  font-size: 12px;
  line-height: 1.5;
  color: var(--el-text-color-secondary);

  div {
    white-space: nowrap;
  }
}

.pagination {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

.theme-switch {
  --el-switch-on-color: var(--el-color-primary);
  --el-switch-off-color: var(--el-border-color);
}
</style>