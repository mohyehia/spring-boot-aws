package com.moh.yehia.s3.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ObjectUploadRequest {
    private MultipartFile multipartFile;
    private String bucketName;
}
