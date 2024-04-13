package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.GroupPermissionsDao;
import com.github.jon7even.core.domain.v1.entities.GroupPermissionsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        if (containsGroupPermissionsById(groupPermissionsId)) {
            Optional<GroupPermissionsEntity> foundGroup = Optional.of(mapOfGroupsPermissions.get(groupPermissionsId));
            System.out.println("Найдена группа: " + foundGroup.get());
            return foundGroup;
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
}