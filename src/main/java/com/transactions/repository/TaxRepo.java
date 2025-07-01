package com.transactions.repository;

import com.transactions.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaxRepo extends JpaRepository<Tax, UUID> {
}
