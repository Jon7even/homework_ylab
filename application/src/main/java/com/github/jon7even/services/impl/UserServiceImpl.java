package com.github.jon7even.services.impl;

import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.mappers.UserMapper;
import com.github.jon7even.core.domain.v1.mappers.UserMapperImpl;
import com.github.jon7even.services.UserService;

import static com.github.jon7even.services.constants.Constants.DEFAULT_USER_GROUP;

/**
 * Реализация сервиса взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final Integer SERVICE_USER_ID = 5;
    private final UserDao userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDao userRepository) {
        this.userMapper = new UserMapperImpl();
        this.userRepository = userRepository;
    }

    @Loggable
    @Override
    public UserShortResponseDto createUser(UserCreateDto userCreateDto) {
        System.out.println("К нам пришел новый пользователь: " + userCreateDto);

        UserEntity userForSaveInRepository = userMapper.toEntityFromDtoCreate(userCreateDto, DEFAULT_USER_GROUP);
        System.out.println("Пользователь для сохранения собран: " + userForSaveInRepository);

        UserEntity createdUser = userRepository.createUser(userForSaveInRepository)
                .orElseThrow(() -> new NotCreatedException("New User"));
        return userMapper.toShortDtoFromEntity(createdUser);
    }

    @Override
    public UserLogInResponseDto findUserForAuthorization(UserLogInAuthDto userLoginAuthDto) {
        System.out.println("Поиск пользователя по логину: " + userLoginAuthDto);
        UserEntity foundUserEntity = userRepository.findByUserLogin(userLoginAuthDto.getLogin())
                .orElseThrow(() -> new NotFoundException("User by login"));

        System.out.println("Найден пользователь по логину: " + foundUserEntity);
        return userMapper.toInMemoryDtoFromEntity(foundUserEntity);
    }
}
