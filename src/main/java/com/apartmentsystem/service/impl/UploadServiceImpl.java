package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.ImageUpload;
import com.apartmentsystem.service.UploadService;
import com.apartmentsystem.util.UploadImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UploadImageUtil uploadImageUtil;

    /**
     * Save the uploaded file
     * @param file
     * @return
     */
    @Override
    public String saveUpload(MultipartFile file) {
        return uploadImageUtil.saveImage(file);
    }

    /**
     * Upload the image
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public ImageUpload uploadImage(MultipartFile file){
        return uploadImageUtil.uploadImage(file);
    }

}
