package com.apartmentsystem.config;

import com.alipay.api.AlipayConfig;
import com.apartmentsystem.prop.AlipayProperties;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Alipay_Config {
    @Bean
    public AlipayConfig alipayConfig(AlipayProperties alipayProperties) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(alipayProperties.getGatewayHost());
        alipayConfig.setAppId(alipayProperties.getAppId());
        alipayConfig.setPrivateKey(alipayProperties.getPrivateKey());
        alipayConfig.setFormat(alipayProperties.getFormat());
        alipayConfig.setCharset(alipayProperties.getCharset());
        alipayConfig.setAlipayPublicKey(alipayProperties.getAliPublicKey());
        alipayConfig.setSignType(alipayProperties.getSignType());
        alipayConfig.setEncryptKey(alipayProperties.getEncryptKey());
        alipayConfig.setEncryptType(alipayProperties.getEncryptType());
        System.out.println("-----支付宝配置初始化完成-----");
        return alipayConfig;
    }
}