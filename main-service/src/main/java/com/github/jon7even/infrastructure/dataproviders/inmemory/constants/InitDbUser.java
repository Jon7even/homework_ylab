package com.github.jon7even.infrastructure.dataproviders.inmemory.constants;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.*;

/**
 * Утилитарный файл констант инициализации необходимых данных в БД пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitDbUser {
    public final static UserEntity ADMIN_FIRST_USER = UserEntity.builder()
            .id(DEFAULT_ID_ADMIN)
            .login(ADMIN_LOGIN)
            .password(ADMIN_PASSWORD)
            .idGroupPermissions(DEFAULT_ID_GROUP_PERMISSIONS_ADMIN)
            .build();
}