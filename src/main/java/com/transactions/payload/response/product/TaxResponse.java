package com.transactions.payload.response.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class TaxResponse {

    private UUID id;
    private String name;
    private BigDecimal percentage;
}
