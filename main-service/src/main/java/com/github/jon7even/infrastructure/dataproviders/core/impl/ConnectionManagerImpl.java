package com.github.jon7even.infrastructure.dataproviders.core.impl;

import com.github.jon7even.configuration.database.MainConfig;
import com.github.jon7even.core.domain.v1.exception.DataBaseException;
import com.github.jon7even.infrastructure.dataproviders.core.ConnectionManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Реализация загрузчика соединений к БД
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
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
                        config.getBD_SOURCE_URL(),
                        config.getBD_USER(),
                        config.getBD_PASSWORD()
                );
                log.trace("Соединение с БД установлено");
            } catch (SQLException exception) {
                log.error("С соединением пошло что-то не так");
                throw new DataBaseException(exception.getMessage());
            }

        }
        return connection;
    }
}
