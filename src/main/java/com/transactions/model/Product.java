package com.transactions.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

  @Id @GeneratedValue private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private BigDecimal price;

  @ManyToMany
  @JoinTable(
      name = "product_taxes",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "tax_id"))
  private Set<Tax> taxes = new HashSet<>();
}
