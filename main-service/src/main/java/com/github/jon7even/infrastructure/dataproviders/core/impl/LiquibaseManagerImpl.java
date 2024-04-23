package com.github.jon7even.infrastructure.dataproviders.core.impl;

import com.github.jon7even.configuration.database.ConfigLoader;
import com.github.jon7even.configuration.database.impl.ConfigLoaderImpl;
import com.github.jon7even.core.domain.v1.exception.DataBaseException;
import com.github.jon7even.infrastructure.dataproviders.core.LiquibaseManager;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Реализация сервиса для стартовых миграций Liquibase
 *
 * @author Jon7even
 * @version 1.0
 */
public class LiquibaseManagerImpl implements LiquibaseManager {
    private static LiquibaseManagerImpl instance;
    private final ConfigLoader configLoader;

    public static LiquibaseManagerImpl getInstance() {
        if (instance == null) {
            instance = new LiquibaseManagerImpl();
        }
        return instance;
    }

    private LiquibaseManagerImpl() {
        configLoader = ConfigLoaderImpl.getInstance();
    }

    @Override
    public void initMigrate() {
        Connection connection = new ConnectionManagerImpl(configLoader.getConfig()).getConnection();
        try {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(configLoader.getConfig().getMAIN_SCHEMA());
            database.setLiquibaseSchemaName(configLoader.getConfig().getLIQUIBASE_SCHEMA());
            Liquibase liquibase = new Liquibase(
                    configLoader.getConfig().getLIQUIBASE_CHANGE_LOG(), new ClassLoaderResourceAccessor(), database
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
        Connection connection = new ConnectionManagerImpl(configLoader.getConfig()).getConnection();
        try {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(configLoader.getConfig().getMAIN_SCHEMA());
            database.setLiquibaseSchemaName(configLoader.getConfig().getLIQUIBASE_SCHEMA());
            Liquibase liquibase = new Liquibase(
                    configLoader.getConfig().getLIQUIBASE_CHANGE_LOG(), new ClassLoaderResourceAccessor(), database
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
}
