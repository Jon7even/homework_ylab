package com.github.jon7even.core.domain.v1.exception;

public class IncorrectTimeException extends ApplicationException {
    public IncorrectTimeException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not correct", resource);
    }
}


