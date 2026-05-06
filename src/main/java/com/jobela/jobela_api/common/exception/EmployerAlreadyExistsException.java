package com.jobela.jobela_api.common.exception;

public class EmployerAlreadyExistsException extends RuntimeException {
    public EmployerAlreadyExistsException(String message) {
        super(message);
    }
}
