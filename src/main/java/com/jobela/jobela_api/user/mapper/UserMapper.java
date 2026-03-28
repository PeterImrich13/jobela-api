package com.jobela.jobela_api.user.mapper;

import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import com.jobela.jobela_api.user.dto.request.CreateUserRequest;
import com.jobela.jobela_api.user.dto.request.UpdateUserRequest;
import com.jobela.jobela_api.user.dto.response.UserResponse;
import org.mapstruct.Mapper;

import com.jobela.jobela_api.user.entity.User;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "email", source = "email", qualifiedByName = "clean")
    User toEntity(CreateUserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "email", source = "email", qualifiedByName = "clean")
    void updateUserFromRequest(UpdateUserRequest request, @MappingTarget User user);

    UserResponse toResponse(User user);
}
