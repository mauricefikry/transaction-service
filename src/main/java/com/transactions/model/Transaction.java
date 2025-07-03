package com.transactions.model;

import com.transactions.util.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {

  @Id @GeneratedValue private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "net_amount_paid")
  private BigDecimal netAmountPaid;

  @Column(name = "total_amount_paid")
  private BigDecimal totalAmountPaid;

  @Column(name = "total_tax_paid")
  private BigDecimal totalTaxPaid;

  @Column(name = "transaction_time")
  private LocalDateTime transactionTime;

  @Column(name = "payment_status")
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(name = "payment_method")
  private String paymentMethod;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
