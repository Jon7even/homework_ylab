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
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;

import java.util.Optional;

/**
 * Реализация сервиса для взаимодействия с пользователями
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
        userRepository = UserRepository.getInstance();
        userMapper = new UserMapperImpl();
    }

    @Override
    public UserShortResponseDto createUser(UserCreateDto userCreateDto) {
        System.out.println("К нам пришел новый пользователь: " + userCreateDto);

        UserEntity userForSaveInRepository = userMapper.toEntityFromDtoCreate(userCreateDto);
        System.out.println("Пользователь для сохранения собран: " + userForSaveInRepository);

        Optional<UserEntity> createdUser = userRepository.createUser(userForSaveInRepository);
        System.out.println("Пользователя попытались сохранить в репозитории: " + createdUser);

        if (createdUser.isPresent()) {
            System.out.println("Новый пользователь успешно сохранен");
            return userMapper.toShortDtoFromEntity(createdUser.get());
        } else {
            throw new NotCreatedException("New User");
        }
    }

    @Override
    public UserInMemoryDto findUserForAuthorization(UserLoginAuthDto userLoginAuthDto) {
        System.out.println("Поиск пользователя по логину: " + userLoginAuthDto);
        Optional<UserEntity> foundUserEntity = userRepository.findByUserLogin(userLoginAuthDto.getLogin());

        if (foundUserEntity.isPresent()) {
            System.out.println("Найден пользователь по логину: " + foundUserEntity);
            return userMapper.toInMemoryDtoFromEntity(foundUserEntity.get());
        } else {
            throw new NotFoundException("User by login");
        }
    }
}
