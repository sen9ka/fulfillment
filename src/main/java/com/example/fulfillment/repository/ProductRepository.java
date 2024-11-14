package com.example.fulfillment.repository;

import com.example.fulfillment.entity.enums.ProductStatus;
import com.example.fulfillment.entity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductIdAndFulfillmentCenterAndValue(String productId, String fulfillmentCenter, BigDecimal value);

    List<Product> findByProductStatus(ProductStatus productStatus);
}
