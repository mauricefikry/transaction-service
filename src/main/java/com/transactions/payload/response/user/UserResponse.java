package com.transactions.payload.response.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

  private UUID id;
  private String username;
  private String name;
  private String email;
  private LocalDate birthdate;
  private String birthplace;
  private Boolean isDeleted;
  private UUID createdBy;
  private LocalDateTime createdAt;
  private UUID updatedBy;
  private LocalDateTime updatedAt;
}
