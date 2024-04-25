package com.github.jon7even.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;
import com.github.jon7even.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.dataproviders.core.mapper.UserRowMapper;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class UserJdbcRepository implements UserDao {
    private static Set<String> BAN_LIST_ADD_LOGIN;
    private final MainConfig config;
    private final ConnectionManager connectionManager;
    private final UserRowMapper userEntityRowMapper;

    public UserJdbcRepository(MainConfig config) {
        BAN_LIST_ADD_LOGIN = config.getBanListAddLogin();
        this.config = config;
        this.connectionManager = new ConnectionManagerImpl(config);
        this.userEntityRowMapper = UserRowMapper.getInstance();
    }

    @Override
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        log.debug("Пришел запрос на добавление нового пользователя {}", userEntity);
        if (containsLoginInBanList(userEntity.getLogin())) {
            log.warn("Запрещено регистрировать пользователя с таким логином");
            throw new BadLoginException("New User");
        }
        Connection connection = connectionManager.getConnection();
        String queryCreate = String.format("""
                        INSERT INTO %s.user (login, password, id_group)
                        VALUES (?,?,?) 
                        RETURNING ID""",
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryCreate)) {
            statement.setString(1, userEntity.getLogin());
            statement.setString(2, userEntity.getPassword());
            statement.setInt(3, userEntity.getIdGroupPermissions());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                long idKeyHolder = resultSet.getLong(1);
                userEntity.setId(idKeyHolder);
                log.debug("Пользователю присвоен новый ID={}", idKeyHolder);
            } else {
                log.error("ID пользователю не присвоен!");
            }

            log.debug("В БД добавлен новый пользователь {}", userEntity);
            return Optional.of(userEntity);
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не сохранила нового пользователя. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> updateUser(UserEntity userEntity) {
        log.debug("Пришел пользователь на обновление {}", userEntity);
        Long userId = userEntity.getId();
        Optional<UserEntity> oldUser = findByUserId(userId);
        if (oldUser.isPresent()) {
            log.debug("Пользователь есть в системе, продолжаем обновление");
        } else {
            log.warn("Пользователя нет с таким ID userId={}", userId);
            return Optional.empty();
        }

        Connection connection = connectionManager.getConnection();
        String queryUpdate = String.format("""
                        UPDATE %s.user 
                           SET login = ?, password = ?, id_group = ? 
                         WHERE id = ?""",
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryUpdate)) {
            statement.setString(1, userEntity.getLogin());
            statement.setString(2, userEntity.getPassword());
            statement.setInt(3, userEntity.getIdGroupPermissions());
            statement.setLong(4, userId);

            if (statement.executeUpdate() > 0) {
                log.debug("В БД произошло обновление. Старые данные: {}\n Новые данные: {}\n", oldUser, userEntity);
                return Optional.of(userEntity);
            } else {
                return Optional.empty();
            }
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не обновила существующего пользователя. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        log.debug("Пришел запрос на получение данных пользователя по ID={}", userId);
        Connection connection = connectionManager.getConnection();
        String sqlFindUser = String.format("""
                        SELECT *
                          FROM %s.user 
                         WHERE id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userEntityRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД такого пользователя нет");
                return Optional.empty();
            }
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла пользователя по ID. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findByUserLogin(String userLogin) {
        log.debug("Пришел запрос на получение данных пользователя по login={}", userLogin);
        Connection connection = connectionManager.getConnection();
        String sqlFindUser = String.format("""
                        SELECT *
                          FROM %s.user 
                         WHERE login = ?""",
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {
            statement.setString(1, userLogin);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(userEntityRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД такого пользователя нет");
                return Optional.empty();
            }
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла пользователя по логину. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        log.debug("Пришел запрос на получение данных всех пользователей");
        Connection connection = connectionManager.getConnection();
        String sqlFindUser = String.format("""
                        SELECT *
                          FROM %s.user """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindUser)) {
            ResultSet resultSet = statement.executeQuery();
            List<UserEntity> listUsersFromBD = new ArrayList<>();

            while (resultSet.next()) {
                UserEntity userEntity = userEntityRowMapper.mapRow(resultSet);
                listUsersFromBD.add(userEntity);
            }
            log.debug("Найден список пользователей count={}", listUsersFromBD.size());
            return listUsersFromBD;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла список пользователей. Смотрите ошибки выше.");
            return Collections.emptyList();
        }
    }

    private boolean containsLoginInBanList(String userLogin) {
        return BAN_LIST_ADD_LOGIN.stream().anyMatch(userLogin::equalsIgnoreCase);
    }
}
