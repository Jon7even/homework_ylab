package com.github.jon7even.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

/**
 * Класс загружающий настройки из файла application.yaml для класса MainConfig
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigLoader {
    public static final MainConfig config;
    public static final String HOME = System.getProperty("user.dir") + "/src/main/resources/";
    public static final String PROPERTIES = "application.yaml";

    static {
        try {
            Set<String> banList = lines(get(HOME, "BanListAddLogin.properties")).collect(Collectors.toSet());
            Properties properties = new Properties();
            properties.load(new FileInputStream(HOME + PROPERTIES));

            config = MainConfig.builder()
                    .BD_USER(properties.getProperty("BD_USER"))
                    .BD_PASSWORD(properties.getProperty("BD_PASSWORD"))
                    .BD_DRIVER(properties.getProperty("BD_DRIVER"))
                    .BD_SOURCE_URL(properties.getProperty("BD_SOURCE_URL"))
                    .LIQUIBASE_SCHEMA(properties.getProperty("LIQUIBASE_SCHEMA"))
                    .LIQUIBASE_CHANGE_LOG(properties.getProperty("LIQUIBASE_CHANGE_LOG"))
                    .BAN_LIST_ADD_LOGIN(banList)
                    .build();
        } catch (IOException e) {
            System.out.println("Что то пошло не так при загрузке конфигурационного файла");
            throw new RuntimeException(e);
        }
    }

}
