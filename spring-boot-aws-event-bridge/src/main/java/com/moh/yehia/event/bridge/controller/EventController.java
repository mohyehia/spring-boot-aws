package com.moh.yehia.event.bridge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moh.yehia.event.bridge.config.AwsProperties;
import com.moh.yehia.event.bridge.model.EventBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class EventController {
    private final EventBridgeClient eventBridgeClient;
    private final ObjectMapper objectMapper;
    private final AwsProperties awsProperties;

    @PutMapping
    public Mono<String> addEvent(@RequestBody EventBody eventBody) throws JsonProcessingException {
        log.info("EventController :: addEvent :: start");
        eventBody = new EventBody(UUID.randomUUID().toString(), eventBody.name(), eventBody.age());
        PutEventsResponse putEventsResponse = eventBridgeClient.putEvents(PutEventsRequest.builder()
                .entries(PutEventsRequestEntry.builder()
                        .source(awsProperties.getSource()) // will be used at our custom-pattern
                        .detailType(awsProperties.getDetailType()) // will be used at our custom-pattern
                        .detail(objectMapper.writeValueAsString(eventBody)) // detail should be a valid json argument with key/value pair
                        .eventBusName(awsProperties.getEventBus())
                        .build()
                )
                .build()
        );
        log.info("putEventsResponse =>{}", putEventsResponse);
        log.info("EventController :: addEvent :: end");
        return Mono.just(putEventsResponse.toString());
    }

}
