package com.moh.yehia.dynamodb.model.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;

    private CategoryRequest category;
}
