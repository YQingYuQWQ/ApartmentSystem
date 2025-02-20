<template>
    <div class="detail-container" v-loading.fullscreen.lock="pageloading">
        <!-- 图片轮播区 -->
        <div class="carousel-section">
            <el-carousel :interval="5000" height="500px" indicator-position="outside">
                <el-carousel-item v-for="(img, index) in roomData.images" :key="index">
                    <el-image :src="img" fit="cover" class="carousel-image" :preview-src-list="roomData.images"/>
                </el-carousel-item>
            </el-carousel>
            <el-tag class="status-tag" :type="statusType[roomData.status]" effect="dark">
                {{ statusText[roomData.status] }}
            </el-tag>
        </div>

        <!-- 主体内容 -->
        <div class="content-wrapper">
            <!-- 左侧信息区 -->
            <div class="info-section">
                <h1 class="room-title">{{ roomData.title }}</h1>
                <div class="meta-info">
                    <div class="info-item">
                        <el-icon>
                            <OfficeBuilding />
                        </el-icon>
                        <span class="label">公寓编号：</span>
                        <span class="value">{{ roomData.house_number }}</span>
                    </div>
                    <div class="info-item">
                        <el-icon>
                            <Location />
                        </el-icon>
                        <span class="label">所在位置：</span>
                        <span class="value">{{ roomData.building_name }} {{ roomData.floor }}层</span>
                    </div>
                    <div class="info-item">
                        <el-icon>
                            <DataAnalysis />
                        </el-icon>
                        <span class="label">房间面积：</span>
                        <span class="value">{{ roomData.area }}㎡</span>
                    </div>
                </div>

                <!-- 价格卡片 -->
                <el-card class="price-card">
                    <div class="price-content">
                        <div class="price-item">
                            <span class="label">每月租金</span>
                            <span class="value">¥{{ roomData.price }}</span>
                        </div>
                        <div class="price-item">
                            <span class="label">押金要求</span>
                            <span class="value">¥{{ roomData.price * 2 }}（押二付一）</span>
                        </div>
                    </div>
                </el-card>

                <!-- 设施服务 -->
                <div class="facility-section">
                    <h3>配套设施</h3>
                    <div class="facility-grid">
                        <div class="facility-item" v-for="item in roomData.facilities" :key="item">
                            <el-icon :size="24">
                                <component :is="facilityIcons[item]" />
                            </el-icon>
                            <span>{{ item }}</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 右侧预订栏 -->
            <div class="booking-section">
                <el-card class="booking-card">
                    <div class="booking-content">
                        <div class="price-display">
                            <span class="label">月租金</span>
                            <span class="price">¥{{ roomData.price }}</span>
                        </div>
                        <el-button type="primary" class="book-btn" :disabled="roomData.status !== 'vacant'"
                            @click="handleBooking" :loading="loading">
                            {{ bookingButtonText }}
                        </el-button>
                        <div class="tips">
                            <el-icon>
                                <InfoFilled />
                            </el-icon>
                            <span v-if="roomData.status === 'vacant'">当前可立即入住</span>
                            <span v-else>该房间{{ statusText[roomData.status] }}</span>
                        </div>
                    </div>
                </el-card>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import router from '@/router/index'
import { useRoute } from 'vue-router'
import { User, Unlock, Connection, Sunny, Document, KnifeFork, Basketball, Watermelon } from '@element-plus/icons-vue'


const route = useRoute()
const facilityIcons = {
    '独立卫浴': User,
    '智能门锁': Unlock,
    '高速WiFi': Connection,
    '空调': Sunny,
    '书桌椅': Document,
    '公共厨房': KnifeFork,
    '健身房': Basketball,
    '洗衣房': Watermelon
}

const roomData = ref({})
const pageloading = ref(true)
const loading = ref(false)
const statusType = {
    vacant: 'success',
    occupied: 'danger',
    under_maintenance: 'warning'
}
const statusText = {
    vacant: '可预订',
    occupied: '已出租',
    under_maintenance: '维护中'
}

const bookingButtonText = computed(() => {
    return roomData.value.status === 'vacant' ? '立即预订' : statusText[roomData.value.status]
})

