package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.GroupPermissionsEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс DAO для групп разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
public interface GroupUserDao {
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
     * Метод для поиска определенной группы с разрешениями по ID
     *
     * @param groupPermissionsId существующий ID группы с разрешениями
     * @return определенную группу с разрешениями, если она есть в системе
     */
    Optional<GroupPermissionsEntity> findByGroupPermissionsId(Long groupPermissionsId);

    /**
     * Метод для поиска всех групп с разрешениями
     *
     * @return список всех групп с разрешениями
     */
    List<GroupPermissionsEntity> getAllGroupsOfPermissions();
}
