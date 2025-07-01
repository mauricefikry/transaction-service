package com.transactions.payload.response.transaction;

import com.transactions.util.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TransactionResponse {

    private UUID id;
    private UUID userId;
    private BigDecimal netAmountPaid;
    private BigDecimal totalAmountPaid;
    private BigDecimal totalTaxPaid;
    private LocalDateTime transactionTime;
    private PaymentStatus paymentStatus;
    private String paymentMethod;
    private UUID createdBy;
    private UUID productId;
    private String productName;
}
