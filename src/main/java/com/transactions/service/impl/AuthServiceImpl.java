package com.transactions.service.impl;

import com.transactions.config.security.CustomUserDetailsService;
import com.transactions.model.Role;
import com.transactions.model.User;
import com.transactions.payload.request.LoginRequest;
import com.transactions.payload.request.SignUpRequest;
import com.transactions.payload.response.auth.LoginResponse;
import com.transactions.payload.response.auth.SignUpResponse;
import com.transactions.payload.response.base.ApiResponse;
import com.transactions.repository.RoleRepo;
import com.transactions.repository.UserRepo;
import com.transactions.service.AuthService;
import com.transactions.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> signIn(LoginRequest request) {

        Optional<User> user =
                userRepo.findByUsernameOrEmail(
                        request.getUsernameOrEmail().toUpperCase(),
                        request.getUsernameOrEmail().toUpperCase()
                );

        if (user.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "User not exist",
                            400,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }

        boolean exist = passwordEncoder.matches(
                request.getPassword().replaceAll("\\s", ""),
                user.get().getPassword()
        );
        if (!exist) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "Password Wrong",
                            400,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }


        if (Boolean.TRUE.equals(user.get().getIsDeleted())) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "User Not Found, User deleted",
                            400,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.get().getUsername());
        String token = jwtUtil.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User Sign In successfully",
                        0,
                        loginResponse
                )
        );
    }

    @Override
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(SignUpRequest request) {

        if (userRepo.existsByEmail(request.getEmail().toUpperCase())) {
            return new ResponseEntity<>(
                    new ApiResponse<>(
                            false,
                            "Email is already in use",
                            400,
                            null
                    ), HttpStatus.BAD_REQUEST
            );
        }

        User user = new User();
        user.setUsername(request.getUsername().toUpperCase());
        user.setEmail(request.getEmail().toUpperCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> bodyRoles = new HashSet<>();
        if (null != request.getRoles()) {
            List<Role> roles = roleRepo.findAllByName(request.getRoles());
            bodyRoles.addAll(roles);
        } else {
            Optional<Role> userRole = roleRepo.findByName("ROLE_USER");
            userRole.ifPresent(bodyRoles::add);
        }


        user.setRoles(bodyRoles);
        userRepo.save(user);

        List<String> roleNames = bodyRoles.stream().map(Role::getName).toList();

        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setEmail(user.getEmail());
        signUpResponse.setUsername(user.getUsername());
        signUpResponse.setCreatedAt(user.getCreatedAt());
        signUpResponse.setRoles(roleNames);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User registered successfully",
                        0,
                        signUpResponse
                )
        );
    }
}
