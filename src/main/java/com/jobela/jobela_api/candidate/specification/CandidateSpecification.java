package com.jobela.jobela_api.candidate.specification;

import com.jobela.jobela_api.candidate.entity.Candidate;
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
}

