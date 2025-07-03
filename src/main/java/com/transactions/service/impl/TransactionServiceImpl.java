package com.transactions.service.impl;

import com.transactions.config.security.UserPrincipal;
import com.transactions.model.Product;
import com.transactions.model.Transaction;
import com.transactions.model.User;
import com.transactions.payload.request.transaction.TransactionRequest;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.transaction.TransactionResponse;
import com.transactions.repository.ProductRepo;
import com.transactions.repository.TransactionRepo;
import com.transactions.repository.UserRepo;
import com.transactions.service.TransactionService;
import com.transactions.service.TransactionSpecs;
import com.transactions.util.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepo transactionRepo;
  private final UserRepo userRepo;
  private final ProductRepo productRepo;

  @Override
  public ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllTransaction(
      String name,
      LocalDateTime startDate,
      LocalDateTime endDate,
      List<PaymentStatus> paymentStatus,
      List<String> paymentMethod,
      UUID createdBy) {

    List<Transaction> transactionList =
        transactionRepo.findAll(
            TransactionSpecs.getTransactionSpec(
                name, startDate, endDate, paymentStatus, paymentMethod, createdBy));

    List<TransactionResponse> responseList =
        transactionList.stream().map(this::toTransactionResponse).toList();

    return ResponseEntity.ok(
        new ApiResponse<>(true, "Get All Transaction successfully", 0, responseList));
  }

  @Override
  public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(UUID id) {

    Optional<Transaction> transaction = transactionRepo.findById(id);
      return transaction.map(value -> ResponseEntity.ok(
              new ApiResponse<>(
                      true,
                      "Get Transaction By Id successfully",
                      0,
                      toTransactionResponse(value)))).orElseGet(() -> new ResponseEntity<>(
              new ApiResponse<>(false, "Get Transaction By Id Failed, Id Not Found", 404, null),
              HttpStatus.BAD_REQUEST));
  }

  @Override
  public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(
      TransactionRequest request, UserPrincipal userPrincipal) {

    Optional<User> user = userRepo.findById(request.getUserId());
    if (user.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Create Transaction Failed, User Not Found", 404, null),
          HttpStatus.BAD_REQUEST);
    }

    Transaction transaction = new Transaction();
    transaction.setUser(user.get());
    transaction.setNetAmountPaid(request.getNetAmountPaid());
    transaction.setTotalAmountPaid(request.getTotalAmountPaid());
    transaction.setTotalTaxPaid(request.getTotalTaxPaid());
    transaction.setTransactionTime(request.getTransactionTime());
    transaction.setPaymentStatus(request.getPaymentStatus());
    transaction.setPaymentMethod(request.getPaymentMethod());
    transaction.setCreatedBy(userPrincipal.getId());

    Optional<Product> productOptional = productRepo.findById(request.getProductId());
    if (productOptional.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Create Transaction Failed, Product Not Found", 404, null),
          HttpStatus.BAD_REQUEST);
    }
    transaction.setProduct(productOptional.get());
    transactionRepo.save(transaction);

    return ResponseEntity.ok(
        new ApiResponse<>(
            true, "Create Transaction successfully", 0, toTransactionResponse(transaction)));
  }

  @Override
  public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(
      UUID id, TransactionRequest request, User currentUser) {
    Optional<Transaction> transaction = transactionRepo.findById(id);
    if (transaction.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Delete Transaction By Id Failed, Id Not Found", 404, null),
          HttpStatus.BAD_REQUEST);
    }

    if (null != request.getNetAmountPaid())
      transaction.get().setNetAmountPaid(request.getNetAmountPaid());
    if (null != request.getTotalAmountPaid())
      transaction.get().setNetAmountPaid(request.getTotalAmountPaid());
    if (null != request.getTotalTaxPaid())
      transaction.get().setNetAmountPaid(request.getTotalTaxPaid());
    if (null != request.getTransactionTime())
      transaction.get().setTransactionTime(request.getTransactionTime());
    if (null != request.getPaymentStatus())
      transaction.get().setPaymentStatus(request.getPaymentStatus());
    if (null != request.getPaymentMethod())
      transaction.get().setPaymentMethod(request.getPaymentMethod());
    if (null != request.getProductId()) {
      Optional<Product> productOptional = productRepo.findById(id);
      productOptional.ifPresent(product -> transaction.get().setProduct(product));
    }

    Transaction transactionData = transactionRepo.save(transaction.get());
    return ResponseEntity.ok(
        new ApiResponse<>(
            true,
            "Update Transaction By Id successfully",
            0,
            toTransactionResponse(transactionData)));
  }

  @Override
  public ResponseEntity<ApiResponse<TransactionResponse>> deleteTransaction(UUID id) {

    Optional<Transaction> transaction = transactionRepo.findById(id);
    if (transaction.isEmpty()) {
      return new ResponseEntity<>(
          new ApiResponse<>(false, "Delete Transaction By Id Failed, Id Not Found", 404, null),
          HttpStatus.BAD_REQUEST);
    }

    transactionRepo.delete(transaction.get());
    return ResponseEntity.ok(
        new ApiResponse<>(true, "Delete Transaction By Id successfully", 0, null));
  }

  @Override
  public ResponseEntity<ApiResponse<Map<String, Object>>> getReportSummary(
      LocalDateTime start, LocalDateTime end, UUID userId) {

    Map<String, Object> reportSummary = new LinkedHashMap<>();

    if (start != null && end != null) {
      BigDecimal totalByCustomerBetween =
          transactionRepo.totalAmountBetweenTwoDates(start, end, userId);
      reportSummary.put(
          "totalByCustomerBetweenDates",
          totalByCustomerBetween != null ? totalByCustomerBetween : BigDecimal.ZERO);
    }

    BigDecimal totalByCustomer = transactionRepo.total(userId);
    reportSummary.put(
        "totalTransaction", totalByCustomer != null ? totalByCustomer : BigDecimal.ZERO);

    List<Object[]> taxSums = transactionRepo.totalPerTax(userId);
    Map<String, BigDecimal> taxReport = new HashMap<>();
    for (Object[] row : taxSums) {
      taxReport.put((String) row[0], (BigDecimal) row[1]);
    }
    reportSummary.put("totalPerTax", taxReport);

    List<Object[]> productSums = transactionRepo.totalPerProduct(userId);
    Map<String, BigDecimal> productReport = new HashMap<>();
    for (Object[] row : productSums) {
      productReport.put((String) row[0], (BigDecimal) row[1]);
    }
    reportSummary.put("totalPerProduct", productReport);

    return ResponseEntity.ok(
        new ApiResponse<>(true, "Get Report Summary Transaction successfully", 0, reportSummary));
  }

  private TransactionResponse toTransactionResponse(Transaction transaction) {

    return TransactionResponse.builder()
        .id(transaction.getId())
        .userId(transaction.getUser().getId())
        .netAmountPaid(transaction.getNetAmountPaid())
        .totalAmountPaid(transaction.getTotalAmountPaid())
        .totalTaxPaid(transaction.getTotalTaxPaid())
        .transactionTime(transaction.getTransactionTime())
        .paymentStatus(transaction.getPaymentStatus())
        .paymentMethod(transaction.getPaymentMethod())
        .createdBy(transaction.getCreatedBy())
        .productId(transaction.getProduct().getId())
        .productName(transaction.getProduct().getName())
        .build();
  }
}
