package com.github.jon7even.infrastructure.dataproviders.inmemory.constants;

import com.github.jon7even.core.domain.v1.entities.permissions.NameType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный файл констант инициализации необходимых данных в БД для нормальной работы приложения
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitialCommonDataInDb {
    public static final Long DEFAULT_ID_ADMIN = 1L;
    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    public static final String HOME = System.getProperty("user.dir") + "/src/main/resources/";

    public static final Integer DEFAULT_ID_GROUP_PERMISSIONS_ADMIN = 1;
    public static final Integer DEFAULT_ID_GROUP_PERMISSIONS_USER = 2;
    public static final String DEFAULT_NAME_GROUP_PERMISSIONS_ADMIN = "Admin";
    public static final String DEFAULT_NAME_GROUP_PERMISSIONS_USER = "User";

    public final static NameType SERVICE_HISTORY = NameType.builder()
            .id(1)
            .name("History Service")
            .build();
    public final static NameType SERVICE_DIARY = NameType.builder()
            .id(2)
            .name("Diary Service")
            .build();
    public final static NameType SERVICE_WORKOUT = NameType.builder()
            .id(3)
            .name("Workout Service")
            .build();
    public final static NameType SERVICE_TYPE_WORKOUT = NameType.builder()
            .id(4)
            .name("TypeWorkout Service")
            .build();

    public final static NameType SERVICE_USER = NameType.builder()
            .id(5)
            .name("User Service")
            .build();
}
