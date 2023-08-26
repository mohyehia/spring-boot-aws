package com.moh.yehia.rds.service.impl;

import com.moh.yehia.rds.model.Product;
import com.moh.yehia.rds.model.ProductRequest;
import com.moh.yehia.rds.repository.ProductRepository;
import com.moh.yehia.rds.service.design.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Product findById(long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product save(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        return productRepository.save(product);
    }

    @Override
    public void delete(long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    @Transactional
    @Modifying
    public Product update(long productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return null;
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        return productRepository.save(product);
    }
}
