package com.jobela.jobela_api.common.sort;

import java.util.Set;

public final class EmployerPublicProfileSortFields {

    public static final Set<String> ALLOWED = Set.of(
            "companyName",
            "city",
            "country"
    );

    private EmployerPublicProfileSortFields() {

    }


}
