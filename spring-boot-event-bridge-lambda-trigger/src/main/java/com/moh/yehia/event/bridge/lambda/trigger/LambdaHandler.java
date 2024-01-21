package com.moh.yehia.event.bridge.lambda.trigger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;

import java.util.Map;

public class LambdaHandler implements RequestHandler<ScheduledEvent, String> {
    @Override
    public String handleRequest(ScheduledEvent scheduledEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("LambdaHandler :: handleRequest :: start");
        logger.log("scheduledEvent =>" + scheduledEvent);
        Map<String, Object> detail = scheduledEvent.getDetail();
        logger.log("User ID =>" + detail.getOrDefault("id", "0000-0000-0000-0000"));
        logger.log("User Name =>" + detail.getOrDefault("name", "No info available!"));
        logger.log("User Age =>" + detail.getOrDefault("age", "No info available!"));
        logger.log("LambdaHandler :: handleRequest :: end");
        return "Successfully read object from EventBridge!";
    }
}
