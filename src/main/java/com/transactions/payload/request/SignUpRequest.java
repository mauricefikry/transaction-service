package com.transactions.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class SignUpRequest {

  private String username;

  @NotBlank @NotNull private String email;

  @NotBlank @NotNull private String password;

  private Set<String> roles;
}
