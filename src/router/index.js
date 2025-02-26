import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import Preview from '@/views/Preview.vue'
import Register from '@/views/Register.vue'
import UserCenter from '@/views/UserCenter.vue'
import HouseInfo from '@/views/HouseInfo.vue'
import ManagerCenter from '@/views/manager/ManagerCenter.vue'
import Dashboard from '@/views/manager/Dashboard.vue'
import HouseManage from '@/views/manager/HouseManage.vue'
import ContractManage from '@/views/manager/ContractManage.vue'
import AnnouncementManage from '@/views/manager/AnnouncementManage.vue'
import RepairManage from '@/views/manager/RepairManage.vue'
import SuperManagerCenter from '@/views/supermanager/SuperManagerCenter.vue'


const routes = [
  {
    path: '/',
    name: 'preview',
    component: Preview
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/usercenter',
    name: 'usercenter',
    component: UserCenter
  },
  {
    path: '/houseinfo',
    name: 'houseinfo',
    component: HouseInfo
  },
  {
    path: '/managercenter',
    name: '物业管理中心',
    component: ManagerCenter,
    redirect: '/managercenter/dashboard',
    children: [
      {
        path: 'dashboard',
        name: '数据看板',
        component: Dashboard 
      },
      {
        path: 'housemanage',
        name: '房屋管理',
        component: HouseManage 
      },
      {
        path: 'contractmanage',
        name: '合同管理',
        component: ContractManage
      },
      {
        path: 'announcementmanage',
        name: '公告管理',
        component: AnnouncementManage
      },
      {
        path: 'repairmanage',
        name: '报修管理',
        component: RepairManage
      }
    ]
  },
  {
    path: '/supermanagercenter',
    name: '超级管理员中心',
    component: SuperManagerCenter,
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
