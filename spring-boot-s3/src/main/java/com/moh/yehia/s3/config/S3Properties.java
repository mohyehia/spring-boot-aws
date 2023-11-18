package com.moh.yehia.s3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.s3")
@Data
public class S3Properties {
    private String accessKey;
    private String secretKey;
}
