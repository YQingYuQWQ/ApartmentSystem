import axios from 'axios';

const api = axios.create({
  baseURL: 'http://ddns.myredstone.top:4089/',
  timeout: 50000,
});

api.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers['Authorization'] = `${token}`
      }
      return config
    },
    (error) => {
      return Promise.reject(error)
    }
  )

export default api;