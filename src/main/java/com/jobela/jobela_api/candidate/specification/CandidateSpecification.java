package com.jobela.jobela_api.candidate.specification;

import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.common.enums.AuthorizationType;
import com.jobela.jobela_api.common.enums.CandidateTargetPosition;
import com.jobela.jobela_api.common.enums.LanguageLevel;
import org.springframework.data.jpa.domain.Specification;

public final class CandidateSpecification {

    private CandidateSpecification() {

    }

    public static Specification<Candidate> profileVisible() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.isTrue(root.get("profileVisible"));
        }

    public static Specification<Candidate> countryEquals(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country == null || country.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("country")),
                    country.toLowerCase()
            );
        };
    }

    public static Specification<Candidate> cityEquals(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("city")),
                    city.toLowerCase()
            );
        };
    }

    public static Specification<Candidate> search (String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            var pattern = "%" + search.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("headline")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("summary")), pattern)
            );
        };
    }

    public static Specification<Candidate> skillEquals(String skill) {
        return (root, query, criteriaBuilder) -> {
            if (skill == null || skill.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            query.distinct(true);

            var skillJoin = root.join("candidateSkills");

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(skillJoin.get("skillName")),
                    skill.toLowerCase()
            );
        };
    }

    public static Specification<Candidate> languageEquals(String language) {
        return (root, query, criteriaBuilder) -> {
            if (language == null || language.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            query.distinct(true);

            var languageJoin= root.join("candidateLanguages");

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(languageJoin.get("languageName")),
                    language.toLowerCase()
            );
        };
    }

    public static Specification<Candidate> openToWorkEquals(Boolean openToWork) {
        return (root, query, criteriaBuilder) -> {
            if (openToWork == null) {
                return criteriaBuilder.conjunction();
            }

            var preferenceJoin = root.join("candidatePreference");

            return criteriaBuilder.equal(
                    preferenceJoin.get("openToWork"),
                    openToWork
            );
        } ;
    }

    public static Specification<Candidate> targetPositionEquals(CandidateTargetPosition targetPosition) {
        return (root, query, criteriaBuilder) ->  {
            if (targetPosition == null) {
                return criteriaBuilder.conjunction();
            }

            var preferenceJoin = root.join("candidatePreference");

            return criteriaBuilder.equal(
                    preferenceJoin.get("targetPosition"),
                    targetPosition
            );
        };
    }

    public static Specification<Candidate> languageLevelEquals(LanguageLevel level) {
        return (root, query, criteriaBuilder) ->  {
            if (level == null) {
                return criteriaBuilder.conjunction();
            }

            query.distinct(true);

            var languageJoin = root.join("candidateLanguages");

            return criteriaBuilder.equal(
                    languageJoin.get("level"),
                    level
            );
        };
    }

    public static Specification<Candidate> authorizationTypeEquals(AuthorizationType authorizationType) {
        return (root, query, criteriaBuilder) ->  {
            if (authorizationType == null) {
                return criteriaBuilder.conjunction();
            }

            query.distinct(true);

            var authorizationJoin = root.join("candidateWorkAuthorizations");

            return criteriaBuilder.equal(
                    authorizationJoin.get("authorizationType"),
                    authorizationType
            );
        };
    }

    public static Specification<Candidate> sponsorshipRequiredEquals(Boolean sponsorshipRequired) {
        return (root, query, criteriaBuilder) ->  {
            if (sponsorshipRequired == null) {
                return criteriaBuilder.conjunction();
            }

            query.distinct(true);

            var sponsorshipJoin = root.join("candidateWorkAuthorizations");

            return criteriaBuilder.equal(
                    sponsorshipJoin.get("sponsorshipRequired"),
                    sponsorshipRequired
            );
        };
    }

}

