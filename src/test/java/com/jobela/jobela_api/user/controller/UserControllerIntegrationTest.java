package com.jobela.jobela_api.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobela.jobela_api.common.enums.UserRole;
import com.jobela.jobela_api.config.PostgresTestContainerConfig;
import com.jobela.jobela_api.user.dto.request.ChangePasswordRequest;
import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.request.UpdateUserRequest;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class UserControllerIntegrationTest extends PostgresTestContainerConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        var request = new CreateUserRequest(
                "test@mail.com",
                "password123",
                UserRole.CANDIDATE
        );

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@mail.com"))
                .andExpect(jsonPath("$.role").value("CANDIDATE"))
                .andExpect(jsonPath("$.active").value(true));

        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        var savedUser = userRepository.save(
                User.builder()
                        .email("test@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .active(true)
                        .build()
        );

        mockMvc.perform(get("/api/users/{userId}", savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value("test@mail.com"))
                .andExpect(jsonPath("$.role").value("CANDIDATE"));
    }

    @Test
    void getAllUsers_shouldReturnPageUsers() throws Exception {
        userRepository.save(
                User.builder()
                        .email("first@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .active(true)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email("second@mail.com")
                        .password("password123")
                        .role(UserRole.EMPLOYER)
                        .active(true)
                        .build()
        );

        mockMvc.perform(get("/api/users")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].email").value("first@mail.com"))
                .andExpect(jsonPath("$.content[1].email").value("second@mail.com"));
    }

    @Test
    void deactivateUser_shouldSetUserToInactive() throws Exception {
        var savedUser = userRepository.save(
                User.builder()
                        .email("test@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .active(true)
                        .build()
    );

        mockMvc.perform(patch("/api/users/{userId}/deactivate", savedUser.getId()))
                .andExpect(status().isNoContent());

        var updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(updatedUser.isActive()).isFalse();
    }

    @Test
    void activateUser_shouldSetUserToActive() throws Exception {
        var savedUser = userRepository.save(
                User.builder()
                        .email("test@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .build()
        );

        savedUser.setActive(false);
        userRepository.save(savedUser);

        mockMvc.perform(patch("/api/users/{userId}/activate", savedUser.getId()))
                .andExpect(status().isNoContent());

        var updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(updatedUser.isActive()).isTrue();
    }

    @Test
    void updateUser_shouldUpdateEmail() throws Exception {
        var savedUser = userRepository.save(
                User.builder()
                .email("test@mail.com")
                .password("password123")
                .role(UserRole.CANDIDATE)
                .active(true)
                .build()
        );

        var request = new  UpdateUserRequest("new@mail.com");

        mockMvc.perform(patch("/api/users/{userId}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.email").value("new@mail.com"))
                .andExpect(jsonPath("$.role").value("CANDIDATE"));

        var updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(updatedUser.getEmail()).isEqualTo("new@mail.com");
    }

    @Test
    void changePassword_shouldUpdatePassword() throws Exception {
        var savedUser = userRepository.save(
                User.builder()
                        .email("test@mail.com")
                        .password("oldPassword123")
                        .role(UserRole.CANDIDATE)
                        .active(true)
                        .build()
        );

        var request = new ChangePasswordRequest(
                "oldPassword123",
                "newPassword123",
                "newPassword123");

        mockMvc.perform(patch("/api/users/{userId}/password", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        var updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(updatedUser.getPassword()).isEqualTo("newPassword123");
    }

    @Test
    void deleteUser_shouldDeleteUser() throws Exception {
        var savedUser = userRepository.save(
                User.builder()
                        .email("test@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .active(true)
                        .build()
        );

        mockMvc.perform(delete("/api/users/{userId}", savedUser.getId()))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    void getUserById_shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/users/{userId}", 999L))
                .andExpect(status().isNotFound());
    }
}
