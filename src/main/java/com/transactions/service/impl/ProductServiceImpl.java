package com.transactions.service.impl;

import com.transactions.model.Product;
import com.transactions.model.Tax;
import com.transactions.payload.request.ProductRequest;
import com.transactions.payload.request.TaxRequest;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.product.ProductResponse;
import com.transactions.payload.response.product.TaxResponse;
import com.transactions.repository.ProductRepo;
import com.transactions.repository.TaxRepo;
import com.transactions.service.ProductService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepo productRepo;
  private final TaxRepo taxRepo;

  @Override
  public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProduct() {
    List<Product> products = productRepo.findAll();

    List<ProductResponse> responseList =
        products.stream().map(this::toProductResponse).collect(Collectors.toList());

    return ResponseEntity.ok(
        new ApiResponse<>(true, "Get All Products successfully", 0, responseList));
  }

  @Override
  public ResponseEntity<ApiResponse<ProductResponse>> getProductById(UUID id) {
    Optional<Product> product = productRepo.findById(id);
    if (product.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Product Not Found", 404, null), HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok(
        new ApiResponse<>(
            true, "Get User By Id successfully", 0, toProductResponse(product.get())));
  }

  @Override
  public ResponseEntity<ApiResponse<ProductResponse>> createProductById(ProductRequest request) {

    Product product = new Product();
    product.setName(request.getName());
    product.setPrice(request.getPrice());
    if (request.getTaxIds() != null) {
      product.setTaxes(new HashSet<>(taxRepo.findAllById(request.getTaxIds())));
    }
    productRepo.save(product);

    return ResponseEntity.ok(
        new ApiResponse<>(true, "Create Product successfully", 0, toProductResponse(product)));
  }

  @Override
  public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(
      UUID id, ProductRequest request) {
    Optional<Product> product = productRepo.findById(id);
    if (product.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Update Product Failed, Product Not Found", 404, null),
          HttpStatus.BAD_REQUEST);
    }

    if (null != request.getName()) product.get().setName(request.getName());
    if (null != request.getPrice()) product.get().setPrice(request.getPrice());
    if (request.getTaxIds() != null) {
      product.get().setTaxes(new HashSet<>(taxRepo.findAllById(request.getTaxIds())));
    }

    product.get().setUpdatedAt(LocalDateTime.now());
    productRepo.save(product.get());

    return ResponseEntity.ok(
        new ApiResponse<>(
            true, "Update Product successfully", 0, toProductResponse(product.get())));
  }

  @Override
  public ResponseEntity<ApiResponse<ProductResponse>> deleteProductById(UUID id) {
    Optional<Product> product = productRepo.findById(id);
    if (product.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Delete Product Failed, Product Not Found", 404, null),
          HttpStatus.BAD_REQUEST);
    }

    productRepo.delete(product.get());

    return ResponseEntity.ok(new ApiResponse<>(true, "Delete Product successfully", 0, null));
  }

  @Override
  public ResponseEntity<ApiResponse<TaxResponse>> createTax(TaxRequest request) {

    Tax tax = new Tax();
    tax.setName(request.getName());
    tax.setPercentage(request.getPercentage());

    taxRepo.save(tax);
    return ResponseEntity.ok(
        new ApiResponse<>(true, "Create tax successfully", 0, toTaxResponse(tax)));
  }

  @Override
  public ResponseEntity<ApiResponse<List<TaxResponse>>> getAllTax() {

    List<Tax> taxes = taxRepo.findAll();
    List<TaxResponse> responseList =
        taxes.stream().map(this::toTaxResponse).collect(Collectors.toList());

    return ResponseEntity.ok(new ApiResponse<>(true, "Get All Tax successfully", 0, responseList));
  }

  private ProductResponse toProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .taxes(
            product.getTaxes().stream()
                .map(
                    t ->
                        TaxResponse.builder()
                            .id(t.getId())
                            .name(t.getName())
                            .percentage(t.getPercentage())
                            .build())
                .collect(Collectors.toSet()))
        .build();
  }

  private TaxResponse toTaxResponse(Tax tax) {
    return TaxResponse.builder()
        .id(tax.getId())
        .name(tax.getName())
        .percentage(tax.getPercentage())
        .build();
  }
}
