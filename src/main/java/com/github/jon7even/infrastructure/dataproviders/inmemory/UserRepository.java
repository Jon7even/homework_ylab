package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialDataInDb.ADMIN_LOGIN;
import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialDataInDb.ADMIN_PASSWORD;


/**
 * Реализация репозитория пользователей в памяти
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserRepository implements UserDao {
    private static UserRepository instance;

    private final HashMap<Long, UserEntity> userList = new HashMap<>();

    private Long idGenerator = 0L;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        UserEntity admin = UserEntity.builder()
                .id(++idGenerator)
                .login(ADMIN_LOGIN)
                .password(ADMIN_PASSWORD)
                .idGroupPermissions(1)
                .build();
        userList.put(admin.getId(), admin);
    }

    @Override
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        Long userId = ++idGenerator;
        userEntity.setId(userId);
        userList.put(userId, userEntity);
        System.out.println("В БД добавлен новый пользователь: " + userEntity);
        return Optional.of(userList.get(userId));
    }

    @Override
    public Optional<UserEntity> updateUser(UserEntity userEntity) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        System.out.println("Ищу пользователя с userId=" + userId);
        return Optional.of(userList.get(userId));
    }

    @Override
    public Optional<UserEntity> findByUserLogin(String userLogin) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> deleteUserById(String userId) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }
}
