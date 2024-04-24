package com.github.jon7even.dataproviders.core.impl;

import com.github.jon7even.core.domain.v1.exception.DataBaseException;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.LiquibaseManager;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Реализация сервиса для стартовых миграций Liquibase
 *
 * @author Jon7even
 * @version 1.0
 */
public class LiquibaseManagerImpl implements LiquibaseManager {
    private final MainConfig config;

    public LiquibaseManagerImpl(MainConfig config) {
        this.config = config;
    }

    @Override
    public void initMigrate() {
        initSchema();
        Connection connection = new ConnectionManagerImpl(config).getConnection();
        try {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(config.getMainSchema());
            database.setLiquibaseSchemaName(config.getLiquibaseSchema());
            Liquibase liquibase = new Liquibase(
                    config.getLiquibaseChangeLog(), new ClassLoaderResourceAccessor(), database
            );
            liquibase.update(new Contexts());
            connection.setAutoCommit(true);
            System.out.println("Миграции успешно выполнены");
        } catch (LiquibaseException exception) {
            System.out.println("С загрузкой миграций Liquibase пошло что-то не так");
            throw new DataBaseException(exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("С выполнением скриптов миграций пошло что-то не так");
            throw new DataBaseException(exception.getMessage());
        }
    }

    @Override
    public void dropAll() {
        Connection connection = new ConnectionManagerImpl(config).getConnection();
        try {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(config.getMainSchema());
            database.setLiquibaseSchemaName(config.getLiquibaseSchema());
            Liquibase liquibase = new Liquibase(
                    config.getLiquibaseChangeLog(), new ClassLoaderResourceAccessor(), database
            );
            liquibase.dropAll();
            connection.setAutoCommit(true);
            System.out.println("Произошел Liquibase откат в БД");
        } catch (LiquibaseException exception) {
            System.out.println("С откатом миграций Liquibase пошло что-то не так");
            throw new DataBaseException(exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("С выполнением отката миграций пошло что-то не так");
            throw new DataBaseException(exception.getMessage());
        }
    }

    private void initSchema() {
        String sqlQuerySchema = String.format("CREATE SCHEMA IF NOT EXISTS %s;"
                        + "CREATE SCHEMA IF NOT EXISTS %s;",
                config.getLiquibaseSchema(),
                config.getMainSchema()
        );
        Connection connection = new ConnectionManagerImpl(config).getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuerySchema);
            System.out.println("Загрузка предварительной Schema закончена");
        } catch (SQLException exception) {
            System.out.println("Что-то пошло не так с инициализацией Schema " + exception.getMessage());
            throw new DataBaseException(exception.getMessage());
        }
    }
}
