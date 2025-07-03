package com.transactions.repository;

import com.transactions.model.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo
    extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {

  @Query(
      "SELECT SUM(t.totalAmountPaid) FROM Transaction t "
          + "WHERE t.user.id = :userId AND t.transactionTime BETWEEN :start AND :end")
  BigDecimal totalAmountBetweenTwoDates(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end,
      @Param("userId") UUID userId);

  @Query("SELECT SUM(t.totalAmountPaid) " + "FROM Transaction t")
  BigDecimal total(@Param("userId") UUID userId);

  @Query(
      "SELECT t.paymentMethod, SUM(t.totalTaxPaid) FROM Transaction t "
          + "WHERE t.user.id = :userId GROUP BY t.paymentMethod")
  List<Object[]> totalPerTax(@Param("userId") UUID userId);

  @Query(
      "SELECT t.product.name, SUM(t.totalAmountPaid) FROM Transaction t "
          + "WHERE t.user.id = :userId GROUP BY t.product.name")
  List<Object[]> totalPerProduct(@Param("userId") UUID userId);
}
