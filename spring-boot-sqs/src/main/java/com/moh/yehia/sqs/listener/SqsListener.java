package com.moh.yehia.sqs.listener;

import com.moh.yehia.sqs.model.MessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
@Slf4j
public class SqsListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage) message;
        try {
            log.info("received message object =>{}", objectMessage.getObject());
            MessageRequest messageRequest = (MessageRequest) objectMessage.getObject();
            log.info("retrieved messageRequest =>{}", messageRequest);
        } catch (JMSException e) {
            log.error(e.getMessage(), e);
        }
    }
}
