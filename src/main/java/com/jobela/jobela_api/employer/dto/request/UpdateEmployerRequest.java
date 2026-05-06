package com.jobela.jobela_api.employer.dto.request;

import com.jobela.jobela_api.common.enums.Industry;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateEmployerRequest(

        @Size(max = 255, message = "Company name cannot be longer than 255 characters")
        String companyName,

        @Size(max = 3000, message = "Company description cannot be longer than 3000 characters")
        String companyDescription,

        Industry industry,

        @Size(max = 100, message = "Website cannot be longer than 100 characters")
        String website,

        @Size(max = 50, message = "Phone cannot be longer than 50 characters")
        String phone,

        @Size(max = 255, message = "Contact email cannot be longer than 255 characters")
        @Email(message = "Invalid email format")
        String contactEmail,

        @Size(max = 100, message = "City cannot be longer than 100 characters")
        String city,

        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        @Size(max = 100, message = "Street cannot be longer than 100 characters")
        String street,

        @Size(max = 10, message = "Street number cannot be longer than 10 characters")
        String streetNumber,

        @Size(max = 500, message = "Profile photo cannot be longer than 500 characters")
        String profilePhoto,

        boolean profileVisible
) {
}
