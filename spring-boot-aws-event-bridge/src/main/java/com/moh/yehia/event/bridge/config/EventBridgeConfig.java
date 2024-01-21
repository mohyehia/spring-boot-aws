package com.moh.yehia.event.bridge.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

@Configuration
@RequiredArgsConstructor
public class EventBridgeConfig {
    private final AwsProperties awsProperties;

    @Bean
    public EventBridgeClient eventBridgeClient() {
        return EventBridgeClient.builder()
                .region(Region.US_WEST_1)
                .credentialsProvider(() -> AwsBasicCredentials.create(awsProperties.getAccessKey(), awsProperties.getSecretKey()))
                .build();
    }
}
