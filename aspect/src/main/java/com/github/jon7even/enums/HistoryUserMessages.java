package com.github.jon7even.enums;

/**
 * Перечисление сообщений для логирования
 *
 * @author Jon7even
 * @version 1.0
 */
public enum HistoryUserMessages {
    /**
     * Действия происходящие с ресурсами
     */
    SIGN_UP("<Регистрация>"),
    SIGN_IN("<Вход в приложение>"),
    SIGN_OUT("<Выход из приложения>"),
    DIARY_CREATE_SHORT("<Создание нового дневника>"),
    DIARY_CREATE_FULL("<Создание нового дневника создано=[%s] вес=[%.01fкг] рост=[%.01fсм]>"),
    DIARY_UPDATE_SHORT("<Обновление существующего дневника>"),
    DIARY_UPDATE_FULL("<Обновление существующего дневника обновлено=[%s] вес=[%.01fкг] рост=[%.01fсм]>"),
    DIARY_FIND_BY_USER_ID("<Поиск дневника по userId=[%d]>"),
    DIARY_FIND_BY_DIARY_ID("<Поиск дневника по diaryId=[%d]>"),
    DIARY_EXIST_BY_USER_ID("<Проверка существования дневника по userId=[%d]>"),

    /**
     * Какие процессы происходят с ресурсом
     */
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
