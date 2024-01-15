package com.moh.yehia.cognito.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
@RequiredArgsConstructor
public class CognitoConfig {
    private final CognitoProperties cognitoProperties;

    @Bean
    public CognitoIdentityProviderClient cognitoIdentityProviderClient() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(() -> AwsBasicCredentials.create(cognitoProperties.getAccessKey(), cognitoProperties.getSecretKey()))
                .build();
    }

}
