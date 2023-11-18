package com.moh.yehia.s3.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buckets")
@RequiredArgsConstructor
@Log4j2
public class BucketController {
    private final AmazonS3 amazonS3;

    @GetMapping
    public List<Bucket> viewBuckets() {
        log.info("BucketController :: viewBuckets :: start");
        List<Bucket> buckets = amazonS3.listBuckets();
        log.info("buckets =>{}", buckets);
        return buckets;
    }

    @DeleteMapping("/{name}")
    public String deleteByName(@PathVariable("name") String bucketName) {
        log.info("BucketController :: viewBuckets :: start");
        amazonS3.deleteBucket(new DeleteBucketRequest(bucketName));
        log.info("bucket deleted successfully!");
        return "bucket deleted successfully";
    }
}
