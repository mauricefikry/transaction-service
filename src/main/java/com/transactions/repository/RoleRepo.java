package com.transactions.repository;

import com.transactions.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String roleName);

    @Query(value = "SELECT * FROM roles WHERE name IN (:names) ", nativeQuery = true)
    List<Role> findAllByName(@Param("names") Set<String> names);

    @Query(value = "SELECT * FROM roles WHERE name IN (:ids) ", nativeQuery = true)
    List<Role> findAllById(@Param("ids") Set<String> ids);

}
