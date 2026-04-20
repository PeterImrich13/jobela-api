package com.jobela.jobela_api.user.service;

import com.jobela.jobela_api.common.enums.UserRole;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.InvalidPasswordException;
import com.jobela.jobela_api.common.exception.UserAlreadyExistsException;
import com.jobela.jobela_api.common.exception.UserNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import com.jobela.jobela_api.user.dto.request.ChangePasswordRequest;
import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.request.UpdateUserRequest;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.mapper.UserMapper;
import com.jobela.jobela_api.user.repository.UserRepository;
import com.jobela.jobela_api.user.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private StringMapperHelper stringMapperHelper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldCreateUserSuccessfully() {
        var request = new CreateUserRequest("test@mail.com", "password123", UserRole.CANDIDATE);

        when(stringMapperHelper.clean("test@mail.com")).thenReturn("test@mail.com");
        when(userRepository.existsByEmailIgnoreCase("test@mail.com")).thenReturn(false);

        var user = User.builder()
                .email("test@mail.com")
                .password("password123")
                .role(UserRole.CANDIDATE)
                .build();

        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(
                new UserResponse(1L, "test@mail.com", UserRole.CANDIDATE, true, null)
        );

        var response = userService.createUser(request);

        assertThat(response.email()).isEqualTo("test@mail.com");
        verify(userRepository).save(user);
    }

    @Test
    void createUser_shouldThrowWhenEmailAlreadyExists() {
        var request = new CreateUserRequest("test@mail.com", "password123", UserRole.CANDIDATE);

        when(stringMapperHelper.clean("test@mail.com")).thenReturn("test@mail.com");
        when(userRepository.existsByEmailIgnoreCase("test@mail.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void createUser_shouldThrowWhenEmailIsBlank() {
        var request = new CreateUserRequest("   ", "password123", UserRole.CANDIDATE);

        when(stringMapperHelper.clean("   ")).thenReturn("");

        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void getUser_shouldThrownWhenUserNotFoud() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateUser_shouldThrownByEmailAlreadyExists() {
        var user = User.builder()
                .id(1L)
                .email("old@mail.com")
                .build();

        var request = new UpdateUserRequest("new@mail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(stringMapperHelper.clean("new@mail.com")).thenReturn("new@mail.com");
        when(userRepository.existsByEmailIgnoreCase("new@mail.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(1L, request))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void changePassword_shouldChangePasswordSuccessfully() {
        var user = User.builder()
                .id(1L)
                .password("oldPass")
                .build();

        var request = new ChangePasswordRequest("oldPass", "newPass123", "newPass123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.changePassword(1L, request);

        assertThat(user.getPassword()).isEqualTo("newPass123");
        verify(userRepository).save(user);
    }

    @Test
    void changedPassword_shouldThrownWhenCurrentPasswordIsWrong() {
        var user = User.builder()
                .id(1L)
                .password("oldPass")
                .build();

        var request = new ChangePasswordRequest("wrong", "newPass123", "newPass123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.changePassword(1L, request))
                .isInstanceOf(InvalidPasswordException.class);
    }
}
