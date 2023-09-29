package com.moh.yehia.lambda.service.design;

import com.moh.yehia.lambda.model.Product;
import com.moh.yehia.lambda.model.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(String productId);

    Product save(ProductRequest productRequest);
}
