package com.moh.yehia.event.bridge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "aws.event.bridge")
public class AwsProperties {
    private String accessKey;
    private String secretKey;
    private String source;
    private String detailType;
    private String eventBus;
}
