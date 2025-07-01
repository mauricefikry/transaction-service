package com.transactions.payload.request;

import com.transactions.payload.response.product.TaxResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductRequest {

    private String name;
    private BigDecimal price;
    private Set<UUID> taxIds;
}
