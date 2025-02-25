<template>
  <div class="dashboard-container">
    <!-- 数据卡片组 -->
    <div class="metric-grid">
      <div class="metric-card" v-for="(item, index) in metrics" :key="index" :class="`metric-${index}`">
        <div class="card-content">
          <div class="icon-wrapper">
            <component :is="item.icon" class="metric-icon" />
          </div>
          <div class="metric-info">
            <div class="label">{{ item.label }}</div>
            <div class="value">{{ item.value }}</div>
          </div>
        </div>
        <div class="trend">
          <span :class="`trend-${item.trend}`">{{ item.trendValue }}</span>
        </div>
      </div>
    </div>

    <!-- 可视化图表区 -->
    <div class="chart-grid">
      <div class="chart-card main-chart">
        <div class="chart-header">
          <h3>房屋状态分布</h3>
          <el-radio-group v-model="chartType" size="small">
            <el-radio-button label="bar">柱状图</el-radio-button>
            <el-radio-button label="pie">饼图</el-radio-button>
          </el-radio-group>
        </div>
        <div ref="mainChart" class="chart-content"></div>
      </div>

      <div class="chart-card side-chart">
        <h3>报修处理进度</h3>
        <div ref="progressChart" class="chart-content"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import { House, Tools, Document, User } from '@element-plus/icons-vue'

const metrics = ref([
  { label: '总房屋数', value: '356', icon: House, trend: 'up', trendValue: '+2.3%' },
  { label: '待处理报修', value: '23', icon: Tools, trend: 'down', trendValue: '-1.2%' },
  { label: '生效合同', value: '289', icon: Document, trend: 'up', trendValue: '+5.6%' },
  { label: '在线用户', value: '1.2k', icon: User, trend: 'steady', trendValue: '0.0%' }
])

const chartType = ref('bar')
const mainChart = ref(null)
const progressChart = ref(null)

onMounted(() => {
  renderMainChart()
  renderProgressChart()
})

watch(chartType, () => {
  renderMainChart()
})

const chartColors = ['#36a3ff', '#34bfa3', '#ffbf35', '#f4516c']

const renderMainChart = () => {
  const chart = echarts.init(mainChart.value)
  const option = {
    color: chartColors,
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    grid: {
      top: 40,
      bottom: 30,
      containLabel: true
    },
    [chartType.value === 'bar' ? 'xAxis' : '_']: {
      type: 'category',
      data: ['可租', '已租', '维护中'],
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      }
    },
    [chartType.value === 'bar' ? 'yAxis' : '_']: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      }
    },
    series: [{
      type: chartType.value,
      data: [
        { value: 120, name: '可租' },
        { value: 200, name: '已租' },
        { value: 36, name: '维护中' }
      ],
      ...(chartType.value === 'pie' ? {
        radius: ['40%', '70%'],
        label: {
          show: true,
          formatter: '{b|{b}}\n{c} ({d}%)',
          rich: {
            b: {
              fontSize: 14,
              lineHeight: 20
            }
          }
        }
      } : {
        barWidth: 40,
        itemStyle: {
          borderRadius: [6, 6, 0, 0]
        }
      })
    }]
  }
  chart.setOption(option)
}

const renderProgressChart = () => {
  const chart = echarts.init(progressChart.value)
  const option = {
    color: ['#36a3ff', '#ffbf35', '#34bfa3'],
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: 'bottom'
    },
    series: [{
      type: 'pie',
      radius: ['55%', '85%'],
      avoidLabelOverlap: false,
      label: {
        show: false
      },
      data: [
        { value: 23, name: '待处理' },
        { value: 15, name: '处理中' },
        { value: 89, name: '已完成' }
      ]
    }]
  }
  chart.setOption(option)
}
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 24px;
  background: #f8fafc;
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 20px;
  margin-bottom: 24px;

  .metric-card {
    background: white;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    transition: transform 0.3s, box-shadow 0.3s;
    position: relative;
    overflow: hidden;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
    }

    .card-content {
      display: flex;
      align-items: center;
      gap: 16px;

      .icon-wrapper {
        width: 48px;
        height: 48px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: linear-gradient(135deg, var(--icon-bg1), var(--icon-bg2));
        
        .metric-icon {
          width: 24px;
          height: 24px;
          color: white;
        }
      }

      .metric-info {
        .label {
          color: #64748b;
          font-size: 14px;
          margin-bottom: 4px;
        }

        .value {
          color: #1e293b;
          font-size: 24px;
          font-weight: 700;
        }
      }
    }

    .trend {
      position: absolute;
      top: 12px;
      right: 12px;
      font-size: 12px;
      padding: 4px 8px;
      border-radius: 4px;

      &-up { background: #e6f4ff; color: #1677ff; }
      &-down { background: #fff1f0; color: #ff4d4f; }
      &-steady { background: #f6ffed; color: #52c41a; }
    }

    &.metric-0 { --icon-bg1: #36a3ff; --icon-bg2: #689fff; }
    &.metric-1 { --icon-bg1: #ffbf35; --icon-bg2: #ffd666; }
    &.metric-2 { --icon-bg1: #34bfa3; --icon-bg2: #3cd4ad; }
    &.metric-3 { --icon-bg1: #f4516c; --icon-bg2: #ff758c; }
  }
}

.chart-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;

  @media (max-width: 1200px) {
    grid-template-columns: 1fr;
  }

  .chart-card {
    background: white;
    border-radius: 12px;
    padding: 24px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);

    .chart-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 24px;

      h3 {
        margin: 0;
        color: #1e293b;
        font-size: 18px;
      }
    }

    .chart-content {
      height: 400px;
    }
  }
}
</style>