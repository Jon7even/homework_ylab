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
                .bdUser(container.getUsername())
                .bdPassword(container.getPassword())
                .bdDriver(container.getDriverClassName())
                .bdSourceUrl(container.getJdbcUrl())
                .liquibaseSchema(configLoader.getConfig().getLiquibaseSchema())
                .mainSchema(configLoader.getConfig().getMainSchema())
                .liquibaseChangeLog(configLoader.getConfig().getLiquibaseChangeLog())
                .banListAddLogin(configLoader.getConfig().getBanListAddLogin())
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
