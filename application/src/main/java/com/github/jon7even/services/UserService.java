package com.github.jon7even.services;

import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;

/**
 * Интерфейс для взаимодействия с пользователями
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
    UserLogInResponseDto findUserForAuthorization(UserLogInAuthDto userLoginAuthDto);

    /**
     * Метод для поиска ID пользователя по его логину, если он есть в системе
     *
     * @param userLogin существующий login пользователя
     * @return ID пользователя, если он есть в системе
     */
    Long getUserIdByLogin(String userLogin);
}
