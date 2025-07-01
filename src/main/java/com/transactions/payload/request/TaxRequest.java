package com.transactions.payload.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TaxRequest {

    private String name;
    private BigDecimal percentage;
}
