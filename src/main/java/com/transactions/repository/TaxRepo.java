package com.transactions.repository;

import com.transactions.model.Tax;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepo extends JpaRepository<Tax, UUID> {}
