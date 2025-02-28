<template>
    <div class="fee-management">
        <!-- 操作工具栏 -->
        <div class="operation-bar">
            <el-card shadow="never" class="filter-card">
                <div class="filter-wrapper">
                    <el-form :model="queryParams" class="responsive-form">
                        <el-row :gutter="16">
                            <el-col :xs="24" :sm="12" :md="8" :lg="6">
                                <el-form-item label="费用类型">
                                    <el-select v-model="queryParams.type" placeholder="全部类型" clearable
                                        @change="handleFilter" class="full-width-select">
                                        <el-option v-for="item in typeOptions" :key="item.value" :label="item.label"
                                            :value="item.value" />
                                    </el-select>
                                </el-form-item>
                            </el-col>

                            <el-col :xs="24" :sm="12" :md="8" :lg="6">
                                <el-form-item label="支付状态">
                                    <el-select v-model="queryParams.paid" placeholder="全部状态" clearable
                                        @change="handleFilter" class="full-width-select">
                                        <el-option label="已支付" :value="1" />
                                        <el-option label="未支付" :value="0" />
                                    </el-select>
                                </el-form-item>
                            </el-col>

                            <el-col :xs="24" :sm="12" :md="8" :lg="6">
                                <el-form-item label="结算状态">
                                    <el-select v-model="queryParams.status" placeholder="全部状态" clearable
                                        @change="handleFilter" class="full-width-select">
                                        <el-option label="已结算" :value="1" />
                                        <el-option label="未结算" :value="0" />
                                    </el-select>
                                </el-form-item>
                            </el-col>

                            <el-col :xs="24" :sm="24" :md="24" :lg="6">
                                <div class="action-buttons">
                                    <el-button type="primary" @click="handleFilter" class="query-button">
                                        <el-icon>
                                            <Search />
                                        </el-icon>
                                        查询
                                    </el-button>
                                    <el-button @click="resetFilter">
                                        <el-icon>
                                            <Refresh />
                                        </el-icon>
                                        重置
                                    </el-button>
                                </div>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            </el-card>
        </div>

        <!-- 数据展示区 -->
        <el-card shadow="never" class="data-card">
            <el-table v-loading="listLoading" :data="list" style="width: 100%" :header-cell-style="headerStyle"
                :row-class-name="tableRowClassName" @row-click="handleRowClick">
                <!-- 表格列 -->
                <el-table-column label="订单编号" prop="fee_number" min-width="180" show-overflow-tooltip />

                <el-table-column label="费用类型" prop="type" min-width="120">
                    <template #default="{ row }">
                        <el-tag :type="typeTagMap[row.type]" effect="light" class="type-tag">
                            {{ typeLabels[row.type] }}
                        </el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="金额" prop="amount" align="right" min-width="120">
                    <template #default="{ row }">
                        <span class="amount">¥{{ row.amount }}</span>
                    </template>
                </el-table-column>

                <el-table-column label="支付状态" prop="paid" min-width="120">
                    <template #default="{ row }">
                        <status-badge :type="row.paid ? 'success' : 'error'">
                            {{ row.paid ? '已支付' : '未支付' }}
                        </status-badge>
                    </template>
                </el-table-column>

                <el-table-column label="结算状态" prop="status" min-width="120">
                    <template #default="{ row }">
                        <status-badge :type="row.status ? 'success' : 'warning'">
                            {{ row.status ? '已结算' : '未结算' }}
                        </status-badge>
                    </template>
                </el-table-column>

                <el-table-column label="截止日期" prop="due_date" min-width="180">
                    <template #default="{ row }">
                        {{ formatDateTime(row.due_date) }}
                    </template>
                </el-table-column>

                <el-table-column label="操作" fixed="right" width="120">
                    <template #default="{ row }">
                        <el-button link type="primary" @click.stop="handleDetail(row)">
                            详情
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <el-pagination class="smart-pagination" :current-page="queryParams.page" :page-size="queryParams.limit"
                :total="total" :layout="paginationLayout" @current-change="handlePageChange" />
        </el-card>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import api from '@/config/axios'
import dayjs from 'dayjs'

const list = ref([])
const total = ref(0)
const listLoading = ref(false)

const queryParams = reactive({
    page: 1,
    limit: 10,
    type: null,
    paid: null,
    status: null
})

const typeOptions = [
    { value: 'rent', label: '租金' },
    { value: 'utilities', label: '公共设施' },
    { value: 'maintenance', label: '维护费用' },
    { value: 'water', label: '水费' },
    { value: 'power', label: '电费' },
    { value: 'deposit', label: '押金' }
]

const typeLabels = {
    rent: '租金',
    utilities: '公共设施',
    maintenance: '维护',
    water: '水费',
    power: '电费',
    deposit: '押金'
}

const typeTagMap = {
    rent: 'warning',
    utilities: '',
    maintenance: 'info',
    water: 'primary',
    power: 'success',
    deposit: 'danger'
}

const fetchData = async () => {
  listLoading.value = true
  try {
    const res = await api.get('/fee/list', {
        params: {
            page: queryParams.page,
            size: queryParams.limit,
            type: queryParams.type,
            paid: queryParams.paid,
            status: queryParams.status
        }
    })
    if (res.data.code === 0) {
      list.value = res.data.data.records
      total.value = res.data.data.total
    }
  } catch (error) {
    ElMessage.error('获取数据失败')
  } finally {
    listLoading.value = false
  }
}

const paginationLayout = computed(() => {
    return window.innerWidth < 768 ? 'prev, pager, next' : 'total, sizes, prev, pager, next, jumper'
})

// 表格样式
const headerStyle = ({ columnIndex }) => ({
    backgroundColor: columnIndex === 0 ? '#f8fafc' : '#ffffff',
    color: '#606266',
    fontWeight: 600
})

const tableRowClassName = ({ rowIndex }) => {
    return rowIndex % 2 === 1 ? 'stripe-row' : ''
}

// 处理筛选
const handleFilter = () => {
    queryParams.page = 1
    fetchData()
}

// 分页处理
const handlePageChange = (val) => {
    queryParams.page = val
    fetchData()
}

const formatDateTime = (value, format = 'YYYY-MM-DD HH:mm') => {
    return value ? dayjs(value).format(format) : '--'
}

// 初始化加载
onMounted(() => {
    fetchData()
})
</script>

<style lang="scss" scoped>
.fee-management {
    padding: 20px;
    background: #f8fafc;

    .operation-bar {
        margin-bottom: 16px;

        .filter-card {
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);

            .filter-wrapper {
                padding: 16px 24px;
            }
        }
    }

    .data-card {
        border-radius: 12px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);

        :deep(.el-table) {
            --el-table-border-color: transparent;
            --el-table-header-bg-color: #f8fafc;

            th {
                font-weight: 600;
            }

            .stripe-row {
                background-color: #fafafa;
            }

            tr:hover td {
                background-color: #f5f7fa !important;
            }

            .type-tag {
                font-weight: 500;
                padding: 4px 8px;
            }

            .amount {
                font-family: 'Roboto Mono', monospace;
                font-weight: 600;
                color: #2d3a4b;
            }
        }
    }

    .smart-pagination {
        padding: 20px 0;
        justify-content: flex-end;
    }
}

// 响应式适配
@media (max-width: 768px) {
    .fee-management {
        padding: 12px;

        .filter-card {
            .el-form-item {
                margin-bottom: 12px;
            }

            .action-buttons {
                display: flex;
                gap: 8px;

                .el-button {
                    flex: 1;
                }
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

.responsive-form {
    .full-width-select {
        width: 100%;
    }

    .query-button {
        width: 100%;
    }
}
</style>