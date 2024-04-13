package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.UserEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialDataInDb.*;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;


/**
 * Реализация репозитория пользователей в памяти
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserRepository implements UserDao {
    private static UserRepository instance;
    private final Map<Long, UserEntity> mapOfUsers = new HashMap<>();
    public static List<String> banListAddLogin;
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
                .idGroupPermissions(DEFAULT_GROUP_PERMISSIONS_ADMIN)
                .build();
        mapOfUsers.put(admin.getId(), admin);

        try {
            banListAddLogin = lines(get(HOME, "BanListAddLogin.properties")).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        if (containsLoginInBanList(userEntity.getLogin())) {
            System.out.println("Запрещено видоизменять пользователя с таким логином");
            return Optional.empty();
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

        if (containUserById(userId)) {
            Optional<UserEntity> foundUserEntity = Optional.of(mapOfUsers.get(userId));
            System.out.println("Найден пользователь: " + foundUserEntity.get());
            return foundUserEntity;
        } else {
            System.out.println("Пользователь с таким userId не найден");
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUserLogin(String userLogin) {
        System.out.println("Ищу пользователя по логину userLogin=" + userLogin);

        Optional<UserEntity> foundUserEntity = mapOfUsers.values().stream()
                .filter(userEntity -> userEntity.getLogin().contains(userLogin))
                .findFirst();

        if (foundUserEntity.isPresent()) {
            System.out.println("Найден пользователь с таким логином: " + foundUserEntity.get());
            return foundUserEntity;
        } else {
            System.out.println("Пользователь с таким логином не найден");
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        System.out.println("Получаю список пользователей");
        return mapOfUsers.values().stream().toList();
    }

    private Boolean containUserById(Long userId) {
        System.out.println("Проверяем есть ли пользователь с userId=" + userId);
        return mapOfUsers.containsKey(userId);
    }

    private Boolean containsLoginInBanList(String userLogin) {
        return banListAddLogin.stream().anyMatch(userLogin::equalsIgnoreCase);
    }
}
