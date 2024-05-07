package com.github.jon7even.validator;

/**
 * Интерфейс для валидации входящих DTO в контроллерах
 *
 * @author Jon7even
 * @version 1.0
 */
public interface ValidatorDto<T> {
    /**
     * Метод для валидации входящих DTO
     *
     * @param dto объект для валидации
     */
    void validate(T dto);
}
