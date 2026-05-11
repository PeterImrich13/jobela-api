package com.jobela.jobela_api.user.controller;

import com.jobela.jobela_api.user.dto.request.ChangePasswordRequest;
import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.request.UpdateUserRequest;
import com.jobela.jobela_api.user.dto.response.UserResponse;
import com.jobela.jobela_api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

        var response = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {

        var response = userService.getUserById(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

         var responses = userService.getAllUsers(pageable);

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#userId, authentication)")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,@Valid @RequestBody UpdateUserRequest request) {

        var response = userService.updateUser(userId, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/password")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#userId, authentication)")
    public ResponseEntity<Void> changePassword(@PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(userId, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(#userId, authentication)")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {

        userService.deactivateUser(userId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId) {

        userService.activateUser(userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
