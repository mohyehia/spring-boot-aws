package com.moh.yehia.s3.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.moh.yehia.s3.model.ObjectUploadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/objects")
@RequiredArgsConstructor
@Log4j2
public class ObjectController {
    private final AmazonS3 amazonS3;

    @PostMapping
    public PutObjectResult uploadObject(@ModelAttribute ObjectUploadRequest objectUploadRequest) throws IOException {
        MultipartFile multipartFile = objectUploadRequest.getMultipartFile();
        InputStream inputStream = multipartFile.getInputStream();
        String key = "products/" + UUID.randomUUID().toString() + ".png";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setExpirationTime(Date.from(Instant.now()));
        PutObjectRequest putObjectRequest = new PutObjectRequest(objectUploadRequest.getBucketName(), key, inputStream, objectMetadata);
        PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
        log.info("putObjectResult =>{}", putObjectResult.toString());
        return putObjectResult;
    }

    @GetMapping
    public ListObjectsV2Result retrieveAll(@RequestParam("bucket") String bucketName) {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request();
        listObjectsV2Request.setBucketName(bucketName);
        listObjectsV2Request.setPrefix("products/");
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(listObjectsV2Request);
        log.info("retrieved successfully the bucket objects!");
        for (S3ObjectSummary objectSummary : listObjectsV2Result.getObjectSummaries()) {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(objectSummary.getBucketName(), objectSummary.getKey()));
            log.info("s3 object url =>{}", amazonS3.getUrl(s3Object.getBucketName(), s3Object.getKey()).toExternalForm());
        }
        return listObjectsV2Result;
    }
}
