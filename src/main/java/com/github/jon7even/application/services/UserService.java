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
    UserShortResponseDto createUser(UserCreateDto userCreateDto);

    UserInMemoryDto findUserForAuthorization(UserLoginAuthDto userLoginAuthDto);
}
