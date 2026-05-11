package com.jobela.jobela_api.user.service;

import com.jobela.jobela_api.user.dto.request.ChangePasswordRequest;
import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.request.UpdateUserRequest;
import com.jobela.jobela_api.user.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
   UserResponse createUser(CreateUserRequest request);
   UserResponse getUserById(Long userId);
   Page<UserResponse> getAllUsers(Pageable pageable);
   UserResponse updateUser(Long userId, UpdateUserRequest request);
   void changePassword(Long userId, ChangePasswordRequest request);
   void deactivateUser(Long userId);
   void activateUser(Long userId);
   void deleteUser(Long userId);
}
