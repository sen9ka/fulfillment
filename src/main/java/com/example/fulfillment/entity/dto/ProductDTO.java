package com.example.fulfillment.entity.dto;

import com.example.fulfillment.entity.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String productId;

    private ProductStatus productStatus;

    private String fulfillmentCenter;

    private Long quantity;

    private BigDecimal value;

}
