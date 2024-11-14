package com.example.fulfillment.service;


import com.example.fulfillment.controller.exceptionHandler.Constant;
import com.example.fulfillment.controller.exceptionHandler.exceptions.ProductNotFoundException;
import com.example.fulfillment.entity.dto.ProductDTO;
import com.example.fulfillment.entity.enums.ProductStatus;
import com.example.fulfillment.entity.model.Product;
import com.example.fulfillment.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetProductList() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductId("P001");
        product1.setProductStatus(ProductStatus.Sellable);
        product1.setFulfillmentCenter("Center1");
        product1.setQuantity(10L);
        product1.setValue(BigDecimal.valueOf(100));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductId("P002");
        product2.setProductStatus(ProductStatus.Unfulfillable);
        product2.setFulfillmentCenter("Center2");
        product2.setQuantity(0L);
        product2.setValue(BigDecimal.valueOf(0));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDTO> productList = productService.getProductList();

        assertEquals(2, productList.size());
        assertEquals("P001", productList.get(0).getProductId());
        assertEquals(ProductStatus.Sellable, productList.get(0).getProductStatus());
        assertEquals("Center1", productList.get(0).getFulfillmentCenter());
        assertEquals(10L, productList.get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(100), productList.get(0).getValue());

        assertEquals("P002", productList.get(1).getProductId());
        assertEquals(ProductStatus.Unfulfillable, productList.get(1).getProductStatus());
        assertEquals("Center2", productList.get(1).getFulfillmentCenter());
        assertEquals(0L, productList.get(1).getQuantity());
        assertEquals(BigDecimal.valueOf(0), productList.get(1).getValue());
    }

    @Test
    void testGetByStatus() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductStatus(ProductStatus.Sellable);

        when(productRepository.findByProductStatus(ProductStatus.Sellable)).thenReturn(Arrays.asList(product1));

        List<ProductDTO> productList = productService.getByStatus(ProductStatus.Sellable);

        assertEquals(1, productList.size());
        assertEquals(ProductStatus.Sellable, productList.get(0).getProductStatus());
    }

    @Test
    void testGetProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProductId("P001");
        product.setFulfillmentCenter("Center1");
        product.setValue(BigDecimal.valueOf(100));

        String productId = "P001";
        String centerId = "Center1";
        BigDecimal value = BigDecimal.valueOf(100);

        when(productRepository.findByProductIdAndFulfillmentCenterAndValue(productId, centerId, value)).thenReturn(Optional.of(product));

        ProductDTO productDTO = productService.getProduct(productId, centerId, value);

        assertEquals("P001", productDTO.getProductId());
        assertEquals("Center1", productDTO.getFulfillmentCenter());
        assertEquals(BigDecimal.valueOf(100), productDTO.getValue());
    }

    @Test
    void testGetProductNotFound() {
        String productId = "P001";
        String centerId = "Center1";
        BigDecimal value = BigDecimal.valueOf(100);

        when(productRepository.findByProductIdAndFulfillmentCenterAndValue(productId, centerId, value)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProduct(productId, centerId, value);
        });

        assertEquals(Constant.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void testUpdateOrAddProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId("P001");
        productDTO.setProductStatus(ProductStatus.Sellable);
        productDTO.setFulfillmentCenter("Center1");
        productDTO.setQuantity(10L);
        productDTO.setValue(BigDecimal.valueOf(100));

        Product product = new Product();
        product.setProductId("P001");
        product.setProductStatus(ProductStatus.Sellable);
        product.setFulfillmentCenter("Center1");
        product.setQuantity(10L);
        product.setValue(BigDecimal.valueOf(100));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.updateOrAddProduct(productDTO);

        assertEquals("P001", result.getProductId());
        assertEquals(ProductStatus.Sellable, result.getProductStatus());
        assertEquals("Center1", result.getFulfillmentCenter());
        assertEquals(10L, result.getQuantity());
        assertEquals(BigDecimal.valueOf(100), result.getValue());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        Product product = new Product();
        product.setId(1L);
        product.setProductId("P001");

        productService.deleteProduct(productDTO);

        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void testGetValue() {
        Product product1 = new Product();
        product1.setValue(BigDecimal.valueOf(100));
        product1.setProductStatus(ProductStatus.Sellable);
        product1.setFulfillmentCenter("Center1");

        Product product2 = new Product();
        product2.setValue(BigDecimal.valueOf(200));
        product2.setProductStatus(ProductStatus.Sellable);
        product2.setFulfillmentCenter("Center2");

        when(productRepository.findByProductStatus(ProductStatus.Sellable)).thenReturn(Arrays.asList(product1, product2));

        BigDecimal totalValue = productService.getValue("Center1");

        assertEquals(BigDecimal.valueOf(100), totalValue);
    }
}