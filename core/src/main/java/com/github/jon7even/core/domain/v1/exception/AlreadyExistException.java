package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если сущность не создана
 *
 * @author Jon7even
 * @version 1.0
 */
public class AlreadyExistException extends ApplicationException {
    public AlreadyExistException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] already exist", resource);
    }
}

