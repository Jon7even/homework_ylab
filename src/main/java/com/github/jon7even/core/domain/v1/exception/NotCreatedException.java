package com.github.jon7even.core.domain.v1.exception;

public class NotCreatedException extends ApplicationException {
    public NotCreatedException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not created", resource);
    }
}

