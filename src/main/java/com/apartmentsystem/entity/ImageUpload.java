package com.apartmentsystem.entity;

import lombok.Data;

@Data
public class ImageUpload {
    private String result;
    private int code;
    private String url;
    private String srcName;
    private String thumb;
    private String del;
    private int id;
    private String message;
}
