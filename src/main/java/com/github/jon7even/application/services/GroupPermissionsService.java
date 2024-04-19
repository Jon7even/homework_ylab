package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.permission.GroupPermissionsServiceDto;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;

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
     * @param groupPermissionsId существующий ID группы, к которой пользователь относится
     * @param nameTypeServiceId  существующий ID сервиса
     * @return boolean с ответом - разрешен ли доступ
     */
    boolean getPermissionsForService(Integer groupPermissionsId, Integer nameTypeServiceId, FlagPermissions flag);

    /**
     * Метод, который отдает DTO со всеми флагами FlagPermissions для определенного пользователя к определенному сервису
     *
     * @param groupPermissionsId существующий ID группы, к которой пользователь относится
     * @param nameTypeServiceId  существующий ID сервиса
     * @return определенную группу с разрешениями для определенного сервиса
     */
    GroupPermissionsServiceDto getPermissionsForService(Integer groupPermissionsId, Integer nameTypeServiceId);
}
