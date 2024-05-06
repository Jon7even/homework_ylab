package com.github.jon7even.services;

import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;

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
    boolean processAuthorization(UserLogInAuthDto userLoginAuthDto);

    /**
     * Метод для выхода пользователя из приложения
     *
     * @param userLogInResponseDto заполненный системой объект DTO из сессии
     */
    void processLogOut(UserLogInResponseDto userLogInResponseDto);
}
