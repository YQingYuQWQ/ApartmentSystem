<template>
    <div class="user-center-container" v-loading.fullscreen.lock="loading">
        <!-- 顶部用户信息栏 -->
        <div class="user-header">
            <div class="avatar-container">
                <div class="avatar-wrapper" @mouseenter="showUpload = true" @mouseleave="showUpload = false">
                    <!-- 正常状态显示头像 -->
                    <el-avatar :size="80" :src="userInfo.photo" class="user-avatar"
                        :class="{ 'avatar-hover': showUpload }" />

                    <!-- 悬停时显示上传按钮 -->
                    <el-upload action="/api/upload-avatar" :show-file-list="false" :on-success="handleAvatarUpload"
                        :before-upload="beforeAvatarUpload" class="upload-wrapper">
                        <div class="upload-mask" v-show="showUpload">
                            <el-icon class="upload-icon" color="#ff0000">
                                <Plus />
                            </el-icon>
                        </div>
                    </el-upload>
                </div>
            </div>
            <div class="user-info">
                <h2>{{ userInfo.nick_name }}</h2>
                <p class="meta-info">
                    <span>ID: {{ userInfo.username }}</span>
                    <el-tag type="success" size="small">普通用户</el-tag>
                </p>
            </div>
            <div class="user-actions" style="margin-left: auto;">
                <el-button type="primary" plain @click="goToHome">返回主页</el-button>
                <el-button type="danger" plain @click="logOut">退出登录</el-button>
            </div>
        </div>

        <!-- 主功能导航 -->
        <el-row :gutter="20" class="function-cards">
            <el-col :xs="24" :sm="12" :md="6">
                <!-- 预定公寓 -->
                <el-card class="card-item" @click="goToHome" v-if="showcard === ''">
                    <div class="card-content">
                        <el-icon :size="40" color="#409EFF">
                            <House />
                        </el-icon>
                        <h3>公寓预订</h3>
                        <p>查看可租公寓并在线预订</p>
                    </div>
                </el-card>

                <el-card class="card-item" @click="goToHome" v-else-if="showcard === 'waiting'">
                    <div class="card-content">
                        <el-icon :size="40" color="#409EFF">
                            <House />
                        </el-icon>
                        <h3>待入住</h3>
                        <p>已预定，请前往公寓签署合同</p>
                    </div>
                </el-card>

                <el-card class="card-item" @click="goToContract" v-else-if="showcard === 'occupied'">
                    <div class="card-content">
                        <el-icon :size="40" color="#409EFF">
                            <Memo />
                        </el-icon>
                        <h3>查看明细</h3>
                        <p>查看房屋明细以及合同内容</p>
                    </div>
                </el-card>
            </el-col>

            <!-- 在线缴费 -->
            <el-col :xs="24" :sm="12" :md="6">
                <el-card class="card-item" @click="goToPayment">
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
                        <el-table-column prop="created_at" label="日期" width="240" />
                        <el-table-column prop="type" label="类型" width="120">
                            <template #default="{ row }">
                                <el-tag :type="getTypeTag(row.type)" size="small">
                                    {{ getTypeLabel(row.type) }}
                                </el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="amount" label="金额" />
                        <el-table-column prop="paid" label="状态">
                            <template #default="{ row }">
                                <el-text :type="row.paid === true ? 'success' : 'danger'">
                                    {{ row.paid === true ? '已支付' : '待支付' }}
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
                        <el-timeline-item v-for="(item, index) in repairProgress" :key="index" :timestamp="item.created_at"
                            placement="top">
                            <el-card shadow="hover">
                                <h4>{{ item.description }}</h4>
                                <el-tag :type="statusTypeMap[item.status]">
                                    {{ item.status }}
                                </el-tag>
                            </el-card>
                        </el-timeline-item>
                    </el-timeline>
                </el-card>
            </el-col>
        </el-row>
    </div>
    <!-- 缴费抽屉 -->
    <el-drawer title="缴费管理" size="400px" v-model="drawerVisible" :before-close="handleClose" class="payment-drawer">
        <el-form :model="paymentForm" ref="paymentFormRef" label-position="top" label-width="120px" class="payment-form">
            <!-- 缴费类型 -->
            <el-form-item label="缴费类型" prop="paymentType"
                :rules="[{ required: true, message: '请选择缴费类型', trigger: 'change' }]">
                <el-select v-model="paymentForm.paymentType" placeholder="请选择缴费类型" class="full-width-select">
                    <el-option label="水费" value="water" />
                    <el-option label="电费" value="electricity" />
                    <el-option label="物业费" value="property" />
                </el-select>
            </el-form-item>

            <!-- 缴费金额 -->
            <el-form-item label="缴费金额（元）" prop="amount" :rules="[
                { required: true, message: '请输入金额', trigger: 'blur' },
                { pattern: /^\d+(\.\d{1,2})?$/, message: '请输入有效金额格式', trigger: 'blur' }
            ]">
                <el-input v-model="paymentForm.amount" placeholder="0.00" type="number" step="0.01" class="amount-input">
                    <template #prefix>¥</template>
                </el-input>
            </el-form-item>

            <!-- 操作按钮 -->
            <el-form-item class="form-actions">
                <el-button @click="closeDrawer" size="medium">取消</el-button>
                <el-button type="primary" size="medium" @click="handlePayment" :loading="paymentLoading">
                    立即支付
                </el-button>
            </el-form-item>
        </el-form>
    </el-drawer>
    <el-dialog v-model="dialogContractVisible" title="合同与房屋详情" width="600px">
        <el-descriptions title="合同信息" border>
            <el-descriptions-item label="合同编号">{{ contractData.house_id }}</el-descriptions-item>
            <el-descriptions-item label="租客">{{ contractData.user_id }}</el-descriptions-item>
            <el-descriptions-item label="租金">{{ contractData.monthly_rent }} 元/月</el-descriptions-item>
            <el-descriptions-item label="开始日期">{{ contractData.start_date }}</el-descriptions-item>
            <el-descriptions-item label="结束日期">{{ contractData.end_date }}</el-descriptions-item>
        </el-descriptions>

        <el-divider></el-divider>

        <el-descriptions title="房屋信息" border>
            <el-descriptions-item label="房间号">{{ houseData.house_number }}</el-descriptions-item>
            <el-descriptions-item label="剩余电费">{{ houseData.power_fee }}</el-descriptions-item>
            <el-descriptions-item label="剩余水费">{{ houseData.water_fee }}</el-descriptions-item>
            <el-descriptions-item label="房屋押金">{{ houseData.deposit }}</el-descriptions-item>
        </el-descriptions>

        <template #footer>
            <el-button @click="dialogContractVisible = false">关闭</el-button>
        </template>
    </el-dialog>
    <el-dialog v-model="dialogAnnounceMentVisible" title="公告通知" width="600px" :close-on-click-modal="false">
        <el-scrollbar max-height="400px">
            <el-card v-for="(announcement, index) in announcements" :key="announcement.id" shadow="never" class="mb-4">
                <template #header>
                    <span style="font-size: 16px; font-weight: bold;">{{ announcement.title }}</span>
                </template>
                <div style="white-space: pre-wrap;">{{ announcement.content }}</div>
                <div style="margin-top: 10px; text-align: right; color: gray;">
                    发布时间: {{ announcement.created_at }}
                </div>
                <el-divider v-if="index < announcements.length - 1"></el-divider>
            </el-card>
        </el-scrollbar>

        <template #footer>
            <el-button type="primary" @click="dialogAnnounceMentVisible = false">我知道了</el-button>
        </template>
    </el-dialog>

    <el-dialog v-model="dialogRepairVisible" title="提交报修" width="500px">
        <el-form :model="repairForm" label-width="80px">
            <el-form-item label="房屋 ID">
                <el-input v-model="repairForm.house_id" type="text" disabled />
            </el-form-item>
            <el-form-item label="描述">
                <el-input v-model="repairForm.description" type="textarea" :rows="3" placeholder="请描述具体的报修问题..." />
            </el-form-item>
        </el-form>

        <template #footer>
            <el-button @click="dialogRepairVisible = false">取消</el-button>
            <el-button type="primary" @click="submitRepair">提交</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { ElMessage, ElLoading, ElMessageBox  } from 'element-plus'
