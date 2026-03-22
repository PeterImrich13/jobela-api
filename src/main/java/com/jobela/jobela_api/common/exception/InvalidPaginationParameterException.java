package com.jobela.jobela_api.common.exception;


public class InvalidPaginationParameterException extends RuntimeException {

    public InvalidPaginationParameterException(String message) {
        super(message);
    }
}
