package com.jobela.jobela_api.common.sort;

import java.util.Set;

public final class UserSortFields {

    public static final Set<String> ALLOWED = Set.of(
            "id",
            "email",
            "createdAt",
            "updatedAt"
    );

    private UserSortFields() {

    }
}
