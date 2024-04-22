package com.github.jon7even.infrastructure.dataproviders.core.impl;

import com.github.jon7even.configuration.database.ConfigLoader;
import com.github.jon7even.configuration.database.impl.ConfigLoaderImpl;
import com.github.jon7even.core.domain.v1.exception.DataBaseException;
import com.github.jon7even.infrastructure.dataproviders.core.ConnectionManager;

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
    private static ConnectionManagerImpl instance;
    private final ConfigLoader configLoader;

    public static ConnectionManagerImpl getInstance() {
        if (instance == null) {
            instance = new ConnectionManagerImpl();
        }
        return instance;
    }

    private ConnectionManagerImpl() {
        configLoader = ConfigLoaderImpl.getInstance();
    }

    @Override
    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    configLoader.getConfig().getBD_SOURCE_URL(),
                    configLoader.getConfig().getBD_USER(),
                    configLoader.getConfig().getBD_PASSWORD()
            );
            System.out.println("Соединение с БД установлено");
        } catch (SQLException exception) {
            System.out.println("С соединением пошло что-то не так");
            throw new DataBaseException(exception.getMessage());
        }
        return connection;
    }
}
