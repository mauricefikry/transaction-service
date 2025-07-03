package com.transactions.controller;

import com.transactions.payload.request.RoleRequest;
import com.transactions.payload.request.UserRequest;
import com.transactions.payload.response.auth.RoleResponse;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.payload.response.user.UserResponse;
import com.transactions.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping(value = "/list")
  public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {
    return userService.getAllUser();
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
    return userService.getUserById(id);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ApiResponse<UserResponse>> updateUserById(
      @PathVariable UUID id, @RequestBody UserRequest request) {
    return userService.updateUserById(id, request);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable UUID id) {
    return userService.deleteUserById(id);
  }

  @PostMapping(value = "/role")
  public ResponseEntity<ApiResponse<RoleResponse>> getAllRole(@RequestBody RoleRequest request) {
    return userService.createRole(request);
  }

  @GetMapping(value = "/role/list")
  public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRole() {
    return userService.getAllRole();
  }
}
