package com.github.jon7even.infrastructure.dataproviders.core;

import java.sql.Connection;

/**
 * Интерфейс для работы с соединением к БД
 *
 * @author Jon7even
 * @version 1.0
 */
public interface ConnectionManager {
    /**
     * Метод, предоставляющий доступ к соединению с БД
     *
     * @return Connection с полученными настройками из конфигурации
     */
    Connection getConnection();
}
