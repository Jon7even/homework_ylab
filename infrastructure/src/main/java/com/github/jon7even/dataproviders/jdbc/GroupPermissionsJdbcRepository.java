package com.github.jon7even.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.dao.GroupPermissionsDao;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;
import com.github.jon7even.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.dataproviders.core.mapper.GroupPermissionsRowMapper;
import com.github.jon7even.dataproviders.core.mapper.TypeServiceRowMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Реализация репозитория разрешения групп с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
public class GroupPermissionsJdbcRepository implements GroupPermissionsDao {
    private final MainConfig config;
    private final ConnectionManager connectionManager;
    private final GroupPermissionsRowMapper groupPermissionsRowMapper;
    private final TypeServiceRowMapper typeServiceRowMapper;

    public GroupPermissionsJdbcRepository(MainConfig config) {
        this.config = config;
        this.connectionManager = new ConnectionManagerImpl(config);
        this.groupPermissionsRowMapper = GroupPermissionsRowMapper.getInstance();
        this.typeServiceRowMapper = TypeServiceRowMapper.getInstance();
    }

    @Override
    public Optional<GroupPermissionsEntity> findByGroupPermissionsId(Integer groupPermissionsId) {
        log.debug("Пришел запрос на получение данных по группе разрешений groupPermissionsId={}", groupPermissionsId);
        Connection connection = connectionManager.getConnection();
        String sqlFindPermissionsGroup = String.format("""
                        SELECT prms.id, prms.name
                          FROM %s.permission AS prms
                         WHERE prms.id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindPermissionsGroup)) {
            statement.setInt(1, groupPermissionsId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                GroupPermissionsEntity foundGroup = groupPermissionsRowMapper.mapRow(resultSet);
                foundGroup.setServicesList(getListTypeServiceByGroup(groupPermissionsId));
                return Optional.of(foundGroup);
            } else {
                log.warn("В БД такой группы нет");
                return Optional.empty();
            }
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла такую группу. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<GroupPermissionsEntity> findByGroupPermissionsByIdAndByTypeServiceId(Integer groupPermissionsId,
                                                                                         Integer nameTypeServiceId) {
        log.debug("Пришел запрос на получение данных по группе разрешений groupPermissionsId={} "
                + "и сервису nameTypeServiceId={}", groupPermissionsId, nameTypeServiceId);
        Connection connection = connectionManager.getConnection();
        String sqlFindPermissionsGroup = String.format("""
                        SELECT prms.id, prms.name
                          FROM %s.permission AS prms
                         WHERE prms.id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindPermissionsGroup)) {
            statement.setInt(1, groupPermissionsId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                GroupPermissionsEntity foundGroup = groupPermissionsRowMapper.mapRow(resultSet);
                foundGroup.setServicesList(getListTypeServiceByGroupAndService(groupPermissionsId, nameTypeServiceId));
                return Optional.of(foundGroup);
            } else {
                log.warn("В БД такой группы нет");
                return Optional.empty();
            }
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла такую группу. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    private Set<TypeServiceEntity> getListTypeServiceByGroupAndService(Integer groupPermissionsId,
                                                                       Integer nameTypeServiceId) {
        log.debug("Пришел запрос на получение списка разрешений по groupPermissionsId={} "
                + "и сервису nameTypeServiceId={}", groupPermissionsId, nameTypeServiceId);
        Connection connection = connectionManager.getConnection();
        String sqlFindListTypeServiceByGroupIdAndServiceId = String.format("""
                        SELECT ts.id, ns.id AS service_id, ns.name AS service_name,
                               ts.write, ts.read, ts.update, ts.delete
                          FROM %s.permission_type AS pt
                          LEFT
                               JOIN %s.type_service AS ts ON pt.type_service_id = ts.id
                          LEFT
                               JOIN %s.name_service AS ns ON ts.name_service_id = ns.id
                         WHERE pt.group_id = ?
                           AND ns.id = ? """,
                config.getMainSchema(), config.getMainSchema(), config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindListTypeServiceByGroupIdAndServiceId)) {
            statement.setInt(1, groupPermissionsId);
            statement.setInt(2, nameTypeServiceId);
            ResultSet resultSet = statement.executeQuery();
            Set<TypeServiceEntity> listTypeServiceEntity = new HashSet<>();

            while (resultSet.next()) {
                TypeServiceEntity typeServiceEntity = typeServiceRowMapper.mapRow(resultSet);
                listTypeServiceEntity.add(typeServiceEntity);
            }
            log.debug("Найдены разрешения в количестве count={}", listTypeServiceEntity.size());
            return listTypeServiceEntity;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла разрешений для группы по имени сервиса. Смотрите ошибки выше.");
            throw new NotFoundException(String.format("List TypeService with [groupId=%d] and [typeServiceId=%d]",
                    groupPermissionsId, nameTypeServiceId));
        }
    }

    private Set<TypeServiceEntity> getListTypeServiceByGroup(Integer groupPermissionsId) {
        log.debug("Пришел запрос на получение списка разрешений по groupPermissionsId={} ", groupPermissionsId);
        Connection connection = connectionManager.getConnection();
        String sqlFindListTypeServiceByGroupId = String.format("""
                        SELECT ts.id, ns.id AS service_id, ns.name AS service_name,
                               ts.write, ts.read, ts.update, ts.delete
                          FROM %s.permission_type AS pt
                          LEFT
                               JOIN %s.type_service AS ts ON pt.type_service_id = ts.id
                          LEFT
                               JOIN %s.name_service AS ns ON ts.name_service_id = ns.id
                         WHERE pt.group_id = ? """,
                config.getMainSchema(), config.getMainSchema(), config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindListTypeServiceByGroupId)) {
            statement.setInt(1, groupPermissionsId);
            ResultSet resultSet = statement.executeQuery();
            Set<TypeServiceEntity> listTypeServiceEntity = new HashSet<>();

            while (resultSet.next()) {
                TypeServiceEntity typeServiceEntity = typeServiceRowMapper.mapRow(resultSet);
                listTypeServiceEntity.add(typeServiceEntity);
            }
            log.debug("Найдены разрешения в количестве count={}", listTypeServiceEntity.size());
            return listTypeServiceEntity;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла разрешений для группы Смотрите ошибки выше.");
            throw new NotFoundException(String.format("List TypeService with [groupId=%d]", groupPermissionsId));
        }
    }
}