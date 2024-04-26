package com.github.jon7even.dataproviders.configuration;

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
    private String bdUser;
    private String bdPassword;
    private String bdDriver;
    private String bdSourceUrl;
    private String liquibaseSchema;
    private String mainSchema;
    private String liquibaseChangeLog;
    private Set<String> banListAddLogin;
}
