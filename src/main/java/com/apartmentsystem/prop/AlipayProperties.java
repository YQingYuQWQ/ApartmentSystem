package com.apartmentsystem.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "alipay.alipay-client")
public class AlipayProperties {
    private String appId;
    private String privateKey;
    private String aliPublicKey;
    private String notifyUrl;
    private String encryptKey;
    private String gatewayHost;
    private String format;
    private String charset;
    private String signType;
    private String encryptType;
}
