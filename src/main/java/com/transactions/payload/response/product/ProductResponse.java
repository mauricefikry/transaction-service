package com.transactions.payload.response.product;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductResponse {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Set<TaxResponse> taxes;
}
