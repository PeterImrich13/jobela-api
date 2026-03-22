package com.jobela.jobela_api.common.exception;

public class CandidateAlreadyExistsException extends RuntimeException {
    public CandidateAlreadyExistsException(String message) {
        super(message);
    }
}
