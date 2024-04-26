package com.github.jon7even.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.dataproviders.configuration.ConfigLoader;

import java.util.*;

import static com.github.jon7even.dataproviders.inmemory.constants.InitDbUser.ADMIN_FIRST_USER;


/**
 * Реализация репозитория пользователей в памяти
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserRepository implements UserDao {
    private static UserRepository instance;
    private static Set<String> BAN_LIST_ADD_LOGIN;
    private final Map<Long, UserEntity> mapOfUsers = new HashMap<>();
    private Long idGenerator = 0L;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        BAN_LIST_ADD_LOGIN = configLoader.getConfig().getBanListAddLogin();
        ++idGenerator;
        mapOfUsers.put(ADMIN_FIRST_USER.getId(), ADMIN_FIRST_USER);
    }

    @Override
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        if (containsLoginInBanList(userEntity.getLogin())) {
            System.out.println("Запрещено регистрировать пользователя с таким логином");
            throw new BadLoginException("New User");
        }

        Long userId = ++idGenerator;
        userEntity.setId(userId);
        mapOfUsers.put(userId, userEntity);

        System.out.println("В БД добавлен новый пользователь: " + userEntity);
        return findByUserId(userId);
    }

    @Override
    public Optional<UserEntity> updateUser(UserEntity userEntity) {
        Long userId = userEntity.getId();
        UserEntity oldUser;

        if (containUserById(userId)) {
            oldUser = mapOfUsers.get(userId);
        } else {
            return Optional.empty();
        }

        mapOfUsers.put(userId, userEntity);
        System.out.println("В БД произошло обновление. Старые данные: " + oldUser + "\n Новые данные: " + userEntity);
        return findByUserId(userId);
    }

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        System.out.println("Ищу пользователя с userId=" + userId);
        return Optional.ofNullable(mapOfUsers.get(userId));
    }

    @Override
    public Optional<UserEntity> findByUserLogin(String userLogin) {
        System.out.println("Ищу пользователя по логину userLogin=" + userLogin);

        return mapOfUsers.values().stream()
                .filter(userEntity -> userEntity.getLogin().contains(userLogin))
                .findFirst();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        System.out.println("Получаю список пользователей");
        return mapOfUsers.values().stream().toList();
    }

    private boolean containUserById(Long userId) {
        System.out.println("Проверяем есть ли пользователь с userId=" + userId);
        return mapOfUsers.containsKey(userId);
    }

    private boolean containsLoginInBanList(String userLogin) {
        return BAN_LIST_ADD_LOGIN.stream().anyMatch(userLogin::equalsIgnoreCase);
    }
}
