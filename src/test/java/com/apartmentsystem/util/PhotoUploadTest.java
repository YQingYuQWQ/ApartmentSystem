package com.apartmentsystem.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class PhotoUploadTest {
    public static void main(String[] args) throws IOException {
        String url = "https://www.helloimg.com/api/v1/upload";

        // 文件路径（替换成你要上传的图片文件路径）
        File imageFile = new File("C:\\Users\\Administrator\\Desktop\\aaa.png");

        // 构建其他表单参数
        Map<String, String> params = new HashMap<>();
        params.put("expired_at", "2025-12-31 23:59:59");

        // 调用工具类上传图片
        String response = PhotoUploadUtil.uploadFile(url, imageFile, params);

        // 输出响应内容
        System.out.println("Upload successful: " + response);
    }

}
