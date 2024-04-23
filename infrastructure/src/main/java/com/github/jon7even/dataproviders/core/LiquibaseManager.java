package com.github.jon7even.dataproviders.core;

/**
 * Интерфейс для работы с миграциями Liquibase
 *
 * @author Jon7even
 * @version 1.0
 */
public interface LiquibaseManager {
    /**
     * Метод для проведения миграций, при необходимости создает новую SCHEMA
     */
    void initMigrate();

    /**
     * Метод для удаления всей БД
     */
    void dropAll();
}