import router from '@/router/index'
import api from '@/config/axios'
import dayjs from 'dayjs'

import { House, Money, Bell, Tools, Document, Clock, Memo, Plus } from '@element-plus/icons-vue'

const token = ref('')
const userInfo = ref({})
const paymentForm = ref({})
const announcements = ref({});
const showUpload = ref(false)
const repairForm = ref({
    house_id: '',
    description: '',
    status: ''
});

const house = ref({})
const showcard = ref('')
const loading = ref(true)
const drawerVisible = ref(false)
const dialogAnnounceMentVisible = ref(false);
const dialogRepairVisible = ref(false);


const recentBills = ref([])
const contractData = ref([])
const houseData = ref([])
const dialogContractVisible = ref(false)

const getTypeTag = (type) => {
    switch (type) {
        case 'water':
            return 'primary';
        case 'power':
            return 'success';
        case 'maintenance':
            return 'warning';
        case 'rent':
            return 'info';
        case 'utilities':
            return 'danger';
        case 'deposit':
            return 'success';
        default:
            return 'default';
    }
};

const getTypeLabel = (type) => {
    switch (type) {
        case 'water':
            return '水费';
        case 'power':
            return '电费';
        case 'maintenance':
            return '维修费';
        case 'rent':
            return '租金';
        case 'utilities':
            return '物业费';
        case 'deposit':
            return '押金';
        default:
            return type;
    }
};


