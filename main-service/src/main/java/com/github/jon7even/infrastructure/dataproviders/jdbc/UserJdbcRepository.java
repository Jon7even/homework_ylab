package com.github.jon7even.infrastructure.dataproviders.jdbc;

import com.github.jon7even.configuration.database.MainConfig;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.infrastructure.dataproviders.core.ConnectionManager;
import com.github.jon7even.infrastructure.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.infrastructure.dataproviders.core.impl.UserRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Реализация репозитория пользователей с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserJdbcRepository implements UserDao {
    private static Set<String> BAN_LIST_ADD_LOGIN;
    private final ConnectionManager connectionManager;
    private final MainConfig config;
    private final UserRowMapper userEntityRowMapper;

    public UserJdbcRepository(MainConfig config) {
        this.config = config;
        BAN_LIST_ADD_LOGIN = config.getBAN_LIST_ADD_LOGIN();
        connectionManager = new ConnectionManagerImpl(config);
        userEntityRowMapper = UserRowMapper.getInstance();
    }

    @Override
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        System.out.println("Пришел запрос на добавление нового пользователя" + userEntity);
        if (containsLoginInBanList(userEntity.getLogin())) {
            System.out.println("Запрещено регистрировать пользователя с таким логином");
            throw new BadLoginException("New User");
        }
        Connection connection = connectionManager.getConnection();
        String queryCreate = String.format("INSERT INTO %s.user (login, password, id_group)"
                + "     VALUES (?,?,?) RETURNING ID", config.getMAIN_SCHEMA());

        try (PreparedStatement statement = connection.prepareStatement(queryCreate)) {
            statement.setString(1, userEntity.getLogin());
            statement.setString(2, userEntity.getPassword());
            statement.setInt(3, userEntity.getIdGroupPermissions());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                long idKeyHolder = resultSet.getLong(1);
                userEntity.setId(idKeyHolder);
                System.out.println("Пользователю присвоен новый ID=" + idKeyHolder);
            } else {
                System.out.println("ID пользователю не присвоен!");
            }

            System.out.println("В БД добавлен новый пользователь " + userEntity);
            return Optional.of(userEntity);
        } catch (SQLException e) {
            System.out.println("БД не сохранила пользователя " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> updateUser(UserEntity userEntity) {
        System.out.println("Пришел пользователь на обновление: " + userEntity);
        Long userId = userEntity.getId();
        Optional<UserEntity> oldUser = findByUserId(userId);
        if (oldUser.isPresent()) {
            System.out.println("Пользователь есть в системе, продолжаем обновление");
        } else {
            System.out.println("Пользователя нет с таким ID userId=" + userId);
            return Optional.empty();
        }

        Connection connection = connectionManager.getConnection();
        String queryUpdate = String.format("UPDATE %s.user "
                + "  SET login = ?, password = ?, id_group = ? "
                + "WHERE id = ?", config.getMAIN_SCHEMA());

        try (PreparedStatement statement = connection.prepareStatement(queryUpdate)) {
            statement.setString(1, userEntity.getLogin());
            statement.setString(2, userEntity.getPassword());
            statement.setInt(3, userEntity.getIdGroupPermissions());
            statement.setLong(4, userId);

            if (statement.executeUpdate() > 0) {
                System.out.println("В БД произошло обновление. Старые данные: " + oldUser
                        + "\n Новые данные: " + userEntity);
                return Optional.of(userEntity);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("БД не обновила пользователя " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        System.out.println("Пришел запрос на получение данных пользователя по ID=" + userId);
        Connection connection = connectionManager.getConnection();
        String sqlFindUser = String.format("SELECT * " +
                        "  FROM %s.user " +
                        " WHERE id = ?",
                config.getMAIN_SCHEMA());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userEntityRowMapper.mapRow(resultSet));
            } else {
                System.out.println("В БД такого пользователя нет");
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("БД не нашла пользователя из-за проблем с SQL " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUserLogin(String userLogin) {
        System.out.println("Пришел запрос на получение данных пользователя по login=" + userLogin);
        Connection connection = connectionManager.getConnection();
        String sqlFindUser = String.format("SELECT * " +
                        "  FROM %s.user " +
                        " WHERE login = ?",
                config.getMAIN_SCHEMA());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {
            statement.setString(1, userLogin);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userEntityRowMapper.mapRow(resultSet));
            } else {
                System.out.println("В БД такого пользователя нет");
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("БД не нашла пользователя из-за проблем с SQL " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        System.out.println("Пришел запрос на получение данных всех пользователей");
        Connection connection = connectionManager.getConnection();
        String sqlFindUser = String.format("SELECT * " +
                        "  FROM %s.user ",
                config.getMAIN_SCHEMA());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {
            ResultSet resultSet = statement.executeQuery();
            List<UserEntity> usersFromBD = new ArrayList<>();

            while (resultSet.next()) {
                UserEntity userEntity = userEntityRowMapper.mapRow(resultSet);
                usersFromBD.add(userEntity);
            }
            System.out.println("Найден список пользователей count=" + usersFromBD.size());
            return usersFromBD;
        } catch (SQLException e) {
            System.out.println("БД не нашла список пользователей из-за проблем с SQL " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private boolean containsLoginInBanList(String userLogin) {
        return BAN_LIST_ADD_LOGIN.stream().anyMatch(userLogin::equalsIgnoreCase);
    }
}
