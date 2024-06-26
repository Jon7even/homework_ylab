package com.github.jon7even.dataproviders.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

/**
 * Утилитарный класс загружающий настройки из файла application.yaml для класса MainConfig
 *
 * @author Jon7even
 * @version 1.0
 */
public class ConfigLoader {
    private static ConfigLoader instance;
    private static MainConfig config;
    private static final String PROPERTIES = "/application.yaml";
    private static final String BAN_LIST = "/BanListAddLogin.properties";

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    private ConfigLoader() {
        try {
            Path pathBanList = Path.of(this.getClass().getResource(BAN_LIST).toURI());
            Set<String> banList = lines(get(pathBanList.toUri())).collect(Collectors.toSet());
            System.out.println("Бан-лист загружен: " + banList);

            Path pathProperties = Path.of(this.getClass().getResource(PROPERTIES).toURI());
            Properties properties = new Properties();
            properties.load(new FileInputStream(pathProperties.toFile()));
            System.out.println("Конфигурация загружена");

            config = MainConfig.builder()
                    .bdUser(properties.getProperty("BD_USER"))
                    .bdPassword(properties.getProperty("BD_PASSWORD"))
                    .bdDriver(properties.getProperty("BD_DRIVER"))
                    .bdSourceUrl(properties.getProperty("BD_SOURCE_URL"))
                    .liquibaseSchema(properties.getProperty("LIQUIBASE_SCHEMA"))
                    .mainSchema(properties.getProperty("MAIN_SCHEMA"))
                    .liquibaseChangeLog(properties.getProperty("LIQUIBASE_CHANGE_LOG"))
                    .banListAddLogin(banList)
                    .build();
        } catch (IOException e) {
            System.out.println("Что то пошло не так при загрузке конфигурационного файла");
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            System.out.println("Что то пошло не так при чтении пути");
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, предоставляющий доступ к главной конфигурации приложения
     *
     * @return MainConfig с загруженными данными из файлов
     */
    public MainConfig getConfig() {
        return config;
    }
}