package com.transactions.controller;

import com.transactions.config.security.UserPrincipal;
import com.transactions.model.User;
import com.transactions.payload.request.transaction.TransactionRequest;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.transaction.TransactionResponse;
import com.transactions.service.TransactionService;
import com.transactions.util.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> create(
            @RequestBody TransactionRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        return transactionService.createTransaction(request, currentUser);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getById(
            @PathVariable UUID id
    ) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(
            @PathVariable UUID id,
            @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        return transactionService.updateTransaction(id, request, currentUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> deleteTransaction(
            @PathVariable UUID id
    ) {
        return transactionService.deleteTransaction(id);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllTransactions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<PaymentStatus> paymentStatus,
            @RequestParam(required = false) List<String> paymentMethod,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) UUID createdBy
    ) {
        return transactionService.getAllTransaction(
                name, startDate, endDate, paymentStatus, paymentMethod, createdBy
        );
    }

    @GetMapping(value = "/report")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getReport(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = true) UUID userId
    ) {
        return transactionService.getReportSummary(startDate, endDate, userId);
    }
}
