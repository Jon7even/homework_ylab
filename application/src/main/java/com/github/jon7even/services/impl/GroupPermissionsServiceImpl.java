package com.github.jon7even.services.impl;

import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dao.GroupPermissionsDao;
import com.github.jon7even.core.domain.v1.dto.permission.GroupPermissionsServiceDto;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.mappers.GroupPermissionsMapper;
import com.github.jon7even.core.domain.v1.mappers.GroupPermissionsMapperImpl;
import com.github.jon7even.services.GroupPermissionsService;

/**
 * Реализация сервиса для взаимодействия с группами разрешения
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
public class GroupPermissionsServiceImpl implements GroupPermissionsService {
    private final GroupPermissionsDao groupRepository;
    private final GroupPermissionsMapper groupPermissionsMapper;

    public GroupPermissionsServiceImpl(GroupPermissionsDao groupPermissionsRepository) {
        this.groupRepository = groupPermissionsRepository;
        this.groupPermissionsMapper = new GroupPermissionsMapperImpl();
    }

    @Override
    public Boolean getPermissionForService(Long requesterId, Integer groupPermissionsId,
                                           Integer nameTypeServiceId, FlagPermissions flag) {
        System.out.println("Начинаем определять разрешение для пользователя с requesterId= " + requesterId
                + " из группы groupPermissionsId=" + groupPermissionsId
                + " и сервиса с nameTypeServiceId=" + nameTypeServiceId
                + " с флагом разрешения flag=" + flag);
        GroupPermissionsEntity groupPermissionsFromBD = getGroupPermissionsEntityByGroupAndServiceId(
                groupPermissionsId, nameTypeServiceId
        );
        return isAllowed(groupPermissionsFromBD, flag);
    }

    @Override
    public GroupPermissionsServiceDto getPermissionsForServices(Integer groupPermissionsId, Integer nameTypeServiceId) {
        System.out.println("Начинаем искать разрешения для пользователя из группы groupPermissionsId="
                + groupPermissionsId + " и сервиса с nameTypeServiceId=" + nameTypeServiceId);

        GroupPermissionsEntity groupPermissionsFromBD = getGroupPermissionsEntityByGroupAndServiceId(
                groupPermissionsId, nameTypeServiceId
        );
        System.out.println("Разрешения найдены: " + groupPermissionsFromBD + "\n передаю данные");

        return groupPermissionsMapper.toPermissionsServiceDtoFromEntity(
                groupPermissionsFromBD, getPermissionsForService(groupPermissionsFromBD)
        );
    }

    private GroupPermissionsEntity getGroupPermissionsEntityByGroupAndServiceId(Integer groupPermissionsId,
                                                                                Integer nameTypeServiceId) {
        System.out.println("Начинаю получать сущность GroupPermissionsEntity по заданным параметрам");

        return groupRepository.findByGroupPermissionsByIdAndByTypeServiceId(groupPermissionsId, nameTypeServiceId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("GroupPermissions by groupPermissionsId=%d and nameTypeServiceId=%d",
                                groupPermissionsId, nameTypeServiceId)
                ));
    }

    private boolean isAllowed(GroupPermissionsEntity groupPermissions, FlagPermissions flag) {
        System.out.println("Проверяем имеет ли разрешение группа на флаг=" + flag);
        boolean result = false;
        TypeServiceEntity singleService = getPermissionsForService(groupPermissions);

        switch (flag) {
            case WRITE -> result = singleService.getWrite();
            case READ -> result = singleService.getRead();
            case UPDATE -> result = singleService.getUpdate();
            case DELETE -> result = singleService.getDelete();
        }
        System.out.println("Разрешение группы по флагу: " + result);
        return result;
    }

    private TypeServiceEntity getPermissionsForService(GroupPermissionsEntity groupPermissions) {
        System.out.println("Преобразуем Полученный список groupPermissions в первый результат");
        return groupPermissions.getServicesList().iterator().next();
    }
}
