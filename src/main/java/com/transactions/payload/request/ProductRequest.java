package com.transactions.payload.request;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductRequest {

  private String name;
  private BigDecimal price;
  private Set<UUID> taxIds;
}
