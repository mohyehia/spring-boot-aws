package com.moh.yehia.lambda.service.impl;

import com.moh.yehia.lambda.model.Product;
import com.moh.yehia.lambda.model.ProductRequest;
import com.moh.yehia.lambda.repository.ProductRepository;
import com.moh.yehia.lambda.service.design.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product save(ProductRequest productRequest) {
        return productRepository.save(productRequest);
    }
}
