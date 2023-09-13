package com.moh.yehia.sqs.model;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyMessage implements Message<MessageRequest> {
    private final MessageRequest messageRequest;

    public MyMessage(MessageRequest messageRequest) {
        this.messageRequest = messageRequest;
    }

    @Override
    public MessageRequest getPayload() {
        return messageRequest;
    }

    @Override
    public MessageHeaders getHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("my-custom-header", UUID.randomUUID().toString());
        return new MessageHeaders(headers);
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "messageRequest=" + messageRequest +
                '}';
    }
}