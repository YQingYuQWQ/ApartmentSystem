import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import Preview from '@/views/Preview.vue'
import Register from '@/views/Register.vue'
import UserCenter from '@/views/UserCenter.vue'
import HouseInfo from '@/views/HouseInfo.vue'

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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