// 预订处理
const handleBooking = async () => {
    try {
        await ElMessageBox.confirm(
            `确认预订 ${roomData.value.house_number} 房间？\n月租金：¥${roomData.value.price}`,
            '确认预订',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning'
            }
        )

        loading.value = true
        // 模拟API调用
        setTimeout(() => {
            loading.value = false
            ElMessage.success('预订成功！请前往个人中心完成签约')
            // 这里添加实际预订逻辑
        }, 1500)
    } catch (error) {
        ElMessage.info('已取消预订')
    }
}

onMounted(() => {
    roomData.value = {
        id: route.query.id,
        house_number: route.query.house_number,
        building_name: route.query.building_name,
        floor: route.query.floor,
        status: route.query.status,
        area: route.query.area,
        price: route.query.price,
        images: JSON.parse(route.query.images),
        facilities: ['独立卫浴', '智能门锁', '高速WiFi', '空调', '书桌椅']
    }

    pageloading.value = false
})
</script>

<style lang="scss" scoped>
.detail-container {
    max-width: 1400px;
    margin: 0 auto;
    padding: 20px;
    opacity: 0.9;

    .carousel-section {
        position: relative;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);

        .status-tag {
            position: absolute;
            top: 20px;
            right: 20px;
            z-index: 2;
            font-size: 16px;
            padding: 8px 16px;
        }
    }

    .content-wrapper {
        display: grid;
        grid-template-columns: 1fr 350px;
        gap: 30px;
        margin-top: 30px;

        @media (max-width: 992px) {
            grid-template-columns: 1fr;
        }
    }

    .info-section {
        .room-title {
            font-size: 28px;
            color: #303133;
            margin-bottom: 20px;
        }

        .meta-info {
            background: #f8f9fa;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;

            .info-item {
                display: flex;
                align-items: center;
                gap: 12px;
                margin-bottom: 15px;
                font-size: 16px;

                .label {
                    color: #606266;
                    min-width: 80px;
                }

                .value {
                    color: #303133;
                    font-weight: 500;
                }
            }
        }

        .price-card {
            margin-bottom: 25px;
            border-radius: 12px;

            .price-content {
                .price-item {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    padding: 15px 0;

                    .label {
                        color: #909399;
                    }

                    .value {
                        font-size: 18px;
                        font-weight: 600;
                        color: #409EFF;
                    }
                }
            }
        }

        .facility-section {
            h3 {
                font-size: 20px;
                margin-bottom: 15px;
            }

            .facility-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
                gap: 15px;

                .facility-item {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    padding: 12px;
                    background: #f8f9fa;
                    border-radius: 8px;
                    transition: all 0.3s;

                    &:hover {
                        background: #e9ecef;
                        transform: translateY(-2px);
                    }
                }
            }
        }
    }

    .booking-section {
        .booking-card {
            position: sticky;
            top: 20px;
            border-radius: 12px;

            .booking-content {
                .price-display {
                    text-align: center;
                    margin-bottom: 25px;

                    .label {
                        font-size: 14px;
                        color: #909399;
                    }

                    .price {
                        display: block;
                        font-size: 32px;
                        font-weight: 700;
                        color: #f56c6c;
                        margin-top: 8px;
                    }
                }

                .book-btn {
                    width: 100%;
                    height: 50px;
                    font-size: 18px;
                    letter-spacing: 2px;
                    border-radius: 8px;
                    background: linear-gradient(135deg, #409EFF, #36b5ff);
                    border: none;
                    transition: all 0.3s;

                    &:hover {
                        opacity: 0.9;
                        transform: translateY(-2px);
                    }

                    &:disabled {
                        background: #e0e0e0;
                        cursor: not-allowed;
                    }
                }

                .tips {
                    margin-top: 15px;
                    color: #909399;
                    font-size: 14px;
                    text-align: center;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    gap: 6px;
                }
            }
        }
    }
}

@media (max-width: 768px) {
    .carousel-section {
        .el-carousel {
            height: 300px !important;
        }
    }

    .price-card {
        .value {
            font-size: 16px !important;
        }
    }

    .booking-section {
        .booking-card {
            position: static !important;
            margin-top: 30px;
        }
    }
}
</style>