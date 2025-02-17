import axios from 'axios';

// 创建 axios 实例并设置默认的 baseURL
const api = axios.create({
  baseURL: 'http://ddns.myredstone.top:4089/', // 设置默认域名
  timeout: 5000, // 可选，设置请求超时时间
});

axios.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`
      }
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )

export default api;