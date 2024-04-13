package com.github.jon7even.infrastructure.dataproviders.inmemory.constants;

import lombok.experimental.UtilityClass;

/**
 * Утилитарный файл констант инициализации необходимых данных в БД для нормальной работы приложения
 *
 * @author Jon7even
 * @version 1.0
 */
@UtilityClass
public class InitialDataInDb {
    public static final String ADMIN_LOGIN = "admin";

    public static final String ADMIN_PASSWORD = "admin";

    public static final Integer DEFAULT_GROUP_PERMISSIONS_ADMIN = 0;

    public static final Integer DEFAULT_GROUP_PERMISSIONS_USER = 1;

    public static final String HOME = System.getProperty("user.dir") + "/src/main/resources/";
}
