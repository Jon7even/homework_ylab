package com.github.jon7even.configuration.database;

/**
 * Интерфейс для работы с загрузчиком конфигурации
 *
 * @author Jon7even
 * @version 1.0
 */
public interface ConfigLoader {
    /**
     * Метод, предоставляющий доступ к главной конфигурации приложения
     *
     * @return MainConfig с загруженными данными из файлов
     */
    MainConfig getConfig();
}
