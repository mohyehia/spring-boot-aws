package com.moh.yehia.sqs.controller;

import com.moh.yehia.sqs.model.MessageRequest;
import com.moh.yehia.sqs.model.MyMessage;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final SqsTemplate sqsTemplate;

    @PostMapping
    public String send(@RequestBody MessageRequest messageRequest) throws Exception {
        messageRequest.setUserId(UUID.randomUUID().toString());
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("messageRequest =>{}", messageRequest);
        // Send a message with the provided options.
        SendResult<Object> sendResultResponse = sqsTemplate.send(sqsSendOptions ->
                sqsSendOptions
                        .payload(messageRequest)
                        .header("my-custom-header", UUID.randomUUID().toString())
        );
        log.info("sendResultResponse with provided options =>{}", sendResultResponse);

        Thread.sleep(2000);

        // Send a message to the provided queue with the given payload.
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        SendResult<MessageRequest> sendResultWithQueue = sqsTemplate.send(messageRequest);
        log.info("sendResultWithQueue =>{}", sendResultWithQueue);

        return "Message sent successfully!";
    }

    @PostMapping("/msg")
    public String sendMessage(@RequestBody MessageRequest messageRequest) {
        messageRequest.setUserId(UUID.randomUUID().toString());
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("messageRequest =>{}", messageRequest);

        SendResult<MessageRequest> sendResultWithMessage = sqsTemplate.send("queue name", new MyMessage(messageRequest));
        log.info("sendResultWithMessage =>{}", sendResultWithMessage);
        return "New message published successfully!";
    }
}
