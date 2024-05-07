package com.github.jon7even.services;

import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.dto.permission.GroupPermissionsServiceDto;

/**
 * Интерфейс для взаимодействия с группами разрешений и установки флагов для определенных групп пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface GroupPermissionsService {
    /**
     * Метод, который даёт однозначный ответ - разрешен ли доступ определенного пользователя к определенному сервису
     *
     * @param requesterId существующий ID пользователя для идентификации
     * @param groupPermissionsId существующий ID группы, к которой пользователь относится
     * @param nameTypeServiceId  существующий ID сервиса
     * @param flag какое действие необходимо проверить
     * @return boolean с ответом - разрешен ли доступ
     */
    Boolean getPermissionForService(Long requesterId,
                                     Integer groupPermissionsId,
                                     Integer nameTypeServiceId,
                                     FlagPermissions flag);

    /**
     * Метод, который отдает DTO со всеми флагами FlagPermissions для определенного пользователя к определенному сервису
     *
     * @param groupPermissionsId существующий ID группы, к которой пользователь относится
     * @param nameTypeServiceId  существующий ID сервиса
     * @return определенную группу с разрешениями для определенного сервиса
     */
    GroupPermissionsServiceDto getPermissionsForServices(Integer groupPermissionsId, Integer nameTypeServiceId);
}
