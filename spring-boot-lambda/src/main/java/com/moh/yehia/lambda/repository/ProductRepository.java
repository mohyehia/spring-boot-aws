package com.moh.yehia.lambda.repository;

import com.moh.yehia.lambda.model.Product;
import com.moh.yehia.lambda.model.ProductRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(String productId) {
        return products.stream().filter(product -> product.getId().equals(productId)).findFirst();
    }

    public Product save(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .categoryId(productRequest.getCategoryId())
                .stock(productRequest.getStock())
                .build();
        products.add(product);
        return product;
    }
}