const repairProgress = ref([])

const statusTypeMap = {
    'pending': 'danger',
    'in_progress': 'warning',
    'completed': 'success'
}

const goToHome = () => router.push('/')
const logOut = () => {
    localStorage.removeItem('token');
    router.push('/login');
};
const beforeAvatarUpload = (rawFile) => {
  const allowedTypes = ['image/*']
  const maxSize = 2

  if (!allowedTypes.includes(rawFile.type)) {
    ElMessage.error('只允许上传图片类型!')
    return false
  }

  if (rawFile.size / 1024 / 1024 > maxSize) {
    ElMessage.error(`图片大小不能超过 ${maxSize}MB!`)
    return false
  }

  return true
}

const goToNotice = async () => {
    try {
        const res = await api.post('announcement/getAllAnnouncement');
        if (res.data.code === 0) {
            announcements.value = res.data.data;
            dialogAnnounceMentVisible.value = true; // 显示公告弹框
        } else {
            ElMessage.info('暂无公告');
        }
    } catch (error) {
        ElMessage.error('获取公告失败: ' + error.message);
    }
}
const goToRepair = async () => {
    if (showcard.value != 'occupied') {
        ElMessage.warning("您还未租房")
        return
    }
    repairForm.value.house_id = house.value.id;
    repairForm.value.status = 'in_progress';
    dialogRepairVisible.value = true;
}

