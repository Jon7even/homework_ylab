package com.github.jon7even.validator;

public interface Validator<T> {
    /**
     * Метод для валидации входящих отдельных объектов
     *
     * @param obj  объект для валидации
     * @param name имя поля
     */
    void validate(T obj, String name);
}

