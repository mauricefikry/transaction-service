package com.transactions.controller;

import com.transactions.payload.request.ProductRequest;
import com.transactions.payload.request.TaxRequest;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.product.ProductResponse;
import com.transactions.payload.response.product.TaxResponse;
import com.transactions.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/list")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProduct(
    ) {
        return productService.getAllProduct();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable UUID id
    ) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestBody ProductRequest request
    ) {
        return productService.createProductById(request);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(
            @PathVariable UUID id,
            @RequestBody ProductRequest request
    ) {
        return productService.updateProductById(id, request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProductById(
            @PathVariable UUID id
    ) {
        return productService.deleteProductById(id);
    }

    @PostMapping(value = "/tax")
    public ResponseEntity<ApiResponse<TaxResponse>> getAllTax(
            @RequestBody TaxRequest request
    ) {
        return productService.createTax(request);
    }

    @GetMapping(value = "/tax/list")
    public ResponseEntity<ApiResponse<List<TaxResponse>>> getAllTax(
    ) {
        return productService.getAllTax();
    }
}
