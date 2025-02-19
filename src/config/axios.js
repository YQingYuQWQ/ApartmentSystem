import axios from 'axios';

const api = axios.create({
  baseURL: 'http://ddns.myredstone.top:4089/',
  timeout: 50000,
});

api.interceptors.request.use(
    (config) => {
      console.log('axios拦截器触发')
      const token = localStorage.getItem('token')
      if (token) {
        config.headers['Authorization'] = `${token}`
        console.log("当前token：" + token)
      }
      console.log('Request Headers:', config.headers);
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )

export default api;