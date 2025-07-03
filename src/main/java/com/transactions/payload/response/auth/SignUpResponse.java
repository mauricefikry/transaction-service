package com.transactions.payload.response.auth;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class SignUpResponse {

  private UUID id;
  private LocalDateTime createdAt;
  private String email;
  private String username;
  private List<String> roles;
}
