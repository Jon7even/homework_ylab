package dataproviders.jdbc.setup;

import com.github.jon7even.dataproviders.configuration.ConfigLoader;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.LiquibaseManager;
import com.github.jon7even.dataproviders.core.impl.LiquibaseManagerImpl;
import com.github.jon7even.dataproviders.jdbc.UserJdbcRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import setup.PreparationForTests;

@Testcontainers
public class ContainersSetup extends PreparationForTests {
    protected static UserJdbcRepository userJdbcRepository;
    protected static MainConfig mainConfig;
    protected static ConfigLoader configLoader;
    protected static LiquibaseManager liquibaseManager;
    @Container
    private static final Postgres16TC container = Postgres16TC.getInstance();

    @BeforeAll
    protected static void setUpContainers() {
        configLoader = ConfigLoader.getInstance();
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
        liquibaseManager = new LiquibaseManagerImpl(mainConfig);
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
