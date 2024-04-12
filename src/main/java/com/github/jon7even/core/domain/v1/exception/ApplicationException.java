package com.github.jon7even.core.domain.v1.exception;

public class ApplicationException extends RuntimeException {
    private final String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
