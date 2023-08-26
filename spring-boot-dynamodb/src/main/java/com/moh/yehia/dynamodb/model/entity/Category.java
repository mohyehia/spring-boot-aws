package com.moh.yehia.dynamodb.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @DynamoDBAttribute
    private String id;

    @DynamoDBAttribute
    private String name;
}
