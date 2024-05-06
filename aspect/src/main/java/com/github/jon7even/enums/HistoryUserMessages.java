package com.github.jon7even.enums;

/**
 * Перечисление сообщений для логирования
 *
 * @author Jon7even
 * @version 1.0
 */
public enum HistoryUserMessages {
    SIGN_UP("<Регистрация>"),
    SIGN_IN("<Вход в приложение>"),
    SIGN_OUT("<Выход из приложения>"),

    SUCCESS(": Успех"),
    IN_PROGRESS(": Процесс"),
    FAILURE(": Провал"),
    WARN(": Обратите внимание: ");

    private final String message;

    HistoryUserMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
