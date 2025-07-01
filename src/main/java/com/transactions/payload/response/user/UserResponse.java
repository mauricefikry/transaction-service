package com.transactions.payload.response.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
