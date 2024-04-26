package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если ресурс был не удален
 *
 * @author Jon7even
 * @version 1.0
 */
public class NotDeleteException extends ApplicationException {
    public NotDeleteException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not delete", resource);
    }
}

