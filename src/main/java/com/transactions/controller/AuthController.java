package com.transactions.controller;

import com.transactions.payload.request.LoginRequest;
import com.transactions.payload.request.SignUpRequest;
import com.transactions.payload.response.auth.LoginResponse;
import com.transactions.payload.response.auth.SignUpResponse;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(value = "/signin")
  public ResponseEntity<ApiResponse<LoginResponse>> signIn(@RequestBody LoginRequest request) {

    return authService.signIn(request);
  }

  @PostMapping(value = "/signup")
  public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@RequestBody SignUpRequest request) {
    return authService.signUp(request);
  }
}
