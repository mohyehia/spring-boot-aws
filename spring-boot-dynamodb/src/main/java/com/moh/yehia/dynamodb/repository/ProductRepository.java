package com.moh.yehia.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.moh.yehia.dynamodb.model.entity.Category;
import com.moh.yehia.dynamodb.model.entity.Product;
import com.moh.yehia.dynamodb.model.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProductRepository {
    private final DynamoDBMapper dynamoDbMapper;

    public List<Product> findAll() {
        PaginatedScanList<Product> paginatedScanList = dynamoDbMapper.scan(Product.class, new DynamoDBScanExpression());
        log.info("paginatedScanList =>{}", paginatedScanList.size());
        return new ArrayList<>(paginatedScanList);
    }

    public Product save(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(new Category(UUID.randomUUID().toString(), productRequest.getCategory().getName()));
        dynamoDbMapper.save(product);
        return product;
    }

    public Product findById(String productId) {
        return dynamoDbMapper.load(Product.class, productId);
    }

    public void deleteById(String productId) {
        Product product = dynamoDbMapper.load(Product.class, productId);
        if (product != null) {
            dynamoDbMapper.delete(product);
        }
    }

    public void update(String productId, ProductRequest productRequest) {
        Product product = new Product();
        product.setId(productId);
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        if (productRequest.getCategory() != null) {
            product.setCategory(new Category(UUID.randomUUID().toString(), productRequest.getCategory().getName()));
        }
        dynamoDbMapper.save(product, productUpdateExpression(product));
    }

    private DynamoDBSaveExpression productUpdateExpression(Product product) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id", new ExpectedAttributeValue(new AttributeValue().withS(product.getId())));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }
}
