package com.moh.yehia.parameter.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.*;

import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringBootParameterStoreApplication {

    @Value("${dbUsername}")
    private String username;

    @Value("${dbPassword}")
    private String password;

    @Autowired
    private SsmClient ssmClient;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootParameterStoreApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        List<Parameter> parameters = ssmClient.getParametersByPath(request -> request.path("/config/spring-boot/")).parameters();
        parameters.forEach(param -> log.info("parameter loaded from aws parameter store =>{}", param));

//        addNewParameter();
//        updateParameter();
        updateTags();
        return args -> log.info("Loaded AWS parameter store values =>{}, {}", username, password);
    }

    private void updateTags() {
        String resourceId = "/config/spring-boot/dbHost";
        log.info("Updating resource tags");
        RemoveTagsFromResourceResponse removeTagsFromResourceResponse = ssmClient.removeTagsFromResource(RemoveTagsFromResourceRequest.builder().resourceId(resourceId).resourceType(ResourceTypeForTagging.PARAMETER).tagKeys("env").build());
        log.info("removeTagsFromResourceResponse =>{}", removeTagsFromResourceResponse);
        AddTagsToResourceResponse addTagsToResourceResponse = ssmClient.addTagsToResource(AddTagsToResourceRequest.builder().resourceId(resourceId).resourceType(ResourceTypeForTagging.PARAMETER).tags(Tag.builder().key("env").value("production").build()).build());
        log.info("addTagsToResourceResponse =>{}", addTagsToResourceResponse);
    }

    private void updateParameter() {
        log.info("updating existing parameter");
        PutParameterResponse putParameterResponse = ssmClient.putParameter(
                PutParameterRequest.builder()
                        .name("/config/spring-boot/dbHost")
                        .value("preprod-host")
                        .description("database host updated description")
                        .overwrite(true)
                        .build());
        log.info("PutParameterResponse =>{}", putParameterResponse);
    }

    private void addNewParameter() {
        log.info("creating new parameter");
        PutParameterResponse putParameterResponse = ssmClient.putParameter(
                PutParameterRequest.builder()
                        .name("/config/spring-boot/dbHost")
                        .value("localhost")
                        .dataType("text")
                        .description("database host")
                        .type(ParameterType.STRING)
                        .tier(ParameterTier.STANDARD)
                        .tags(Tag.builder().key("env").value("dev").build())
                        .build());
        log.info("PutParameterResponse =>{}", putParameterResponse);
    }

    @Bean
    SsmClient ssmClient() {
        return SsmClient.create();
    }

}
