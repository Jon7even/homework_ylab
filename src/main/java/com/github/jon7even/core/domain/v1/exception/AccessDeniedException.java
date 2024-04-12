package com.github.jon7even.core.domain.v1.exception;

public class AccessDeniedException extends ApplicationException {
    public AccessDeniedException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not found", resource);
    }
}

