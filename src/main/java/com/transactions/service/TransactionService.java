package com.transactions.service;

import com.transactions.config.security.UserPrincipal;
import com.transactions.model.User;
import com.transactions.payload.request.transaction.TransactionRequest;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.transaction.TransactionResponse;
import com.transactions.util.PaymentStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface TransactionService {

  ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllTransaction(
      String name,
      LocalDateTime start,
      LocalDateTime end,
      List<PaymentStatus> paymentStatus,
      List<String> paymentMethod,
      UUID createdBy);

  ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(UUID id);

  ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(
      TransactionRequest request, UserPrincipal userPrincipal);

  ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(
      UUID id, TransactionRequest request, User currentUser);

  ResponseEntity<ApiResponse<TransactionResponse>> deleteTransaction(UUID id);

  ResponseEntity<ApiResponse<Map<String, Object>>> getReportSummary(
      LocalDateTime start, LocalDateTime end, UUID userId);
}
