package com.apartmentsystem.service;

import com.apartmentsystem.entity.ImageUpload;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String saveUpload(MultipartFile file);
    ImageUpload uploadImage(MultipartFile file);
}
