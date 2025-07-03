package com.transactions.payload.response.auth;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {

  private UUID id;
  private String name;
}
