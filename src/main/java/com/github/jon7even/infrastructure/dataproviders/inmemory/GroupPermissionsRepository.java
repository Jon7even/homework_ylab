package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.GroupPermissionsDao;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitDbGroupPermissions.BASE_GROUP_PERMISSIONS_FOR_ADMIN;
import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitDbGroupPermissions.BASE_GROUP_PERMISSIONS_FOR_USER;
import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.DEFAULT_ID_GROUP_PERMISSIONS_ADMIN;
import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.DEFAULT_ID_GROUP_PERMISSIONS_USER;

/**
 * Реализация репозитория разрешения групп
 *
 * @author Jon7even
 * @version 1.0
 */
public class GroupPermissionsRepository implements GroupPermissionsDao {
    private static GroupPermissionsRepository instance;
    private final Map<Integer, GroupPermissionsEntity> mapOfGroupsPermissions = new HashMap<>();
    private Integer idGenerator = 0;

    public static GroupPermissionsRepository getInstance() {
        if (instance == null) {
            instance = new GroupPermissionsRepository();
        }
        return instance;
    }

    private GroupPermissionsRepository() {
        ++idGenerator;
        mapOfGroupsPermissions.put(DEFAULT_ID_GROUP_PERMISSIONS_ADMIN, BASE_GROUP_PERMISSIONS_FOR_ADMIN);
        ++idGenerator;
        mapOfGroupsPermissions.put(DEFAULT_ID_GROUP_PERMISSIONS_USER, BASE_GROUP_PERMISSIONS_FOR_USER);
    }

    @Override
    public Optional<GroupPermissionsEntity> createGroupPermissions(GroupPermissionsEntity groupPermissionsEntity) {
        Integer groupPermissionsId = ++idGenerator;
        groupPermissionsEntity.setId(groupPermissionsId);
        mapOfGroupsPermissions.put(groupPermissionsId, groupPermissionsEntity);

        System.out.println("В БД добавлена новая группа разрешений: " + groupPermissionsEntity);
        return findByGroupPermissionsId(groupPermissionsId);
    }

    @Override
    public Optional<GroupPermissionsEntity> updateGroupPermissions(GroupPermissionsEntity groupPermissionsEntity) {
        Integer groupPermissionsId = groupPermissionsEntity.getId();
        GroupPermissionsEntity oldGroup;

        if (containsGroupPermissionsById(groupPermissionsId)) {
            oldGroup = mapOfGroupsPermissions.get(groupPermissionsId);
        } else {
            return Optional.empty();
        }

        mapOfGroupsPermissions.put(groupPermissionsId, groupPermissionsEntity);
        System.out.println("В БД произошло обновление. Старые данные: " + oldGroup
                + "\n Новые данные: " + groupPermissionsEntity);
        return findByGroupPermissionsId(groupPermissionsId);
    }

    @Override
    public Optional<GroupPermissionsEntity> findByGroupPermissionsId(Integer groupPermissionsId) {
        System.out.println("Ищу группу с groupPermissionsId=" + groupPermissionsId);
        return Optional.ofNullable(mapOfGroupsPermissions.get(groupPermissionsId));
    }

    @Override
    public Optional<GroupPermissionsEntity> findByGroupPermissionsIdAndByTypeServiceId(Integer groupPermissionsId,
                                                                                       Integer nameTypeServiceId) {
        System.out.println("Ищу группу с groupPermissionsId=" + groupPermissionsId
                + "и nameTypeServiceId=" + nameTypeServiceId);
        Optional<GroupPermissionsEntity> foundGroupWithAllType = findByGroupPermissionsId(groupPermissionsId);

        if (foundGroupWithAllType.isPresent()) {
            if (containsTypeServiceId(nameTypeServiceId)) {
                Optional<GroupPermissionsEntity> groupWithFilterType = Optional.of(
                        setNewSetOfService(foundGroupWithAllType.get(), nameTypeServiceId)
                );
                System.out.println("Возвращаю отфильтрованную группу: " + groupWithFilterType.get());
                return groupWithFilterType;
            } else {
                System.out.println("Группа с таким nameTypeServiceId не найдена");
                return Optional.empty();
            }
        } else {
            System.out.println("Группа с таким groupPermissionsId не найдена");
            return Optional.empty();
        }
    }

    @Override
    public List<GroupPermissionsEntity> getAllGroupsOfPermissions() {
        System.out.println("Получаю список всех групп разрешений");
        return mapOfGroupsPermissions.values().stream().toList();
    }

    private Boolean containsGroupPermissionsById(Integer groupPermissionsId) {
        System.out.println("Проверяем есть ли группа с groupPermissionsId=" + groupPermissionsId);
        return mapOfGroupsPermissions.containsKey(groupPermissionsId);
    }

    private Boolean containsTypeServiceId(Integer typeServiceId) {
        System.out.println("Проверяем есть ли такой сервис с typeServiceId=" + typeServiceId);
        return mapOfGroupsPermissions.values()
                .stream()
                .anyMatch(groupPermissions -> groupPermissions
                        .getServicesList()
                        .stream()
                        .anyMatch(typeServiceEntity -> typeServiceEntity.getNameType().getId().equals(typeServiceId)));
    }

    private GroupPermissionsEntity setNewSetOfService(GroupPermissionsEntity groupPermissionsNotFilter,
                                                      Integer typeServiceId) {
        System.out.println("Устанавливаю новый список типов группе" + groupPermissionsNotFilter);
        return GroupPermissionsEntity.builder()
                .id(groupPermissionsNotFilter.getId())
                .name(groupPermissionsNotFilter.getName())
                .servicesList(filterTypeServicesById(groupPermissionsNotFilter.getServicesList(), typeServiceId))
                .build();
    }

    private Set<TypeServiceEntity> filterTypeServicesById(Set<TypeServiceEntity> listOfType,
                                                          Integer nameTypeServiceId) {
        System.out.println("Фильтрую список сервисов по nameTypeServiceId=" + nameTypeServiceId);
        return listOfType.stream()
                .filter(typeServiceEntity -> typeServiceEntity.getNameType().getId().equals(nameTypeServiceId))
                .collect(Collectors.toSet());
    }
}