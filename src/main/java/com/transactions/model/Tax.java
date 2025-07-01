package com.transactions.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tax")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tax {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal percentage;
}
