package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.dto.user.UserLoginAuthDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.application.services.UserService;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.mappers.UserMapper;
import com.github.jon7even.core.domain.v1.mappers.UserMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.jdbc.UserJdbcRepository;

/**
 * Реализация сервиса взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private final UserDao userRepository;
    private final UserMapper userMapper;

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceImpl() {
        userRepository = UserJdbcRepository.getInstance();
        userMapper = new UserMapperImpl();
    }

    @Override
    public UserShortResponseDto createUser(UserCreateDto userCreateDto) {
        System.out.println("К нам пришел новый пользователь: " + userCreateDto);

        UserEntity userForSaveInRepository = userMapper.toEntityFromDtoCreate(userCreateDto);
        System.out.println("Пользователь для сохранения собран: " + userForSaveInRepository);

        UserEntity createdUser = userRepository.createUser(userForSaveInRepository)
                .orElseThrow(() -> new NotCreatedException("New User"));
        return userMapper.toShortDtoFromEntity(createdUser);
    }

    @Override
    public UserInMemoryDto findUserForAuthorization(UserLoginAuthDto userLoginAuthDto) {
        System.out.println("Поиск пользователя по логину: " + userLoginAuthDto);
        UserEntity foundUserEntity = userRepository.findByUserLogin(userLoginAuthDto.getLogin())
                .orElseThrow(() -> new NotFoundException("User by login"));

        System.out.println("Найден пользователь по логину: " + foundUserEntity);
        return userMapper.toInMemoryDtoFromEntity(foundUserEntity);
    }
}
