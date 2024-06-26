package com.github.jon7even.services.impl;

import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.services.AuthorizationService;

/**
 * Реализация сервиса авторизации для пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserDao userRepository;

    public AuthorizationServiceImpl(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean processAuthorization(UserLogInAuthDto userLoginAuthDto) {
        System.out.println("К нам пришел пользователь на авторизацию: " + userLoginAuthDto);
        UserEntity userFromBd = userRepository.findByUserLogin(userLoginAuthDto.getLogin())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with [userLogin=%s]", userLoginAuthDto.getLogin()))
                );
        if (userFromBd.getPassword().equals(userLoginAuthDto.getPassword())) {
            System.out.println("Пользователь авторизовался");
            return true;
        } else {
            System.out.printf("Доступ запрещен, пароль введен неверно [userLogin=%s]%n", userLoginAuthDto.getLogin());
            return false;
        }
    }

    @Override
    public void processLogOut(UserLogInResponseDto userLogInResponseDto) {
        System.out.println("Пользователь выходит из приложения: " + userLogInResponseDto);
    }
}
