<template>
    <div>
      <button @click="createAliPayOrder">立即支付</button>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      createAliPayOrder() {
        const fee = {
          body: 'Fee202502232221355490533',
          out_trade_no: 'Fee202502232221355490533',
          qr_pay_mode: '2',
          subject: '公寓管理系统-缴费-押金',
          time_expire: '2025-02-23 22:51:35',
          total_amount: '3000.00'
        };
  
        // 创建表单元素
        const form = document.createElement('form');
        form.name = 'punchout_form';
        form.method = 'post';
        form.action = 'https://openapi-sandbox.dl.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=U84eP%2BCiehusvH19Oie7bTbPnmSLX4QeynVPRce%2F6jzT7ORG0ZcwwbvjaPIUjL4PXi7XTmO%2B8bQH1N7LnMliCotidLFsNey0mYd43UWqSMMPilZZfiZwv8OsombQS7SFn4%2F%2F60SBaTOfSF6VRvrVWPiN0fizIGj2GI1RvjwjWlfEBZB7U2yp%2BKFB1RiuL%2BxQF1%2BKT59Aw650SZ7Gcqgj8FND9v1ZXgm1rFFH9EXytwONEXjC1qbselRhR4Z%2F9pe2nVVu8P6XDnqfUXgqs9zPS%2Bc5QIpQ70eQOb0bZ3ZCmbG3%2FiUUSIvr7CLVPjPBH2pa%2BSNtqKrJs8JHfNRzr9af%2Bg%3D%3D&return_url=http%3A%2F%2Fddns.myredstone.top%3A23222%2Fusercenter&notify_url=http%3A%2F%2Fddns.myredstone.top%3A23333%2Falipay%2Fnotify&version=1.0&app_id=9021000138605307&sign_type=RSA2&timestamp=2025-02-23+22%3A21%3A36&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json';
  
        // 创建隐藏的输入框
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'biz_content';
        input.value = JSON.stringify(fee);
        form.appendChild(input);
  
        // 将表单添加到页面并提交
        document.body.appendChild(form);
        form.submit();
      }
    }
  };
  </script>
  