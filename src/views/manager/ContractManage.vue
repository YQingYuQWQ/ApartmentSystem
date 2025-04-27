<template>
  <div class="contract-management">
    <!-- 数据展示区 -->
    <el-card shadow="never" class="data-card">
      <el-table v-loading="loading" :data="contracts" style="width: 100%" :header-cell-style="headerStyle"
        @row-click="handleRowClick">
        <el-table-column prop="id" label="合同编号" min-width="180" />

        <el-table-column label="房屋id" min-width="200">
          <template #default="{ row }">
            <el-popover placement="top-start" trigger="hover" :width="300">
              <template #reference>
                <div class="house-info">
                  <el-icon>
                    <House />
                  </el-icon>
                  <span>{{ row.house_id }}</span>
                </div>
              </template>

              <!-- 卡片内容 -->
              <div class="house-card">
                <div class="card-title">
                  <el-icon>
                    <House />
                  </el-icon>
                  <span>房屋详情</span>
                </div>
                <el-divider />
                <div>房屋ID: {{ row.house_id }}</div>
                <div>房间号: {{ row.house_number }}</div>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column label="租户id" min-width="200">
          <template #default="{ row }">
            <el-popover placement="top-start" trigger="hover" :width="300">
              <template #reference>
                <div class="tenant-info">
                  <span>{{ row.user_id }}</span>
                </div>
              </template>
              <div class="user-card">
                <div>用户ID: {{ row.user_id }}</div>
                <div>用户名: {{ row.username || '无' }}</div>
                <div>电话: {{ row.phone || '无' }}</div>
                <div>邮箱: {{ row.email || '无' }}</div>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column label="租期" min-width="180">
          <template #default="{ row }">
            <div class="duration">
              <div>{{ formatDate(row.start_date) }}</div>
              <div class="separator">至</div>
              <div>{{ formatDate(row.end_date) }}</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="租金信息" min-width="180">
          <template #default="{ row }">
            <div class="rent-info">
              <div>月租：¥{{ row.monthly_rent }}</div>
              <div>押金：¥{{ row.deposit }}</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="状态" min-width="120">
          <template #default="{ row }">
            <status-badge :type="statusType[row.contract_status]">
              {{ statusText[row.contract_status] }}
            </status-badge>
          </template>
        </el-table-column>

        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <el-button link type="danger" @click.stop="handleTerminate(row)" v-if="row.contract_status === 'active'">
              终止
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination class="smart-pagination" :current-page="queryParams.page" :page-size="queryParams.limit"
        :total="total" :layout="paginationLayout" @current-change="handlePageChange" />
    </el-card>

    <!-- 合同编辑对话框 -->
    <contract-dialog v-model="dialogVisible" :data="currentContract" :mode="dialogMode" @submit="handleSubmit" />
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Search, House } from '@element-plus/icons-vue'
import api from '@/config/axios'
import dayjs from 'dayjs'

// 状态映射
const statusType = {
  active: 'success',
  expired: 'warning',
  terminated: 'danger'
}

const statusText = {
  active: '生效中',
  expired: '已到期',
  terminated: '已终止'
}

// 查询参数
const queryParams = reactive({
  page: 1,
  limit: 10,
  search: ''
})

const contracts = ref([])
const total = ref(0)
const loading = ref(false)
const searchKey = ref('')
const dialogVisible = ref(false)
const dialogMode = ref('create')
const currentContract = ref(null)
const houseIds = ref([])
const userIds = ref([])
const houseInfo = ref()
const userInfo = ref()

// 获取合同数据
const fetchContracts = async () => {
  try {
    loading.value = true
    const res = await api.post('leaseContract/getAllLeaseContract')
    if (res.data.code != 0) {
      ElMessage.error('获取合同数据失败: ' + res.data.message)
      return
    }

    const data = res.data.data
    contracts.value = data
    total.value = data.length

    houseIds.value = [...new Set(data.map(item => item.house_id))]
    userIds.value = [...new Set(data.map(item => item.user_id))]

  } catch (error) {
    ElMessage.error('获取合同数据失败')
  } finally {
    loading.value = false
  }
}

//获取房屋数据
const fetchHouseInfo = async () => {
  try {
    const res = await api.post('house/getByIds', houseIds.value)
    if (res.data.code !== 0) {
      ElMessage.error('获取房屋信息失败: ' + res.data.message)
      return
    }
    houseInfo.value = res.data.data
  } catch (error) {
    ElMessage.error('获取房屋信息失败')
  }
}

