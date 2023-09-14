package com.moh.yehia.sqs.controller;

import com.amazonaws.http.SdkHttpMetadata;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/queues")
@RequiredArgsConstructor
@Slf4j
public class QueueController {
    private final AmazonSQSAsync amazonSQS;

    @GetMapping
    public List<String> findQueues() {
        ListQueuesResult listQueuesResult = amazonSQS.listQueues();
        List<String> queueUrls = listQueuesResult.getQueueUrls();
        log.info("retrieved queues =>{}", listQueuesResult);
        return queueUrls;
    }

    @GetMapping("/{prefix}")
    public List<String> findQueuesWithPrefix(@PathVariable("prefix") String prefix) {
        ListQueuesResult listQueuesResult = amazonSQS.listQueues(prefix);
        SdkHttpMetadata sdkHttpMetadata = listQueuesResult.getSdkHttpMetadata();
        log.info("sdkHttpMetadata.getHttpStatusCode() =>{}", sdkHttpMetadata.getHttpStatusCode());
        Map<String, List<String>> httpHeaders = sdkHttpMetadata.getAllHttpHeaders();
        httpHeaders.forEach((k, v) -> log.info("httpHeader =>{}, httpHeaderValue =>{}", k, v.toString()));
        List<String> queueUrls = listQueuesResult.getQueueUrls();
        log.info("retrieved queues with prefix {} are =>{}", prefix, listQueuesResult);
        return queueUrls;
    }
}
