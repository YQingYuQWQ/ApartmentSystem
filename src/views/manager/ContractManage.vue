<template>
  <div class="contract-management">
    <!-- 操作工具栏 -->
    <el-card shadow="never" class="operation-bar">
      <div class="toolbar">
        <div class="search-area">
          <el-input v-model="searchKey" placeholder="搜索合同编号/租户/房屋" clearable @input="handleSearch" class="search-input">
            <template #prefix>
              <el-icon>
                <Search />
              </el-icon>
            </template>
          </el-input>
        </div>
      </div>
    </el-card>

    <!-- 数据展示区 -->
    <el-card shadow="never" class="data-card">
      <el-table v-loading="loading" :data="contracts" style="width: 100%" :header-cell-style="headerStyle"
        @row-click="handleRowClick">
        <el-table-column prop="id" label="合同编号" min-width="180" />

        <el-table-column label="房屋id" min-width="200">
          <template #default="{ row }">
            <div class="house-info">
              <el-icon>
                <House />
              </el-icon>
              <span>{{ row.house_id }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="租户id" min-width="200">
          <template #default="{ row }">
            <div class="tenant-info">
              <el-avatar :size="32" :src="row.tenant_avatar" />
              <span>{{ row.user_id }}</span>
            </div>
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
            <el-button link type="primary" @click.stop="handleEdit(row)">
              编辑
            </el-button>
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

// 获取合同数据
const fetchContracts = async () => {
  try {
    loading.value = true
    const res = await api.post('leaseContract/getAllLeaseContract')
    if (res.data.code != 0) {
      ElMessage.error('获取合同数据失败' + res.data.message)
      return
    }
    console.log(res.data.data)
    contracts.value = res.data.data
    total.value = res.data.data.length
  } catch (error) {
    ElMessage.error('获取合同数据失败')
  } finally {
    loading.value = false
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

// 搜索处理
const handleSearch = () => {
  queryParams.search = searchKey.value
  queryParams.page = 1
  fetchContracts()
}

// 初始化加载
onMounted(() => {
  fetchContracts()
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