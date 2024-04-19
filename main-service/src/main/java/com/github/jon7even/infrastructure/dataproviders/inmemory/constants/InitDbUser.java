package com.github.jon7even.infrastructure.dataproviders.inmemory.constants;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.*;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;

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

    public final static List<String> BAN_LIST_ADD_LOGIN;

    static {
        try {
            BAN_LIST_ADD_LOGIN = lines(get(HOME, "BanListAddLogin.properties")).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}