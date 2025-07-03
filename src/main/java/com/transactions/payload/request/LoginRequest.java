package com.transactions.payload.request;

import lombok.Data;

@Data
public class LoginRequest {

  private String usernameOrEmail;
  private String password;
}
