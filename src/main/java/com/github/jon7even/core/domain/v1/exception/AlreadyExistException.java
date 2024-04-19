package com.github.jon7even.core.domain.v1.exception;

public class AlreadyExistException extends ApplicationException {
    public AlreadyExistException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] already exist", resource);
    }
}

