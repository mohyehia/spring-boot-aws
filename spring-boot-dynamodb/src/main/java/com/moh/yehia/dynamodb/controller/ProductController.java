package com.moh.yehia.dynamodb.controller;

import com.moh.yehia.dynamodb.model.entity.Product;
import com.moh.yehia.dynamodb.model.request.ProductRequest;
import com.moh.yehia.dynamodb.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> findAll() {
        log.info("ProductController :: findAll :: start");
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") String productId) {
        log.info("ProductController :: findById :: start");
        return productRepository.findById(productId);
    }

    @PostMapping
    public Product save(@RequestBody ProductRequest productRequest) {
        log.info("ProductController :: save :: start");
        log.info("productRequest =>{}", productRequest);
        return productRepository.save(productRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String productId) {
        log.info("ProductController :: save :: start");
        productRepository.deleteById(productId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String productId, @RequestBody ProductRequest productRequest) {
        productRepository.update(productId, productRequest);
    }
}
