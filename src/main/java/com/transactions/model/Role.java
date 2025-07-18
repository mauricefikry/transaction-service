package com.transactions.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "uuid", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;
}
