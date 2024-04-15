package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.permission.GroupPermissionsServiceDto;
import com.github.jon7even.application.services.GroupPermissionsService;
import com.github.jon7even.core.domain.v1.dao.GroupPermissionsDao;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.mappers.GroupPermissionsMapper;
import com.github.jon7even.core.domain.v1.mappers.GroupPermissionsMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.GroupPermissionsRepository;

import java.util.Optional;

/**
 * Реализация сервиса для взаимодействия с группами разрешения
 *
 * @author Jon7even
 * @version 1.0
 */
public class GroupPermissionsServiceImpl implements GroupPermissionsService {
    private static GroupPermissionsServiceImpl instance;
    private final GroupPermissionsDao groupRepository;
    private final GroupPermissionsMapper groupPermissionsMapper;

    public static GroupPermissionsServiceImpl getInstance() {
        if (instance == null) {
            instance = new GroupPermissionsServiceImpl();
        }
        return instance;
    }

    private GroupPermissionsServiceImpl() {
        groupRepository = GroupPermissionsRepository.getInstance();
        groupPermissionsMapper = new GroupPermissionsMapperImpl();
    }

    @Override
    public boolean getPermissionsForService(Integer groupPermissionsId, Integer nameTypeServiceId,
                                            FlagPermissions flag) {
        System.out.println("Начинаем определять разрешения для пользователя из группы groupPermissionsId="
                + groupPermissionsId + " и сервиса с nameTypeServiceId=" + nameTypeServiceId
                + " с флагом разрешения flag=" + flag);
        GroupPermissionsEntity groupPermissionsFromBD = getGroupPermissionsEntityByGroupAndServiceId(
                groupPermissionsId, nameTypeServiceId
        );

        return isAllowed(groupPermissionsFromBD, flag);
    }

    @Override
    public GroupPermissionsServiceDto getPermissionsForService(Integer groupPermissionsId, Integer nameTypeServiceId) {
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

        Optional<GroupPermissionsEntity> foundGroup = groupRepository.findByGroupPermissionsIdAndByTypeServiceId(
                groupPermissionsId, nameTypeServiceId
        );

        if (foundGroup.isPresent()) {
            System.out.println("Группа по запросу найдена");
            return foundGroup.get();
        } else {
            System.out.println("Группа по запросу не найдена");
            throw new NotFoundException(
                    String.format("GroupPermissions by groupPermissionsId=%d and nameTypeServiceId=%d",
                            groupPermissionsId, nameTypeServiceId)
            );
        }
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
