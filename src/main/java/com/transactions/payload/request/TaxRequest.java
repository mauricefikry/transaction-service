package com.transactions.payload.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TaxRequest {

  private String name;
  private BigDecimal percentage;
}
