package com.transactions.service;

import com.transactions.payload.request.LoginRequest;
import com.transactions.payload.request.SignUpRequest;
import com.transactions.payload.response.auth.LoginResponse;
import com.transactions.payload.response.auth.SignUpResponse;
import com.transactions.payload.response.base.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse<LoginResponse>> signIn(LoginRequest request);
    ResponseEntity<ApiResponse<SignUpResponse>> signUp(SignUpRequest request);
}
