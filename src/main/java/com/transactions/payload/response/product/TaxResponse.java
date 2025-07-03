package com.transactions.payload.response.product;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaxResponse {

  private UUID id;
  private String name;
  private BigDecimal percentage;
}
