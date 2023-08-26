package com.moh.yehia.dynamodb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.dynamo")
@Data
public class DynamoProperties {
    private String serviceEndpoint;
    private String signingRegion;
    private String accessKey;
    private String secretKey;
}
