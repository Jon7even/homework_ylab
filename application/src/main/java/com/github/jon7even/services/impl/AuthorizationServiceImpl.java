package com.github.jon7even.services.impl;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dto.user.UserLoginAuthDto;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.services.AuthorizationService;

/**
 * Реализация сервиса авторизации для пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserDao userRepository;

    public AuthorizationServiceImpl(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean processAuthorization(UserLoginAuthDto userLoginAuthDto) {
        System.out.println("К нам пришел пользователь на авторизацию: " + userLoginAuthDto);
        UserEntity userFromBd = userRepository.findByUserLogin(userLoginAuthDto.getLogin())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with [userLogin=%s]", userLoginAuthDto.getLogin()))
                );
        if (userFromBd.getPassword().equals(userLoginAuthDto.getPassword())) {
            System.out.println("Пользователь авторизовался");
            return true;
        } else {
            throw new AccessDeniedException(String.format("For [userLogin=%s]",
                    userLoginAuthDto.getLogin()));
        }
    }
}