//获取用户数据
const fetchUserInfo = async () => {
      if (userIds.value.length === 0) {
      userInfo.value = []
      return
    }
  try {
    const res = await api.post('user/getUserByIds' , userIds.value)
    if (res.data.code !== 0) {
      ElMessage.error('获取用户信息失败: ' + res.data.message)
      return
    }
    userInfo.value = res.data.data
  } catch (error) {
    ElMessage.error('获取房屋信息失败')
  }
}

// 日期格式化
const formatDate = (value) => {
  return dayjs(value).format('YYYY-MM-DD')
}

// 分页处理
const handlePageChange = (val) => {
  queryParams.page = val
  fetchContracts()
}

// 终止合同
const handleTerminate = async (row) => {
  try {
    const res = await api.post('leaseContract/terminateLeaseContract', {
      id: row.id,
      house_id: row.house_id,
      house_number: row.house_number,
      user_id: row.user_id
    })
    if (res.data.code !== 0) {
      ElMessage.error('获取房屋信息失败: ' + res.data.message)
      return
    }
    houseInfo.value = res.data.data
  } catch (error) {
    ElMessage.error('获取房屋信息失败222')
  }
}

const userMap = computed(() => {
  const map = {}
  userInfo.value.forEach(user => {
    map[user.id] = user
  })
  return map
})

const houseMap = computed(() => {
  const map = {}
  houseInfo.value.forEach(house => {
    map[house.id] = house
  })
  return map
})

const mergeContractData = () => {
  contracts.value = contracts.value.map(contract => {
    const user = userMap.value[contract.user_id] || {}
    const house = houseMap.value[contract.house_id] || {}
    return {
      ...contract,
      username: user.username,
      phone: user.phone,
      email: user.email,
      house_number: house.house_number,
    }
  })
}

// 初始化加载
onMounted( async () => {
  loading.value = true
  await fetchContracts()
  await fetchHouseInfo()
  await fetchUserInfo()
  mergeContractData()
  loading.value = false
})
</script>

<style lang="scss" scoped>
.contract-management {
  padding: 20px;
  background: #f8fafc;

  .operation-bar {
    margin-bottom: 16px;
    border-radius: 12px;

    .toolbar {
      display: flex;
      align-items: center;
      gap: 16px;

      .search-area {
        flex: 1;
        max-width: 400px;
        margin-left: auto;
      }
    }
  }


  .data-card {
    border-radius: 12px;

    :deep(.el-table) {
      --el-table-border-color: transparent;

      th {
        font-weight: 600;
      }

      .house-info {
        display: flex;
        align-items: center;
        gap: 8px;
      }

      .tenant-info {
        display: flex;
        align-items: center;
        gap: 12px;

        .el-avatar {
          flex-shrink: 0;
        }
      }

      .duration {
        display: flex;
        flex-direction: column;
        gap: 4px;

        .separator {
          color: #999;
          font-size: 12px;
          text-align: center;
        }
      }

      .rent-info {
        display: flex;
        flex-direction: column;
        gap: 4px;
      }

      .user-card {
        padding: 10px;
        line-height: 1.8;
      }

      .user-card div {
        margin-bottom: 4px;
      }

      .tenant-info {
        cursor: pointer;
        color: var(--el-color-primary);
      }

      .house-card {
        padding: 10px;
        line-height: 1.8;
      }

      .house-card div {
        margin-bottom: 6px;
      }

      .card-title {
        display: flex;
        align-items: center;
        gap: 8px;
        font-weight: bold;
      }

      .house-info {
        display: flex;
        align-items: center;
        gap: 6px;
        cursor: pointer;
      }

      .house-info:hover {
        color: var(--el-color-primary);
      }
    }
  }

  .smart-pagination {
    padding: 20px 0;
    justify-content: flex-end;
  }
}

@media (max-width: 768px) {
  .contract-management {
    padding: 12px;

    .toolbar {
      flex-direction: column;

      .search-area {
        width: 100%;
        max-width: none;
      }
    }

    .data-card {
      :deep(.el-table) {
        td {
          padding: 12px 8px;
        }

        .cell {
          font-size: 13px;
        }
      }
    }
  }
}
</style>