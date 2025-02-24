<template>
    <div class="user-center-container" v-loading.fullscreen.lock="loading">
        <!-- 顶部用户信息栏 -->
        <div class="user-header">
            <el-avatar :size="80" :src="userInfo.photo" class="user-avatar" />
            <div class="user-info">
                <h2>{{ userInfo.nick_name }}</h2>
                <p class="meta-info">
                    <span>ID: {{ userInfo.username }}</span>
                    <el-tag type="success" size="small">普通用户</el-tag>
                </p>
            </div>
        </div>

        <!-- 主功能导航 -->
        <el-row :gutter="20" class="function-cards">
            <el-col :xs="24" :sm="12" :md="6">
                <!-- 预定公寓 -->
                <el-card class="card-item" @click="goToHome" v-if="showcard">
                    <div class="card-content">
                        <el-icon :size="40" color="#409EFF">
                            <House />
                        </el-icon>
                        <h3>公寓预订</h3>
                        <p>查看可租公寓并在线预订</p>
                    </div>
                </el-card>

                <!-- 查看合同 -->
                <el-card class="card-item" @click="goToBooking" v-else>
                    <div class="card-content">
                        <el-icon :size="40" color="#409EFF">
                            <Memo />
                        </el-icon>
                        <h3>查看租约</h3>
                        <p>查看租约以及合同内容</p>
                    </div>
                </el-card>
            </el-col>

            <!-- 在线缴费 -->
            <el-col :xs="24" :sm="12" :md="6" >
                <el-card class="card-item" @click="goToPayment" >
                    <div class="card-content">
                        <el-icon :size="40" color="#67C23A">
                            <Money />
                        </el-icon>
                        <h3>在线缴费</h3>
                        <p>缴纳水电物业费用</p>
                    </div>
                </el-card>
            </el-col>

            <!-- 社区公告 -->
            <el-col :xs="24" :sm="12" :md="6">
                <el-card class="card-item" @click="goToNotice">
                    <div class="card-content">
                        <el-icon :size="40" color="#E6A23C">
                            <Bell />
                        </el-icon>
                        <h3>社区公告</h3>
                        <p>查看最新社区通知</p>
                    </div>
                </el-card>
            </el-col>

            <!-- 在线报修 -->
            <el-col :xs="24" :sm="12" :md="6">
                <el-card class="card-item" @click="goToRepair">
                    <div class="card-content">
                        <el-icon :size="40" color="#F56C6C">
                            <Tools />
                        </el-icon>
                        <h3>在线报修</h3>
                        <p>提交维修申请</p>
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 最新动态区域 -->
        <el-row :gutter="20">
            <!-- 我的账单 -->
            <el-col :xs="24" :md="16">
                <el-card class="latest-section">
                    <template #header>
                        <div class="section-header">
                            <el-icon>
                                <Document />
                            </el-icon>
                            <span>最近账单</span>
                        </div>
                    </template>
                    <el-table :data="recentBills" style="width: 100%">
                        <el-table-column prop="date" label="日期" width="120" />
                        <el-table-column prop="type" label="类型" width="120">
                            <template #default="{ row }">
                                <el-tag :type="row.type === '水费' ? '' : 'warning'">
                                    {{ row.type }}
                                </el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="amount" label="金额" />
                        <el-table-column prop="status" label="状态">
                            <template #default="{ row }">
                                <el-text :type="row.status === '已支付' ? 'success' : 'danger'">
                                    {{ row.status }}
                                </el-text>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>

            <!-- 报修进度 -->
            <el-col :xs="24" :md="8">
                <el-card class="latest-section">
                    <template #header>
                        <div class="section-header">
                            <el-icon>
                                <Clock />
                            </el-icon>
                            <span>报修进度</span>
                        </div>
                    </template>
                    <el-timeline>
                        <el-timeline-item v-for="(item, index) in repairProgress" :key="index" :timestamp="item.time"
                            placement="top">
                            <el-card shadow="hover">
                                <h4>{{ item.title }}</h4>
                                <el-tag :type="statusTypeMap[item.status]">
                                    {{ item.status }}
                                </el-tag>
                                <p>{{ item.desc }}</p>
                            </el-card>
                        </el-timeline-item>
                    </el-timeline>
                </el-card>
            </el-col>
        </el-row>
    </div>
    <!-- 抽屉 -->
    <el-drawer
      title="缴费"
      size="400px"
      v-model="drawerVisible"
      :before-close="handleClose"
    >
      <el-form :model="paymentForm" ref="paymentFormRef">
        <!-- 缴费类型 -->
        <el-form-item label="缴费类型" prop="paymentType">
          <el-select v-model="paymentForm.paymentType" placeholder="请选择缴费类型">
            <el-option label="水费" value="water"></el-option>
            <el-option label="电费" value="electricity"></el-option>
            <el-option label="物业费" value="property"></el-option>
          </el-select>
        </el-form-item>

        <!-- 缴费金额 -->
        <el-form-item label="缴费金额" prop="amount">
          <el-input v-model="paymentForm.amount" placeholder="请输入金额" />
        </el-form-item>

        <!-- 确认和取消按钮 -->
        <div class="drawer-footer">
          <el-button @click="closeDrawer">取消</el-button>
          <el-button type="primary" @click="handlePayment">确认缴费</el-button>
        </div>
      </el-form>
    </el-drawer>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElLoading, ElMessageBox } from 'element-plus'
