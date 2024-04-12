package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.user.UserLoginAuthDto;
import com.github.jon7even.application.services.AuthorizationService;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.UserEntity;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;

import java.util.Optional;

/**
 * Реализация класса авторизации для пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    private static AuthorizationServiceImpl instance;

    private final UserDao userRepository;

    public static AuthorizationServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthorizationServiceImpl();
        }
        return instance;
    }

    private AuthorizationServiceImpl() {
        userRepository = UserRepository.getInstance();
    }

    @Override
    public boolean processAuthorization(UserLoginAuthDto userLoginAuthDto) {
        System.out.println("К нам пришел пользователь на авторизацию: " + userLoginAuthDto);
        Optional<UserEntity> userFromBd = userRepository.findByUserLogin(userLoginAuthDto.getLogin());

        if (userFromBd.isPresent()) {
            if (userFromBd.get().getPassword().equals(userLoginAuthDto.getPassword())) {
                System.out.println("Пользователь авторизовался");
                return true;
            } else {
                throw new AccessDeniedException(String.format("Access Denied for [userLogin=%s]",
                        userLoginAuthDto.getLogin()));
            }
        } else {
            throw new NotFoundException(String.format("User with [userLogin=%s]", userLoginAuthDto.getLogin()));
        }
    }
}
