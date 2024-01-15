package com.moh.yehia.cognito.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.cognito")
@Data
public class CognitoProperties {
    private String accessKey;
    private String secretKey;
    private String clientId;
}