import router from '@/router/index'
import api from '@/config/axios'
import { House, Money, Bell, Tools, Document, Clock, Memo} from '@element-plus/icons-vue'

const token = ref('')
const userInfo = ref({})
const paymentForm = ref({})

const showcard = ref(true)
const loading = ref(true) 
const drawerVisible = ref(false)

const recentBills = ref([
    { date: '2024-03-15', type: '水费', amount: '¥156.00', status: '已支付' },
    { date: '2024-03-18', type: '电费', amount: '¥89.50', status: '待支付' },
    { date: '2024-03-20', type: '物业费', amount: '¥320.00', status: '待支付' }
])

const repairProgress = ref([
    {
        time: '2024-03-10 14:00',
        title: '卫生间漏水',
        status: '处理中',
        desc: '已分配维修人员，预计3个工作日内处理'
    },
    {
        time: '2024-03-05 09:30',
        title: '空调故障',
        status: '已完成',
        desc: '已更换压缩机，完成维修'
    }
])

const statusTypeMap = {
    '待处理': 'danger',
    '处理中': 'warning',
    '已完成': 'success'
}

const goToHome = () => router.push('/')
const goToNotice = () => router.push('/notice')
const goToRepair = () => router.push('/repair')
const goToPayment = () => {
    if(!showcard){
        ElMessage.warning('您还未租房！')
        return;
    }
    drawerVisible.value = true
    console.log('1')
}
const closeDrawer = () => {
  drawerVisible.value = false
  paymentForm.value = {}
}
const handleClose = () => {
  ElMessageBox.confirm('确定关闭缴费吗?')
    .then(() => {
        closeDrawer();
    })
}


onMounted(async () => {
    try {
        token.value = localStorage.getItem('token');
        if(token.value === null){
            router.push('/')
            ElMessage.error('您还未登录！');
        }
        const response1 = await api.post('user/getUserInfo');
        if (response1.data.code !== 0) {
            router.push('/login');
            ElMessage.error('token过期请重新登录！');
        }

        userInfo.value = response1.data.data;

        const response2 = await api.post('leaseContract/getActiveLeaseContractByUserId', {
            user_id: userInfo.value.id
        });

        if (response2.data.data != null) {
            showcard.value = false;
            console.log('用户已有租约');
        }

    } catch (error) {
        console.error('请求出错:', error);
    } finally {
        loading.value = false
    }
})
</script>

<style lang="scss" scoped>
.user-center-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 20px;
    opacity: 0.9;

    .user-header {
        display: flex;
        align-items: center;
        background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        padding: 30px;
        border-radius: 15px;
        margin-bottom: 30px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);

        .user-avatar {
            margin-right: 25px;
        }

        .user-info {
            h2 {
                margin: 0;
                color: #2c3e50;
                font-size: 28px;
            }

            .meta-info {
                margin-top: 10px;
                color: #666;
                display: flex;
                align-items: center;
                gap: 15px;
            }
        }
    }

    .function-cards {
        margin-bottom: 30px;

        .card-item {
            cursor: pointer;
            transition: transform 0.3s, box-shadow 0.3s;
            margin-bottom: 20px;
            border-radius: 12px;

            &:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
            }

            .card-content {
                text-align: center;
                padding: 20px;

                h3 {
                    margin: 15px 0 10px;
                    color: #303133;
                }

                p {
                    color: #909399;
                    font-size: 0.9em;
                    margin: 0;
                }
            }
        }
    }

    .latest-section {
        border-radius: 12px;
        margin-bottom: 20px;

        .section-header {
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 18px;
            color: #303133;
        }

        :deep(.el-timeline) {
            padding-left: 10px;
        }
    }
}

@media (max-width: 768px) {
    .user-header {
        flex-direction: column;
        text-align: center;

        .user-avatar {
            margin-right: 0 !important;
            margin-bottom: 15px;
        }
    }
}
</style>