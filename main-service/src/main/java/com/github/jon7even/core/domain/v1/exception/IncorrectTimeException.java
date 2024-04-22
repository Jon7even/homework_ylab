package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если пользователь даёт неправильное время
 *
 * @author Jon7even
 * @version 1.0
 */
public class IncorrectTimeException extends ApplicationException {
    public IncorrectTimeException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not correct", resource);
    }
}


