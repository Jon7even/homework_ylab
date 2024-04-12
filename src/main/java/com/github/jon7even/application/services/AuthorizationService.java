package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.user.UserLoginAuthDto;

/**
 * Интерфейс авторизации для пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface AuthorizationService {
    boolean processAuthorization(UserLoginAuthDto userLoginAuthDto);
}
