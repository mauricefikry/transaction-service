package com.transactions.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserRequest {

  private String name;
  private String username;
  private String email;

  @Past
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate birthdate;

  private String birthplace;
  private Boolean isDeleted;
}
