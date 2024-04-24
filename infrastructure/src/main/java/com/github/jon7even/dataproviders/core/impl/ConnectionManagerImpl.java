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
    private static Connection connection;
    private final MainConfig config;

    public ConnectionManagerImpl(MainConfig config) {
        this.config = config;
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        config.getBdSourceUrl(),
                        config.getBdUser(),
                        config.getBdPassword()
                );
                System.out.println("Соединение с БД установлено");
            } catch (SQLException exception) {
                System.out.println("С соединением пошло что-то не так");
                throw new DataBaseException(exception.getMessage());
            }
        }
        return connection;
    }
}
