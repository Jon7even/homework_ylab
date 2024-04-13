package com.github.jon7even.core.domain.v1.exception;

public class BadLoginException extends ApplicationException {
    public BadLoginException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not created", resource);
    }
}

