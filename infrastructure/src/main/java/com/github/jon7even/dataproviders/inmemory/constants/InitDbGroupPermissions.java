package com.github.jon7even.dataproviders.inmemory.constants;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.github.jon7even.dataproviders.inmemory.constants.InitialCommonDataInDb.*;

/**
 * Утилитарный файл констант инициализации необходимых данных в БД групп разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitDbGroupPermissions {

    public final static TypeServiceEntity HISTORY_TO_ADMIN = TypeServiceEntity.builder()
            .id(1)
            .nameType(SERVICE_HISTORY)
            .update(true)
            .write(true)
            .read(true)
            .delete(true)
            .build();

    public final static TypeServiceEntity DIARY_TO_ADMIN = TypeServiceEntity.builder()
            .id(2)
            .nameType(SERVICE_DIARY)
            .update(true)
            .write(true)
            .read(true)
            .delete(true)
            .build();

    public final static TypeServiceEntity WORKOUT_TO_ADMIN = TypeServiceEntity.builder()
            .id(3)
            .nameType(SERVICE_WORKOUT)
            .update(true)
            .write(true)
            .read(true)
            .delete(true)
            .build();

    public final static TypeServiceEntity WORKOUT_TYPE_TO_ADMIN = TypeServiceEntity.builder()
            .id(4)
            .nameType(SERVICE_TYPE_WORKOUT)
            .update(true)
            .write(true)
            .read(true)
            .delete(true)
            .build();

    public final static TypeServiceEntity USER_TO_ADMIN = TypeServiceEntity.builder()
            .id(5)
            .nameType(SERVICE_USER)
            .update(true)
            .write(true)
            .read(true)
            .delete(true)
            .build();

    public final static TypeServiceEntity HISTORY_TO_USER = TypeServiceEntity.builder()
            .id(6)
            .nameType(SERVICE_HISTORY)
            .update(false)
            .write(false)
            .read(false)
            .delete(false)
            .build();

    public final static TypeServiceEntity DIARY_TO_USER = TypeServiceEntity.builder()
            .id(7)
            .nameType(SERVICE_DIARY)
            .update(false)
            .write(false)
            .read(false)
            .delete(false)
            .build();

    public final static TypeServiceEntity WORKOUT_TO_USER = TypeServiceEntity.builder()
            .id(8)
            .nameType(SERVICE_WORKOUT)
            .update(false)
            .write(false)
            .read(false)
            .delete(false)
            .build();

    public final static TypeServiceEntity WORKOUT_TYPE_TO_USER = TypeServiceEntity.builder()
            .id(9)
            .nameType(SERVICE_TYPE_WORKOUT)
            .update(false)
            .write(false)
            .read(false)
            .delete(false)
            .build();

    public final static TypeServiceEntity USER_TO_USER = TypeServiceEntity.builder()
            .id(10)
            .nameType(SERVICE_USER)
            .update(false)
            .write(false)
            .read(false)
            .delete(false)
            .build();

    public final static Set<TypeServiceEntity> BASE_LIST_PERMISSIONS_FOR_ADMIN = new HashSet<>(
            Set.of(HISTORY_TO_ADMIN, DIARY_TO_ADMIN, WORKOUT_TO_ADMIN, WORKOUT_TYPE_TO_ADMIN, USER_TO_ADMIN)
    );
    public final static Set<TypeServiceEntity> BASE_LIST_PERMISSIONS_FOR_USER = new HashSet<>(
            Set.of(HISTORY_TO_USER, DIARY_TO_USER, WORKOUT_TO_USER, WORKOUT_TYPE_TO_USER, USER_TO_USER)
    );

    public final static GroupPermissionsEntity BASE_GROUP_PERMISSIONS_FOR_ADMIN = GroupPermissionsEntity.builder()
            .id(DEFAULT_ID_GROUP_PERMISSIONS_ADMIN)
            .name(DEFAULT_NAME_GROUP_PERMISSIONS_ADMIN)
            .servicesList(BASE_LIST_PERMISSIONS_FOR_ADMIN)
            .build();

    public final static GroupPermissionsEntity BASE_GROUP_PERMISSIONS_FOR_USER = GroupPermissionsEntity.builder()
            .id(DEFAULT_ID_GROUP_PERMISSIONS_USER)
            .name(DEFAULT_NAME_GROUP_PERMISSIONS_USER)
            .servicesList(BASE_LIST_PERMISSIONS_FOR_USER)
            .build();
}
