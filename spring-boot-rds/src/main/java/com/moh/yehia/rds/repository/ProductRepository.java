package com.moh.yehia.rds.repository;

import com.moh.yehia.rds.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
