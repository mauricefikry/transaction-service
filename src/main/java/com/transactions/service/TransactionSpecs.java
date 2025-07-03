package com.transactions.service;

import com.transactions.model.Transaction;
import com.transactions.util.PaymentStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecs {

  public static Specification<Transaction> getTransactionSpec(
      String name,
      LocalDateTime startDate,
      LocalDateTime endDate,
      List<PaymentStatus> paymentStatus,
      List<String> paymentMethod,
      UUID createdBy) {

    Specification<Transaction> spec = (root, query, cb) -> cb.conjunction();
    if (startDate != null && endDate != null) {
      spec =
          spec.and(
              (root, query, cb) -> cb.between(root.get("transactionTime"), startDate, endDate));
    }

    if (name != null && !name.isBlank()) {
      spec =
          spec.and(
              (root, query, cb) ->
                  cb.like(cb.lower(root.get("user").get("name")), "%" + name.toLowerCase() + "%"));
    }

    if (paymentStatus != null && !paymentStatus.isEmpty()) {
      spec = spec.and((root, query, cb) -> root.get("paymentStatus").in(paymentStatus));
    }

    if (paymentMethod != null && !paymentMethod.isEmpty()) {
      spec = spec.and((root, query, cb) -> root.get("paymentMethod").in(paymentMethod));
    }

    if (createdBy != null) {
      spec = spec.and((root, query, cb) -> cb.equal(root.get("createdBy"), createdBy));
    }

    return spec;
  }
}
