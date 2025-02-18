<template>
  <router-view/>
</template>
<script setup>
import { watch } from 'vue'
import router from '@/router/index'

watch(
  () => router.currentRoute.value.path,
  (newPath) => {
    if (newPath === '/login' || newPath === '/register') {
      document.documentElement.style.height = '100%';
      document.body.style.height = '100%';
      document.body.style.margin = '0';
      document.body.style.overflow = 'hidden';
    } else {
      document.documentElement.style.height = '';
      document.body.style.height = '';
      document.body.style.margin = '';
      document.body.style.overflow = '';
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
</style>
