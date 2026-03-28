package com.jobela.jobela_api.user.service;

import com.jobela.jobela_api.common.exception.InvalidPaginationParameterException;
import com.jobela.jobela_api.common.exception.InvalidPasswordException;
import com.jobela.jobela_api.common.exception.UserAlreadyExistsException;
import com.jobela.jobela_api.common.exception.UserNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import com.jobela.jobela_api.user.dto.request.ChangePasswordRequest;
import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.request.UpdateUserRequest;
import com.jobela.jobela_api.user.dto.response.UserResponse;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.user.mapper.UserMapper;
import com.jobela.jobela_api.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final StringMapperHelper stringMapperHelper;

   @Override
    public UserResponse createUser(CreateUserRequest request) {
       var cleanedEmail = stringMapperHelper.clean(request.email());

       log.info("Creating user with email={}", cleanedEmail);

      if (cleanedEmail == null || cleanedEmail.isEmpty()) {
          throw new BadRequestException("Email cannot be blank");
       }
       if (userRepository.existsByEmailIgnoreCase(cleanedEmail)) {
           log.warn("User creation failed, email already exists: {}", cleanedEmail);
           throw new UserAlreadyExistsException("User with email already exists: " + cleanedEmail);
       }

       var user = userMapper.toEntity(request);

       var savedUser = userRepository.save(user);

       log.info("User created successfully with id={} and email={}", savedUser.getId(), savedUser.getEmail());
       return userMapper.toResponse(savedUser);
   }

   @Override
   @Transactional(readOnly = true)
   public UserResponse getUserById(Long userId) {
       log.info("Fetching user by id={}", userId);

       var user = getUserOrThrow(userId);

       return userMapper.toResponse(user);
   }

   @Override
   @Transactional(readOnly = true)
   public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String direction) {
       log.info("Fetching all users page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);

       validatePagingAndSorting(page, size, sortBy, direction);

       var sort = direction.equalsIgnoreCase("desc")
               ? Sort.by(sortBy).descending()
               : Sort.by(sortBy).ascending();

       var pageable = PageRequest.of(page, size, sort);

       return userRepository.findAll(pageable)
               .map(userMapper::toResponse);
   }

   @Override
   public UserResponse updateUser(Long userId, UpdateUserRequest request) {
       log.info("Updating user with id={}", userId);

       var user = getUserOrThrow(userId);

       var cleanedEmail = stringMapperHelper.clean(request.email());

       if (cleanedEmail == null || cleanedEmail.isEmpty()) {
           throw new BadRequestException("Email cannot be blank");
       }

       if (!user.getEmail().equalsIgnoreCase(cleanedEmail) && userRepository.existsByEmailIgnoreCase(cleanedEmail)) {
               throw new UserAlreadyExistsException("User with email already exists" + cleanedEmail);
       }

       userMapper.updateUserFromRequest(request, user);

       var updatedUser = userRepository.save(user);

       log.info("User updated successfully with id={}", updatedUser.getId());

       return userMapper.toResponse(updatedUser);
   }

   @Override
   public void deactivateUser(Long userId) {
       log.info("Deactivating user with id={}", userId);

       var user = getUserOrThrow(userId);

       user.setActive(false);
       userRepository.save(user);

       log.info("User deactivated successfully id={}", userId);
   }

   @Override
   public void activateUser(Long userId) {
       log.info("Activating user id={}", userId);

       var user = getUserOrThrow(userId);

       user.setActive(true);
       userRepository.save(user);

       log.info("User activated successfully id={}", userId);
   }

   @Override
   public void changePassword(Long userId, ChangePasswordRequest request) {
       log.info("Changing password for user with id={}", userId);

       var user = getUserOrThrow(userId);

       if (!user.getPassword().equals(request.currentPassword())) {
           log.warn("Password change failed, current password mismatch for user with id={}", userId);
           throw new InvalidPasswordException("Current password is incorrect");
       }
       if (!request.newPassword().equals(request.confirmPassword())) {
           log.warn("Password, change failed, new password and confirm password do not match for user, id={}", userId);
           throw new InvalidPasswordException("New password and confirm password do not match");
       }
       user.setPassword(request.newPassword());
       userRepository.save(user);

       log.info("Password changed successfully for user with id={}", userId);
   }

   @Override
    public void deleteUser(Long userId) {
       log.info("Deleting user id={}", userId);

       var user = getUserOrThrow(userId);

       userRepository.delete(user);

       log.info("User successfully deleted, id={}", userId);
   }

   private User getUserOrThrow(Long userId) {
       return userRepository.findById(userId)
               .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

   }

   private void validatePagingAndSorting(int page, int size, String sortBy, String direction) {
       if (page < 0) {
           throw new InvalidPaginationParameterException("Page must be greater than or equal to 0");
       }

       if (size <= 0 || size > 50) {
           throw new InvalidPaginationParameterException("Size must be between 1 and 50");
       }

       if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
           throw new InvalidPaginationParameterException("Direction must be either 'asc' or 'desc'");
       }

       var allowedSortFields = Set.of("id", "email", "createdAt", "updatedAt");

       if (!allowedSortFields.contains(sortBy)) {
           throw new InvalidPaginationParameterException("Invalid sortBy value. Allowed values: id, email, createdAt, updatedAt");
       }
   }
    }
