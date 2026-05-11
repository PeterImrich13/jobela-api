package com.jobela.jobela_api.common.sort;


import java.util.Set;

public final class CandidatePublicProfileSortFields {

    public static final Set<String> ALLOWED = Set.of(
            "createdAt",
            "firstName",
            "lastName",
            "city",
            "country"
    );

    private CandidatePublicProfileSortFields() {

    }
}
