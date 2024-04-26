package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;

import java.util.Optional;

/**
 * Интерфейс DAO для групп разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
public interface GroupPermissionsDao {
    /**
     * Метод для поиска определенной группы по ID с разрешениями и всеми имеющимися сервисами
     *
     * @param groupPermissionsId существующий ID группы с разрешениями
     * @return определенную Entity группу с разрешениями для всех сервисов, если она есть в системе
     */
    Optional<GroupPermissionsEntity> findByGroupPermissionsId(Integer groupPermissionsId);

    /**
     * Метод для поиска определенной группы по ID с разрешениями определенного сервиса
     *
     * @param groupPermissionsId существующий ID группы с разрешениями
     * @param nameTypeServiceId  существующий ID сервиса
     * @return определенную Entity группу с разрешениями для определенного сервиса
     */
    Optional<GroupPermissionsEntity> findByGroupPermissionsByIdAndByTypeServiceId(Integer groupPermissionsId,
                                                                                  Integer nameTypeServiceId);
}
