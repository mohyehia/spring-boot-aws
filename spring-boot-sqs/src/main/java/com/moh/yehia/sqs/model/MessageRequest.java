package com.moh.yehia.sqs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest implements Serializable {
    private String userId;
    private String message;
    private String content;
    private Timestamp createdAt;
}
