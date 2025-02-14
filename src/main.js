import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import axios from 'axios';

axios.defaults.baseURL = 'http://ddns.myredstone.top:4089/';
axios.defaults.timeout = 5000;

const app = createApp(App);

app.use(store)
   .use(router)
   .use(ElementPlus);

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

app.config.globalProperties.$axios = axios;

app.mount('#app');
