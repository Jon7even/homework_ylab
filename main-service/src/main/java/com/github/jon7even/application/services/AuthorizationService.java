package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.user.UserLoginAuthDto;

/**
 * Интерфейс для взаимодействия с сервисом авторизации
 *
 * @author Jon7even
 * @version 1.0
 */
public interface AuthorizationService {
    /**
     * Метод, который даёт однозначный ответ правильный ли пароль ввел пользователь
     *
     * @param userLoginAuthDto заполненный пользователем объект DTO
     * @return boolean с ответом - правильно ли введен пароль
     */
    boolean processAuthorization(UserLoginAuthDto userLoginAuthDto);
}
