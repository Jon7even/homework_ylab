package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если ресурс не прошел валидацию
 *
 * @author Jon7even
 * @version 1.0
 */
public class MethodArgumentNotValidException extends ApplicationException {
    public MethodArgumentNotValidException(String resource, String reason) {
        super(getErrorMessage(resource, reason));
    }

    private static String getErrorMessage(String resource, String reason) {
        return String.format("[%s] not validated, reason: [%s]", resource, reason);
    }
}

