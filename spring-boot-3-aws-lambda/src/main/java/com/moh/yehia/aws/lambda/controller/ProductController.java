package com.moh.yehia.aws.lambda.controller;

import com.moh.yehia.aws.lambda.model.ProductDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    List<ProductDTO> products = new ArrayList<>();

    @GetMapping
    public List<ProductDTO> findProducts() {
        return products;
    }

    @GetMapping("/{productId}")
    public ProductDTO findById(@PathVariable("productId") String productId) {
        return products.stream()
                .filter(productDTO -> productDTO.id().equals(productId))
                .findFirst()
                .orElse(new ProductDTO("0000-0000-0000-0000", "No product info available!", "No product info available!"));
    }

    @PostMapping
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        String id = UUID.randomUUID().toString();
        products.add(new ProductDTO(id, productDTO.name(), productDTO.description()));
        return new ProductDTO(id, productDTO.name(), productDTO.description());
    }

    @DeleteMapping("/{productId}")
    public void deleteId(@PathVariable("productId") String productId) {
        if (!products.isEmpty()) {
            products.removeIf(productDTO -> productDTO.id().equals(productId));
        }
    }
}
