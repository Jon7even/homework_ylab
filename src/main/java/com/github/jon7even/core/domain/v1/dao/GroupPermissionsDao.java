package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс DAO для групп разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
public interface GroupPermissionsDao {
    /**
     * Метод для создания новой группы разрешений
     *
     * @param groupPermissionsEntity новая группа с разрешениями без ID
     * @return новая группа с разрешениями со сгенерированным ID
     */
    Optional<GroupPermissionsEntity> createGroupPermissions(GroupPermissionsEntity groupPermissionsEntity);

    /**
     * Метод для обновления существующей группы с разрешениями
     *
     * @param groupPermissionsEntity существующая группа с разрешениями с ID
     * @return обновленная группа с разрешениями, если он есть в системе
     */
    Optional<GroupPermissionsEntity> updateGroupPermissions(GroupPermissionsEntity groupPermissionsEntity);

    /**
     * Метод для поиска определенной группы по ID с разрешениями и всеми имеющимися сервисами
     *
     * @param groupPermissionsId существующий ID группы с разрешениями
     * @return определенную группу с разрешениями для всех сервисов, если она есть в системе
     */
    Optional<GroupPermissionsEntity> findByGroupPermissionsId(Integer groupPermissionsId);

    /**
     * Метод для поиска определенной группы по ID с разрешениями определенного сервиса
     *
     * @param groupPermissionsId существующий ID группы с разрешениями
     * @param nameTypeServiceId существующий ID сервиса
     * @return определенную группу с разрешениями для определенного сервиса
     */
    Optional<GroupPermissionsEntity> findByGroupPermissionsIdAndByTypeServiceId(Integer groupPermissionsId,
                                                                                Integer nameTypeServiceId);

    /**
     * Метод для поиска всех групп с разрешениями и всеми имеющимися сервисами
     *
     * @return список всех групп с разрешениями для всех сервисов
     */
    List<GroupPermissionsEntity> getAllGroupsOfPermissions();
}
