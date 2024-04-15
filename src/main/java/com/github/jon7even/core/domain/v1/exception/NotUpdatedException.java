package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если ресурс не был обновлен
 *
 * @author Jon7even
 * @version 1.0
 */
public class NotUpdatedException extends ApplicationException {
    public NotUpdatedException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not updated", resource);
    }
}

