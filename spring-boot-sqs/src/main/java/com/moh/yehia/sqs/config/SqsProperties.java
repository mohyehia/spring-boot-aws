package com.moh.yehia.sqs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsProperties {
    private String serviceEndpoint;
    private String signingRegion;
    private String accessKey;
    private String secretKey;
}
