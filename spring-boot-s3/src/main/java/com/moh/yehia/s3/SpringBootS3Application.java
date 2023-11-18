package com.moh.yehia.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SpringBootS3Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootS3Application.class);
    }
}
