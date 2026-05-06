package com.jobela.jobela_api.candidate.dto.response.profile;

import com.jobela.jobela_api.candidate.dto.response.certification.CandidateCertificationResponse;
import com.jobela.jobela_api.candidate.dto.response.document.CandidateDocumentResponse;
import com.jobela.jobela_api.candidate.dto.response.education.CandidateEducationResponse;
import com.jobela.jobela_api.candidate.dto.response.language.CandidateLanguageResponse;
import com.jobela.jobela_api.candidate.dto.response.locationpreference.CandidateLocationPreferenceResponse;
import com.jobela.jobela_api.candidate.dto.response.preference.CandidatePreferenceResponse;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.dto.response.workauthorization.CandidateWorkAuthorizationResponse;
import com.jobela.jobela_api.candidate.dto.response.workexperience.CandidateWorkExperienceResponse;
import com.jobela.jobela_api.common.enums.Gender;

import java.util.List;
import java.time.LocalDate;

public record CandidatePublicProfileResponse(

        String titleBefore,
        String titleAfter,
        String firstName,
        String lastName,
        Gender gender,
        String phone,
        String city,
        String country,
        String nationality,
        LocalDate dateOfBirth,
        String headline,
        String summary,
        String profilePhotoUrl,

        CandidatePreferenceResponse preference,
        List<CandidateWorkExperienceResponse> workExperiences,
        List<CandidateEducationResponse> educations,
        List<CandidateCertificationResponse> certifications,
        List<CandidateSkillResponse> skills,
        List<CandidateLanguageResponse> languages,
        List<CandidateDocumentResponse> documents,
        List<CandidateLocationPreferenceResponse> locationPreferences,
        List<CandidateWorkAuthorizationResponse> workAuthorizations
) {
}
