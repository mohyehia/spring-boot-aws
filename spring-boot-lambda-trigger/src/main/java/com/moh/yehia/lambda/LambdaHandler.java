package com.moh.yehia.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;

public class LambdaHandler implements RequestHandler<S3Event, String> {
    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("LambdaHandler :: handleRequest :: start");
        S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord = s3Event.getRecords().get(0);
        String bucketName = s3EventNotificationRecord.getS3().getBucket().getName();
        String fileName = s3EventNotificationRecord.getS3().getObject().getKey();
        String fileURL = s3EventNotificationRecord.getS3().getObject().getUrlDecodedKey();
        logger.log("BucketName =>" + bucketName);
        logger.log("FileName =>" + fileName);
        logger.log("fileURL =>" + fileURL);
        logger.log("LambdaHandler :: handleRequest :: end");
        return "Successfully read file from s3 bucket!";
    }
}
