<template>
  <div class="home-container">
    <!-- 渐变背景层 -->
    <div class="background-layer"></div>

    <!-- 导航栏 -->
    <nav class="main-nav">
      <div class="nav-brand">
        <img src="@/assets/logo.png" class="logo">
        <span>智慧公寓云管家</span>
      </div>
      <div class="nav-actions">
        <el-button type="primary" round @click="goToLogin">登录</el-button>
        <el-button type="success" round @click="goToRegister">立即注册</el-button>
      </div>
    </nav>

    <!-- 主内容区 -->
    <main class="content-wrapper">
      <h1 class="slogan">发现您的理想空间</h1>
      <p class="sub-slogan">精选优质公寓，智能管理体验</p>

      <!-- 房屋筛选 -->
      <div class="filter-bar">
        <el-radio-group v-model="filterStatus" @change="filterHouses">
          <el-radio-button value="all">全部房源</el-radio-button>
          <el-radio-button value="vacant">可租状态</el-radio-button>
          <el-radio-button value="occupied">已入住</el-radio-button>
        </el-radio-group>
      </div>

      <!-- 房屋卡片网格 -->
      <div class="house-grid">
        <transition-group name="staggered-fade" tag="div" class="house-grid">
          <el-card v-for="(house, index) in filteredHouses" :key="house.id" class="house-card"
            :style="{ 'transition-delay': `${index * 0.1}s` }">
            <template #header>
              <div class="card-header">
                <el-tag :type="house.status === 'vacant' ? 'success' : 'danger'" effect="dark" class="status-tag">
                  {{ house.status === 'vacant' ? '可租' : '已入住' }}
                </el-tag>
                <h3 class="house-building_name">{{ house.building_name }}</h3>
              </div>
            </template>

            <el-carousel :interval="5000" height="200px" indicator-position="outside">
              <el-carousel-item v-for="img in house.images" :key="img">
                <el-image :src="img" fit="cover" class="house-image" :preview-src-list="house.images" />
              </el-carousel-item>
            </el-carousel>

            <div class="house-info">
              <div class="info-item">
                <el-icon>
                  <OfficeBuilding />
                </el-icon>
                {{ house.area }}㎡
              </div>
              <div class="info-item">
                <el-icon>
                  <Location />
                </el-icon>
                {{ house.location }}
              </div>
              <div class="info-item price">
                ¥{{ house.price }}/月
              </div>
            </div>

            <el-button type="primary" class="book-btn" :disabled="house.status !== 'vacant'" @click="handleBook(house)">
              {{ house.status === 'vacant' ? '立即预订' : '已出租' }}
            </el-button>
          </el-card>
        </transition-group>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, getCurrentInstance } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '@/config/axios'

const router = useRouter()

// 模拟数据
const houses = ref([

])
const houses1 = ref([])

const filterStatus = ref('all')
const filteredHouses = computed(() => {
  if (filterStatus.value === 'all') return houses.value
  return houses.value.filter(h => h.status === filterStatus.value)
})

const handleBook = (house) => {
  ElMessage.info('请登录后进行预订操作')
  router.push('/login')
}

const goToLogin = () => router.push('/login')
const goToRegister = () => router.push('/register')

onMounted(() => {
  api.get('house/showList')
    .then(response => {
      houses.value = response.data.data.map(house => {
        house.location = ` ${house.building_name} ${house.floor}层 ${house.house_number}`;
        house.images = house.images.split(',');
        return house;
      });
    })
    .catch(error => {
      console.error('Error fetching house data:', error);
    });

  console.log(houses);
});
</script>

<style lang="scss" scoped>
.home-container {
  min-height: 100vh;
  position: relative;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.background-layer {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(45deg, #6a11cb 0%, #2575fc 100%);
  clip-path: polygon(0 0, 100% 0, 100% 30%, 0 50%);
  z-index: 0;
}

.main-nav {
  position: relative;
  padding: 1.5rem 5%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: rgba(255, 255, 255, 0.95);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 2;

  .nav-brand {
    display: flex;
    align-items: center;
    gap: 1rem;
    font-size: 1.5rem;
    font-weight: bold;
    color: #2c3e50;

    .logo {
      height: 40px;
      width: auto;
    }
  }
}

.content-wrapper {
  position: relative;
  padding: 4rem 5%;
  z-index: 1;

  .slogan {
    text-align: center;
    font-size: 2.5rem;
    color: #ffffff;
    margin-bottom: 1rem;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
  }

  .sub-slogan {
    text-align: center;
    color: rgba(255, 255, 255, 0.9);
    font-size: 1.2rem;
    margin-bottom: 3rem;
  }
}

/* 修改网格布局样式 */
.house-grid {
  display: grid;
  /* 自动适应列数，最小300px */
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  padding: 2rem 0;
  width: 100%;
  /* 添加容器宽度限制 */
}

/* 添加移动端响应式 */
@media (max-width: 768px) {
  .house-grid {
    grid-template-columns: 1fr;
  }
}

.house-card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border-radius: 12px;
  overflow: hidden;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
  }

  .card-header {
    display: flex;
    align-items: center;
    gap: 1rem;

    .status-tag {
      font-size: 0.9rem;
      padding: 6px 12px;
    }
  }

  .house-image {
    width: 100%;
    height: 200px;
    border-radius: 8px;
  }

  .house-info {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1rem;
    padding: 1rem 0;

    .info-item {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      color: #666;

      &.price {
        grid-column: span 2;
        font-size: 1.2rem;
        color: #f56c6c;
        font-weight: bold;
        justify-content: center;
      }
    }
  }

  .book-btn {
    width: 100%;
    margin-top: 1rem;
    transition: all 0.3s ease;

    &:hover {
      letter-spacing: 2px;
    }
  }
}

.staggered-fade-enter-active,
.staggered-fade-leave-active {
  transition: all 0.5s ease;
}

.staggered-fade-enter-from,
.staggered-fade-leave-to {
  opacity: 0;
  transform: translateY(30px);
}
</style>