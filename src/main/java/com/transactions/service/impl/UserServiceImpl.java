package com.transactions.service.impl;

import com.transactions.model.Role;
import com.transactions.model.User;
import com.transactions.payload.request.RoleRequest;
import com.transactions.payload.request.UserRequest;
import com.transactions.payload.response.auth.RoleResponse;
import com.transactions.payload.response.user.UserResponse;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.repository.RoleRepo;
import com.transactions.repository.UserRepo;
import com.transactions.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;


    @Override
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {
        List<User> users = userRepo.findAll();

        List<UserResponse> responseList = users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Get All User successfully",
                        0,
                        responseList
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(UUID id) {

        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "User Not Found",
                            404,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Get User By Id successfully",
                        0,
                        toUserResponse(user.get())
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(UUID id, UserRequest request) {

        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "Update User Failed, User Not Found",
                            404,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }

        if (null != request.getEmail()) user.get().setEmail(request.getEmail());
        if (null != request.getName()) user.get().setName(request.getName());
        if (null != request.getUsername()) user.get().setUsername(request.getUsername());
        if (null != request.getBirthdate()) user.get().setBirthdate(request.getBirthdate());
        if (null != request.getBirthplace()) user.get().setBirthplace(request.getBirthplace());
        if (null != request.getIsDeleted()) user.get().setIsDeleted(request.getIsDeleted());

        user.get().setUpdatedAt(LocalDateTime.now());
        userRepo.save(user.get());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Update User successfully",
                        0,
                        toUserResponse(user.get())
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(UUID id) {

        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "Delete User Failed, User Not Found",
                            404,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }

        user.get().setIsDeleted(true);
        userRepo.save(user.get());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Delete User successfully",
                        0,
                        toUserResponse(user.get())
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(RoleRequest request) {

        Role role = new Role();
        role.setName(request.getName());

        roleRepo.save(role);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Create Role successfully",
                        0,
                        toRoleResponse(role)
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRole() {

        List<Role> roles = roleRepo.findAll();

        List<RoleResponse> responseList = roles.stream()
                .map(this::toRoleResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Get All Role successfully",
                        0,
                        responseList
                )
        );
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .birthplace(user.getBirthplace())
                .isDeleted(user.getIsDeleted())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .updatedAt(user.getUpdatedAt())
                .updatedBy(user.getUpdatedBy())
                .build();
    }

    private RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}
