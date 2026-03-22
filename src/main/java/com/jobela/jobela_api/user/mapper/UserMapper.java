package com.jobela.jobela_api.user.mapper;

import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.response.UserResponse;
import org.mapstruct.Mapper;

import com.jobela.jobela_api.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    UserResponse toResponse(User user);
}
