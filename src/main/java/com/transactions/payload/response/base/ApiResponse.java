package com.transactions.payload.response.base;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

  private Boolean status;
  private String message;
  private int errorCode;
  private Instant timestamp;
  private T data;

  public ApiResponse(Boolean status, String message, int errorCode, T data) {
    this.status = status;
    this.message = message;
    this.errorCode = errorCode;
    this.timestamp = Instant.now();
    this.data = data;
  }
}
