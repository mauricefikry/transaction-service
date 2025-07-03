package com.transactions.service;

import com.transactions.payload.request.RoleRequest;
import com.transactions.payload.request.UserRequest;
import com.transactions.payload.response.auth.RoleResponse;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.user.UserResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser();

  ResponseEntity<ApiResponse<UserResponse>> getUserById(UUID id);

  ResponseEntity<ApiResponse<UserResponse>> updateUserById(UUID id, UserRequest request);

  ResponseEntity<ApiResponse<UserResponse>> deleteUserById(UUID id);

  ResponseEntity<ApiResponse<RoleResponse>> createRole(RoleRequest request);

  ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRole();
}