//缴费模块
const goToPayment = () => {
    if (showcard.value != 'occupied') {
        ElMessage.warning('您还未租房！')
        return;
    }
    drawerVisible.value = true
}
const handlePayment = async () => {
    loading.value = true
    switch (paymentForm.value.paymentType) {
        case 'water':
            try {
                const fee = await api.post('fee/createWaterFee', {
                    house_number: house.value.house_number,
                    amount: paymentForm.value.amount,
                    user_id: userInfo.value.id
                })
                if (fee.data.code != 0) {
                    ElMessage.error('订单创建失败' + fee.data.message)
                    loading.value = false
                    return
                }
                loading.value = false
                ElMessage.success('预定成功，正在跳转支付宝支付页面，请及时支付！')

                const formHtml = fee.data.data;

                const formElement = document.createElement('div');
                formElement.innerHTML = formHtml;
                document.body.appendChild(formElement);

                nextTick(() => {
                    const form = formElement.querySelector('form');
                    if (form) {
                        form.submit();
                    } else {
                        console.error('没有找到表单元素');
                    }
                });
            } catch (error) {
                ElMessage.error('出错了！' + error)
            }
            break;
        case 'power':
            break;
        case 'property':
            break;
    }
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

//合同模块
const goToContract = async () => {
    console.log(showcard.value)
    if (showcard.value != 'occupied') {
        ElMessage.warning('您还未租房！')
        return;
    }
    const resContract = await api.post('leaseContract/getActiveLeaseContractByUserId', {
        user_id: userInfo.value.id
    });
    const resHouse = await api.post('house/getByOwnerId');
    if (resHouse.data.code != 0) {
        ElMessage.error('获取房屋数据失败' + res.data.message)
        return
    }
    if (resContract.data.code != 0) {
        ElMessage.error('获取合同数据失败' + res.data.message)
        return
    }
    contractData.value = resContract.data.data;
    houseData.value = resHouse.data.data;
    dialogContractVisible.value = true;
}

//报修模块
const submitRepair = async () => {
    if (repairForm.value.house_id === null || repairForm.value.description === null) {
        ElMessage.warning('请填写完整的报修信息');
        return;
    }
    try {
        const res = await api.post('repair/insertRepair', {
            house_id: repairForm.value.house_id,
            description: repairForm.value.description,
            status: repairForm.value.status
        });
        if (res.data.code === 0) {
            ElMessage.success('报修提交成功');
            dialogRepairVisible.value = false; // 关闭弹框
        } else {
            ElMessage.error('报修提交失败: ' + res.data.message);
            console.log(error)
        }
    } catch (error) {
        ElMessage.error('提交失败: ' + error.message);
        console.log(error)
    }
};


onMounted(async () => {
    try {
        //获取用户信息
        token.value = localStorage.getItem('token');
        if (token.value === null) {
            router.push('/')
            ElMessage.error('您还未登录！');
        }
        const user = await api.post('user/getUserInfo');
        if (user.data.code !== 0) {
            router.push('/login');
            ElMessage.error('token过期请重新登录！');
        }
        userInfo.value = user.data.data;

        //获取用户房屋信息
        const waitingOrder = await api.post('house/getByOwnerId');
        if (waitingOrder.data.data != null) {
            house.value = waitingOrder.data.data
            showcard.value = 'waiting';
        }

        //获取用户合同
        const leaseContract = await api.post('leaseContract/getActiveLeaseContractByUserId', {
            user_id: userInfo.value.id
        });
        if (leaseContract.data.data != null) {
            showcard.value = 'occupied';

            //获取报修记录
            const repairList = await api.get(`repair/selectRepairByHouseId?house_id=${house.value.id}`)
            repairProgress.value = repairList.data.data
        }

        //获取订单信息
        const FeeList = await api.post('fee/getByUserId');
        recentBills.value = FeeList.data.data;
        recentBills.value.forEach(item => {
            item.created_at = dayjs(item.created_at).format('YYYY-MM-DD HH:mm:ss');
            item.due_date = dayjs(item.due_date).format('YYYY-MM-DD');
        });


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

        .avatar-container {
            position: relative;
            display: inline-block;
        }

        .avatar-wrapper {
            position: relative;
            cursor: pointer;
        }

        .user-avatar {
            transition: all 0.3s ease;
        }

        .avatar-container {
            position: relative;
            display: inline-block;
        }

        .avatar-wrapper {
            position: relative;
            cursor: pointer;
        }

        .user-avatar {
            transition: all 0.3s ease;
        }

        .avatar-hover {
            filter: grayscale(50%);
            opacity: 0.7;
        }

        .upload-mask {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: all 0.3s ease;
        }

        .upload-icon {
            font-size: 24px;
            color: white;
        }

        ::v-deep .el-upload {
            display: block;
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            opacity: 0;
            cursor: pointer;
        }

        ::v-deep .el-upload:hover {
            opacity: 1;
        }

        .avatar-hover {
            filter: grayscale(50%);
            opacity: 0.7;
        }

        .upload-mask {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: all 0.3s ease;
        }

        .upload-icon {
            font-size: 24px;
            color: white;
        }

        ::v-deep .el-upload {
            display: block;
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            opacity: 0;
            cursor: pointer;
        }

        ::v-deep .el-upload:hover {
            opacity: 1;
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

.payment-drawer {
    padding: 20px;
}

.payment-form {
    padding: 0 24px;
}

.full-width-select {
    width: 100%;
}

.amount-input {
    width: 100%;
}

.amount-input :deep(.el-input__prefix) {
    display: flex;
    align-items: center;
    padding-left: 8px;
    color: #606266;
}

.form-actions {
    margin-top: 32px;
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}
</style>