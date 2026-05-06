package com.jobela.jobela_api.employer.dto.response;

import com.jobela.jobela_api.common.enums.Industry;
import java.time.LocalDateTime;

public record EmployerResponse(

        Long id,

        Long userId,

        String companyName,

        String companyDescription,

        Industry industry,

        String website,

        String phone,

        String contactEmail,

        String city,

        String country,

        String street,

        String streetNumber,

        String profilePhoto,

        boolean verified,

        Boolean profileVisible,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
