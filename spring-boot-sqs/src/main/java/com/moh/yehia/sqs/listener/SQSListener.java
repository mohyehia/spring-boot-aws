package com.moh.yehia.sqs.listener;

import com.moh.yehia.sqs.model.MessageRequest;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SQSListener {
    @SqsListener("aws-sqs-queue")
    public void listen(Message<MessageRequest> message) {
        log.info("Received new message with payload =>{}", message.getPayload());
        log.info("Received new message with headers =>{}", message.getHeaders().toString());
    }
}
