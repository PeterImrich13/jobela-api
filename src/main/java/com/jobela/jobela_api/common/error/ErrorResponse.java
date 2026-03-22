package com.jobela.jobela_api.common.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String errorType,
        String message,
        String path
) {
}
