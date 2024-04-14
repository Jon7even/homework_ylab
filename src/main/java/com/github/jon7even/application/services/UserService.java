package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.dto.user.UserLoginAuthDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;

/**
 * Интерфейс операций для пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserService {
    /**
     * Метод, который регистрирует нового пользователя в системе
     *
     * @param userCreateDto заполненный объект DTO
     * @return короткая форма ответа UserShortResponseDto
     */
    UserShortResponseDto createUser(UserCreateDto userCreateDto);

    /**
     * Метод, который получает DTO пользователя для использования в памяти View
     *
     * @param userLoginAuthDto заполненный объект DTO
     * @return объект DTO UserInMemoryDto
     */
    UserInMemoryDto findUserForAuthorization(UserLoginAuthDto userLoginAuthDto);
}
