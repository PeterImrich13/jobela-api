package com.jobela.jobela_api.employer.dto.response.profile;

import com.jobela.jobela_api.common.enums.Industry;

public record EmployerPublicProfileResponse(

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
        boolean verified
) {
}
