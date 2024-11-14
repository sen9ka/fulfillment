package com.example.fulfillment.controller;

import com.example.fulfillment.entity.dto.ProductDTO;
import com.example.fulfillment.entity.enums.ProductStatus;
import com.example.fulfillment.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Контроллер для работы с продуктами")
public class ProductsController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Получение списка продуктов")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getProductList(), HttpStatus.OK);
    }

    @GetMapping("/{productId}/{centerId}/{value}")
    @Operation(summary = "Получение продукта по идентификатору, идентификатору центра и стоимости")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable String productId, @PathVariable String centerId, @PathVariable BigDecimal value
    ) {
        return new ResponseEntity<>(productService.getProduct(productId, centerId, value), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "Обновление или добавление продукта")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestBody ProductDTO productDTO
            ) {
        return new ResponseEntity<>(productService.updateOrAddProduct(productDTO), HttpStatus.OK);
    }

    @DeleteMapping()
    @Operation(summary = "Удаление продукта")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteClient(@RequestBody ProductDTO productDTO) {
        productService.deleteProduct(productDTO);
    }

    @GetMapping("/{status}")
    @Operation(summary = "Получение списка продуктов по статусу")
    public ResponseEntity<List<ProductDTO>> getByStatus(@PathVariable ProductStatus status) {
        return new ResponseEntity<>(productService.getByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/value")
    @Operation(summary = "Получение общего значения (value) всех продуктов с состоянием Sellable")
    public ResponseEntity<BigDecimal> getValue(
            @RequestParam(required = false) String centerId
    ) {
        return new ResponseEntity<>(productService.getValue(centerId), HttpStatus.OK);
    }

}
