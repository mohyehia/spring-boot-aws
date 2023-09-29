package com.moh.yehia.lambda.resource;

import com.moh.yehia.lambda.model.Product;
import com.moh.yehia.lambda.model.ProductRequest;
import com.moh.yehia.lambda.service.design.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;

    @Bean
    public Supplier<List<Product>> findAll() {
        return productService::findAll;
    }

    @Bean
    public Function<String, Product> findById() {
        return productId -> productService.findById(productId.trim());
    }

    @Bean
    public Function<ProductRequest, Product> save() {
        return productService::save;
    }
}
