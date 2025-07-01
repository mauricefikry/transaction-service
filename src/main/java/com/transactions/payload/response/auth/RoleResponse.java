package com.transactions.payload.response.auth;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleResponse {

    private UUID id;
    private String name;
}
