package com.apartmentsystem.util;

import com.apartmentsystem.entity.ImageUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class UploadImageUtil {

    @Value("${custom.file.path}")
    private String UPLOAD_DIR;
    @Value("${custom.token}")
    private String token;
    @Value("${custom.imageUrl}")
    private String url;

    private final OkHttpClient httpClient = new OkHttpClient();

    public String saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            InputStream fileStream = file.getInputStream();
            Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            fileStream.close();
            return fileName;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public ImageUpload uploadImage(MultipartFile image) {
        try {
            String filename = saveImage(image);
            Path filePath = Paths.get(UPLOAD_DIR, filename);
            if (!Files.exists(filePath)) {
                throw new RuntimeException("文件不存在: " + filename); // 转为运行时异常
            }

            File file = filePath.toFile();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", file.getName(),
                            RequestBody.create(file, MediaType.parse("image/*")))
                    .addFormDataPart("token", token)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("请求失败: " + response.code());
                }

                String responseBody = response.body() != null ? response.body().string() : null;
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(responseBody, ImageUpload.class);
            }
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e); // 包装为运行时异常
        }
    }
}
