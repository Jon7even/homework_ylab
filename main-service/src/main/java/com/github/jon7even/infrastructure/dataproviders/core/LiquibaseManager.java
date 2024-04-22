package com.github.jon7even.infrastructure.dataproviders.core;

/**
 * Интерфейс для работы с миграциями Liquibase
 *
 * @author Jon7even
 * @version 1.0
 */
public interface LiquibaseManager {
    /**
     * Метод, который производит необходимые миграции при старте приложения
     */
    void initMigrate();
}
