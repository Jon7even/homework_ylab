package com.github.jon7even.configuration.database;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Класс хранящий настройки из файла application.yaml является аналогом @ConfigurationProperties
 *
 * @author Jon7even
 * @version 1.0
 */
@Getter
@Setter
@Builder
public final class MainConfig {
    private String BD_USER;
    private String BD_PASSWORD;
    private String BD_DRIVER;
    private String BD_SOURCE_URL;
    private String LIQUIBASE_SCHEMA;
    private String MAIN_SCHEMA;
    private String LIQUIBASE_CHANGE_LOG;
    private Set<String> BAN_LIST_ADD_LOGIN;
}
