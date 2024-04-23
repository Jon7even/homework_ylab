package com.github.jon7even.dataproviders.inmemory.constants;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный файл констант инициализации необходимых данных в БД пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitDbUser {
    public final static UserEntity ADMIN_FIRST_USER = UserEntity.builder()
            .id(InitialCommonDataInDb.DEFAULT_ID_ADMIN)
            .login(InitialCommonDataInDb.ADMIN_LOGIN)
            .password(InitialCommonDataInDb.ADMIN_PASSWORD)
            .idGroupPermissions(InitialCommonDataInDb.DEFAULT_ID_GROUP_PERMISSIONS_ADMIN)
            .build();
}