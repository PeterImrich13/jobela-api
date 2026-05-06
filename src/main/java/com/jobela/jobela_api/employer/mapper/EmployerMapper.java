package com.jobela.jobela_api.employer.mapper;

import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import com.jobela.jobela_api.employer.dto.request.CreateEmployerRequest;
import com.jobela.jobela_api.employer.dto.request.UpdateEmployerRequest;
import com.jobela.jobela_api.employer.dto.response.EmployerResponse;
import com.jobela.jobela_api.employer.entity.Employer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface EmployerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "companyName", source = "companyName", qualifiedByName = "clean")
    @Mapping(target = "companyDescription", source = "companyDescription", qualifiedByName = "clean")
    @Mapping(target = "website", source = "website", qualifiedByName = "clean")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "clean")
    @Mapping(target = "contactEmail", source = "contactEmail", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "street", source = "street", qualifiedByName = "clean")
    @Mapping(target = "streetNumber", source = "streetNumber", qualifiedByName = "clean")
    @Mapping(target = "profilePhoto", source = "profilePhoto", qualifiedByName = "clean")
    @Mapping(target = "profileVisible", ignore = true)
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Employer toEntity(CreateEmployerRequest request);

    @Mapping(target = "userId", source = "user.id")
    EmployerResponse toResponse(Employer employer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "companyName", source = "companyName", qualifiedByName = "clean")
    @Mapping(target = "companyDescription", source = "companyDescription", qualifiedByName = "clean")
    @Mapping(target = "website", source = "website", qualifiedByName = "clean")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "clean")
    @Mapping(target = "contactEmail", source = "contactEmail", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "street", source = "street", qualifiedByName = "clean")
    @Mapping(target = "streetNumber", source = "streetNumber", qualifiedByName = "clean")
    @Mapping(target = "profilePhoto", source = "profilePhoto", qualifiedByName = "clean")
    @Mapping(target = "profileVisible", ignore = true)
    @Mapping(target = "verified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEmployerFromRequest(UpdateEmployerRequest request, @MappingTarget Employer employer);
}
