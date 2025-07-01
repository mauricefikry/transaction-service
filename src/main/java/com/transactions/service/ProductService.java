package com.transactions.service;

import com.transactions.payload.request.ProductRequest;
import com.transactions.payload.request.TaxRequest;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.product.ProductResponse;
import com.transactions.payload.response.product.TaxResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProduct();
    ResponseEntity<ApiResponse<ProductResponse>> getProductById(UUID id);
    ResponseEntity<ApiResponse<ProductResponse>> createProductById(ProductRequest request);
    ResponseEntity<ApiResponse<ProductResponse>> updateProductById(UUID id, ProductRequest request);
    ResponseEntity<ApiResponse<ProductResponse>> deleteProductById(UUID id);
    ResponseEntity<ApiResponse<TaxResponse>> createTax(TaxRequest request);
    ResponseEntity<ApiResponse<List<TaxResponse>>> getAllTax();
}
