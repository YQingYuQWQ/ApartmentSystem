<template>
  <el-scrollbar style="height: 100vh">
   <router-view />
  </el-scrollbar>
</template>
<script setup>
import { watch } from 'vue'
import router from '@/router/index'

watch(
  () => router.currentRoute.value.path,
  (newPath) => {
    document.body.style.backgroundImage = ``;
    document.documentElement.style.height = '';
    document.body.style.height = '';
    document.body.style.margin = '';
    document.body.style.overflow = '';

    switch (newPath) {
      case '/login':
      case '/register':
        document.documentElement.style.height = '100%';
        document.body.style.height = '100%';
        document.body.style.margin = '0';
        document.body.style.overflow = 'hidden';
        document.body.style.backgroundImage = `url(${require('@/assets/BG-login.jpg')})`;
        document.body.style.backgroundSize = 'cover';
        document.body.style.backgroundPosition = 'center';
        break;

      case '/usercenter':
        document.documentElement.style.height = '100%';
        document.body.style.minHeight = '100vh';
        document.body.style.margin = '0';
        document.body.style.overflow = 'auto';
        document.body.style.backgroundImage = `url(${require('@/assets/BG-usercenter.png')})`;
        document.body.style.backgroundSize = 'cover';
        document.body.style.backgroundPosition = 'center';
        document.body.style.backgroundAttachment = 'fixed';
        document.body.style.backgroundRepeat = 'no-repeat';
        break;

      case '/houseinfo':
        document.documentElement.style.height = '100%';
        document.body.style.minHeight = '100vh';
        document.body.style.margin = '0';
        document.body.style.overflow = 'auto';
        document.body.style.backgroundImage = `url(${require('@/assets/BG-houseinfo.png')})`;
        document.body.style.backgroundSize = 'cover';
        document.body.style.backgroundPosition = 'center';
        document.body.style.backgroundAttachment = 'fixed';
        document.body.style.backgroundRepeat = 'no-repeat';
        break;

      default:
        break;
    }
  },
  { immediate: true }
)

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
html, body {
  overflow: hidden; /* 隐藏浏览器滚动条 */
}
</style>
