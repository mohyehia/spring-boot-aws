package com.moh.yehia.sqs.controller;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moh.yehia.sqs.model.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final AmazonSQSAsync amazonSQS;
    private final ObjectMapper objectMapper;
    private final JmsTemplate jmsTemplate;

    @PostMapping
    public String sendMessage(@RequestBody MessageRequest messageRequest) throws Exception {
        messageRequest.setUserId(UUID.randomUUID().toString());
        messageRequest.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("messageRequest =>{}", messageRequest);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(messageRequest);
            }
        });
        log.info("sent successfully!");
        return "Message sent successfully!";
    }
}
