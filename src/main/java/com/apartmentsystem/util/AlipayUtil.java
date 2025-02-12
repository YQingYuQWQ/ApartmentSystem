package com.apartmentsystem.util;


import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AlipayUtil {

    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwwbQIISof7d97gER63twSvcNwrFOKFISJ0u7SxTai1SNmSDJfETojz8fPUGn1qTqq+Q8fXtd0riKkPPFMXlTParRdEKwsPheCPxCbJtmMCY39cHA37wVOtygKJpqxJpKGYdTB7l+CjUyLUYLyzYPnIBqcDYyKj6FtM3dcsXaHaiWQR39Dcb736TZ+Q3qxrFzYv17RqrqpM8QiW5gRTjxmnIes6xdbUifMWKNnP3OK8ZF5w2S5xPgJy+5XQfjQV6gHpLVR8ov3f0HfMMlrhaGY9y3JDM/1fzzbU/yIt5uracBJpPpZPe280DbrCCQfzo+o0lrTXsf0bjhPAn4oJl1QwIDAQAB";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";

    /**
     * 验证支付宝回调签名
     * @param params 回调参数
     * @return 验签结果（true = 通过，false = 失败）
     */
    public static boolean verifySignature(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

