package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если доступ к ресурсу запрещен
 *
 * @author Jon7even
 * @version 1.0
 */
public class AccessDeniedException extends ApplicationException {
    public AccessDeniedException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] Access Denied", resource);
    }
}

