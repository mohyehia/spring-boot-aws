package com.moh.yehia.sqs.controller;

import com.moh.yehia.sqs.model.MessageRequest;
import com.moh.yehia.sqs.model.MyMessage;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Log4j2
public class MessageController {
    private final SqsTemplate sqsTemplate;

    private static final String QUEUE_NAME = "aws-sqs-queue";

    @PostMapping
    public String send(@RequestBody MessageRequest messageRequest) throws InterruptedException {
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        // Send a message with the provided options.
        SendResult<Object> sendResultResponse = sqsTemplate.send(sqsSendOptions ->
                sqsSendOptions.queue(QUEUE_NAME)
                        .payload(messageRequest)
                        .header("my-custom-header", UUID.randomUUID().toString())
        );
        log.info("sendResultResponse with provided options =>{}", sendResultResponse.toString());

        Thread.sleep(1000);

        // Send a message to the provided queue with the given payload.
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        SendResult<MessageRequest> sendResultWithQueue = sqsTemplate.send(QUEUE_NAME, messageRequest);
        log.info("sendResultWithQueue =>{}", sendResultWithQueue.toString());

        return "New message published successfully!";
    }

    @PostMapping("/message")
    public String sendMessage(@RequestBody MessageRequest messageRequest) {
        // Send the given Message to the provided queue.
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        SendResult<MessageRequest> sendResultWithMessage = sqsTemplate.send(QUEUE_NAME, new MyMessage(messageRequest));
        log.info("sendResultWithMessage =>{}", sendResultWithMessage.toString());
        return "New message published successfully!";
    }

    @GetMapping("/batch/{count}")
    public String sendBatchOfMessages(@PathVariable("count") int count) {
        MessageRequest messageRequest;
        Collection<Message<MessageRequest>> messages = new ArrayList<>();
        for (int i = 1; i < count; i++) {
            messageRequest = new MessageRequest(i, "Random message for the user " + i, "Random content for the user " + i, Timestamp.valueOf(LocalDateTime.now()));
            messages.add(new MyMessage(messageRequest));
        }
        log.info("sending {} messages", count);
        try {
            SendResult.Batch<MessageRequest> messageRequestBatch = sqsTemplate.sendMany(QUEUE_NAME, messages);
            log.info("Successful messages =>{}", messageRequestBatch.successful().toString());
            log.info("Failed messages =>{}", messageRequestBatch.failed().toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("{} messages sent successfully!", count);
        return "success!";
    }

}