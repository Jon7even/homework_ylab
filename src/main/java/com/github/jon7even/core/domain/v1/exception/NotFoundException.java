package com.github.jon7even.core.domain.v1.exception;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not found", resource);
    }
}
