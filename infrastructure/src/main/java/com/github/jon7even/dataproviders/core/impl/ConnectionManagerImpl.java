package com.github.jon7even.dataproviders.core.impl;

import com.github.jon7even.core.domain.v1.exception.DataBaseException;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Реализация загрузчика соединений к БД
 *
 * @author Jon7even
 * @version 1.0
 */
public class ConnectionManagerImpl implements ConnectionManager {
    private static Connection connection = null;
    private final MainConfig config;

    public ConnectionManagerImpl(MainConfig config) {
        this.config = config;
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        config.getBdSourceUrl(),
                        config.getBdUser(),
                        config.getBdPassword()
                );
                System.out.println("Соединение с БД установлено");
            } catch (SQLException exc) {
                SQLException throwables = exc;
                while (throwables != null) {
                    System.out.println("Сообщение ошибки: " + throwables.getMessage());
                    System.out.println("Статус ошибки: " + throwables.getSQLState());
                    System.out.println("Код ошибки: " + throwables.getErrorCode());
                    throwables = throwables.getNextException();
                }
                throw new DataBaseException("С соединением пошло что-то не так. Смотрите ошибки выше.");
            } catch (ClassNotFoundException e) {
                throw new DataBaseException("С драйвером БД пошло что-то не так. Смотрите ошибки выше.");
            }
        }
        return connection;
    }
}
