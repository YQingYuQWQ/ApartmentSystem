const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8081,
    allowedHosts: [
      'myredstone.top',
      'ddns.myredstone.top'
    ],
    client: {
      overlay: false
  },
  },
});
