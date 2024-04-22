package com.github.jon7even.infrastructure.dataproviders.jdbc;

import com.github.jon7even.configuration.database.ConfigLoader;
import com.github.jon7even.configuration.database.impl.ConfigLoaderImpl;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.infrastructure.dataproviders.core.ConnectionManager;
import com.github.jon7even.infrastructure.dataproviders.core.impl.ConnectionManagerImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Реализация репозитория пользователей с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
public class UserJdbcRepository implements UserDao {
    private static UserJdbcRepository instance;
    private static Set<String> BAN_LIST_ADD_LOGIN;
    private final ConnectionManager connectionManager;
    private final ConfigLoader configLoader;

    public static UserJdbcRepository getInstance() {
        if (instance == null) {
            instance = new UserJdbcRepository();
        }
        return instance;
    }

    private UserJdbcRepository() {
        configLoader = ConfigLoaderImpl.getInstance();
        BAN_LIST_ADD_LOGIN = configLoader.getConfig().getBAN_LIST_ADD_LOGIN();
        connectionManager = ConnectionManagerImpl.getInstance();
    }

    @Override
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        System.out.println("Пришел запрос на добавление нового пользователя" + userEntity);
        Connection connection = connectionManager.getConnection();
        String query = String.format("INSERT INTO %s.user (login, password, id_group)"
                + "     VALUES (?,?,?) RETURNING ID", configLoader.getConfig().getMAIN_SCHEMA());

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userEntity.getLogin());
            statement.setString(2, userEntity.getPassword());
            statement.setInt(3, userEntity.getIdGroupPermissions());
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            System.out.println("Что тут ?" + resultSet.toString());

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
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findByUserLogin(String userLogin) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }
}
