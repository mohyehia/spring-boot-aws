package com.moh.yehia.rds.service.design;

import com.moh.yehia.rds.model.Product;
import com.moh.yehia.rds.model.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(long productId);

    Product save(ProductRequest productRequest);

    void delete(long productId);

    Product update(long productId, ProductRequest productRequest);
}
