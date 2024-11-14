package com.example.fulfillment.service;

import com.example.fulfillment.controller.exceptionHandler.Constant;
import com.example.fulfillment.controller.exceptionHandler.exceptions.ProductNotFoundException;
import com.example.fulfillment.entity.dto.ProductDTO;
import com.example.fulfillment.entity.enums.ProductStatus;
import com.example.fulfillment.entity.model.Product;
import com.example.fulfillment.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    private Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }

    public List<ProductDTO> getProductList() {
        return productRepository.findAll().stream().map(this::convertToProductDTO).toList();
    }

    public List<ProductDTO> getByStatus(ProductStatus status) {
        return productRepository.findByProductStatus(status).stream().map(this::convertToProductDTO).toList();
    }

    public ProductDTO getProduct(String productId, String centerId, BigDecimal value) {
        Product product = productRepository.findByProductIdAndFulfillmentCenterAndValue(productId, centerId, value).orElseThrow(
                () -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE));
        return convertToProductDTO(product);
    }

    @Transactional
    public ProductDTO updateOrAddProduct(ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);
        productRepository.save(product);
        return convertToProductDTO(product);
    }

    @Transactional
    public void deleteProduct(ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);
        productRepository.delete(product);
    }

    public BigDecimal getValue(String centerId) {
        List<Product> productList = productRepository.findByProductStatus(ProductStatus.Sellable);
        return productList.stream()
                .filter(product -> centerId == null || product.getFulfillmentCenter().equals(centerId))
                .map(Product::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
