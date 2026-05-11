package com.jobela.jobela_api.common.sort;

import java.util.Set;

public final class EmployerSortFields {

    public static final Set<String> ALLOWED = Set.of(
            "id",
            "companyName",
            "industry",
            "city",
            "country",
            "createdAt",
            "updatedAt"
    );

    private EmployerSortFields() {

    }
}
