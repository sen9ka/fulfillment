package com.example.fulfillment.entity.model;

import com.example.fulfillment.entity.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "fulfillment_center")
    private String fulfillmentCenter;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "value")
    private BigDecimal value;

}
