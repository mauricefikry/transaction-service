package com.transactions.payload.request.transaction;

import com.transactions.util.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class TransactionRequest {

  private UUID userId;
  private BigDecimal netAmountPaid;
  private BigDecimal totalAmountPaid;
  private BigDecimal totalTaxPaid;
  private LocalDateTime transactionTime;
  private PaymentStatus paymentStatus;
  private String paymentMethod;
  private UUID productId;
}
