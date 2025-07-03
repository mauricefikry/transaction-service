package com.transactions.repository;

import com.transactions.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, UUID> {

  Optional<User> findByUsernameOrEmail(String username, String email);

  boolean existsByEmail(String email);
}
