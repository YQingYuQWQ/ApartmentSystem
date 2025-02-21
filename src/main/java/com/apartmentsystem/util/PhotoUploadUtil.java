package com.apartmentsystem.util;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PhotoUploadUtil {

    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 上传文件
     *
     * @param url         上传文件的服务器 URL
     * @param file        要上传的文件
     * @param formParams  其他表单参数
     * @return 响应结果
     * @throws IOException 可能抛出的异常
     */
    public static String uploadFile(String url, File file, Map<String, String> formParams) throws IOException {
        // 创建文件的 RequestBody，指定图片类型为 image/*
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/*"));

        // 创建 MultipartBody.Builder 用于构建上传内容
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        // 添加文件
        builder.addFormDataPart("file", file.getName(), fileBody);

        // 添加其他表单参数（如 token、权限等）
        if (formParams != null) {
            for (Map.Entry<String, String> entry : formParams.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        // 构建请求体
        MultipartBody requestBody = builder.build();
        // 固定添加 album_id
        builder.addFormDataPart("album_id", "1653");

        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "multipart/form-data")  // 设置 Content-Type
                .addHeader("Authorization", "Bearer " + "993|GFIUd0wjLxwDEkx11wKqPgxuwGQQIYnydH3SorS3")  // 设置 token
                .build();

        // 执行请求并获取响应
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();  // 返回响应内容
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}

