package com.jobela.jobela_api.common.error;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        LocalDateTime timestamp,
        int status,
        String errorType,
        Map<String, String> errors,
        String path
) {
}
