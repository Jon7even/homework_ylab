package com.github.jon7even.core.domain.v1.exception;

/**
 * Класс описывающий исключение если при общих загрузках БД возникают ошибки
 *
 * @author Jon7even
 * @version 1.0
 */
public class DataBaseException extends ApplicationException {
    public DataBaseException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] bad connection", resource);
    }
}


