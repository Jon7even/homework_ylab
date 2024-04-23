package com.github.jon7even.infrastructure.dataproviders.jdbc;

import com.github.jon7even.configuration.database.MainConfig;
import com.github.jon7even.configuration.database.impl.ConfigLoaderImpl;
import com.github.jon7even.infrastructure.dataproviders.core.LiquibaseManager;
import com.github.jon7even.infrastructure.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.infrastructure.dataproviders.core.impl.LiquibaseManagerImpl;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
public class ContainersSetup extends PreparationForTests {
    protected static UserJdbcRepository userJdbcRepository;
    protected static MainConfig mainConfig;
    protected static LiquibaseManager liquibaseManager;
    @Container
    private static final Postgres16TC container = Postgres16TC.getInstance();

    @BeforeAll
    protected static void setUpContainers() {
        ConfigLoaderImpl configLoader = ConfigLoaderImpl.getInstance();
        mainConfig = MainConfig.builder()
                .BD_USER(container.getUsername())
                .BD_PASSWORD(container.getPassword())
                .BD_DRIVER(container.getDriverClassName())
                .BD_SOURCE_URL(container.getJdbcUrl())
                .LIQUIBASE_SCHEMA(configLoader.getConfig().getLIQUIBASE_SCHEMA())
                .MAIN_SCHEMA(configLoader.getConfig().getMAIN_SCHEMA())
                .LIQUIBASE_CHANGE_LOG(configLoader.getConfig().getLIQUIBASE_CHANGE_LOG())
                .BAN_LIST_ADD_LOGIN(configLoader.getConfig().getBAN_LIST_ADD_LOGIN())
                .build();

        userJdbcRepository = new UserJdbcRepository(mainConfig);
        liquibaseManager = LiquibaseManagerImpl.getInstance();
        initSchemaForTests();
    }

    private static void initSchemaForTests() {
        ConfigLoaderImpl configLoader = ConfigLoaderImpl.getInstance();
        String sqlQuerySchema = String.format("CREATE SCHEMA IF NOT EXISTS %s;"
                        + "CREATE SCHEMA IF NOT EXISTS %s;",
                configLoader.getConfig().getLIQUIBASE_SCHEMA(),
                configLoader.getConfig().getMAIN_SCHEMA()
        );
        Connection connection = new ConnectionManagerImpl(mainConfig).getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuerySchema);
            System.out.println("Подготовка Schema для тестов закончена");
        } catch (SQLException exception) {
            System.out.println("Что-то пошло не так с инициализацией Schema " + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @AfterEach
    protected void stopTests() {
        container.stop();
    }

    @BeforeEach
    protected void setUpBd() {
        liquibaseManager.initMigrate();
    }

    @AfterEach
    protected void cleanUpBd() {
        liquibaseManager.dropAll();
    }
}
