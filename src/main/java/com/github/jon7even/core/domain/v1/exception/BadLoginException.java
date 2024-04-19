package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если логин пользователя некорректный
 *
 * @author Jon7even
 * @version 1.0
 */
public class BadLoginException extends ApplicationException {
    public BadLoginException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not created", resource);
    }
}


