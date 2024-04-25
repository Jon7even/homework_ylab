package com.github.jon7even.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.dao.TypeWorkoutDao;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;
import com.github.jon7even.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.dataproviders.core.mapper.DetailOfTypeWorkoutRowMapper;
import com.github.jon7even.dataproviders.core.mapper.TypeWorkoutRowMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Реализация репозитория типа тренировки с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
public class TypeWorkoutJdbcRepository implements TypeWorkoutDao {
    private final MainConfig config;
    private final ConnectionManager connectionManager;
    private final TypeWorkoutRowMapper typeWorkoutRowMapper;
    private final DetailOfTypeWorkoutRowMapper detailOfTypeWorkoutRowMapper;

    public TypeWorkoutJdbcRepository(MainConfig config) {
        this.config = config;
        this.connectionManager = new ConnectionManagerImpl(config);
        this.typeWorkoutRowMapper = TypeWorkoutRowMapper.getInstance();
        this.detailOfTypeWorkoutRowMapper = DetailOfTypeWorkoutRowMapper.getInstance();
    }

    @Override
    public Optional<TypeWorkoutEntity> createTypeWorkout(TypeWorkoutEntity typeWorkoutEntity) {
        log.debug("Пришел запрос на создание нового типа тренировки {}", typeWorkoutEntity);
        Connection connection = connectionManager.getConnection();
        String queryCreate = String.format("""
                        INSERT INTO %s.type_workout (type_name, calorie, detail_id) 
                        VALUES (?,?,?) 
                        RETURNING ID """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryCreate)) {
            statement.setString(1, typeWorkoutEntity.getTypeName());
            statement.setInt(2, typeWorkoutEntity.getCaloriePerHour());
            statement.setInt(3, typeWorkoutEntity.getDetailOfTypeWorkoutEntity().getId());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                long idKeyHolder = resultSet.getLong(1);
                typeWorkoutEntity.setId(idKeyHolder);
                log.debug("Типу тренировки присвоен новый ID={}", idKeyHolder);
            } else {
                log.error("ID типу тренировки не присвоен");
            }

            log.debug("В БД добавлен новый тип тренировки {}", typeWorkoutEntity);
            return Optional.of(typeWorkoutEntity);
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не сохранила новый тип тренировки. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TypeWorkoutEntity> updateTypeWorkout(TypeWorkoutEntity typeWorkoutEntity) {
        log.debug("Пришел запрос на обновление типа тренировки {}", typeWorkoutEntity);
        Long typeWorkoutId = typeWorkoutEntity.getId();
        Optional<TypeWorkoutEntity> oldTypeWorkout = findByTypeWorkoutId(typeWorkoutId);
        if (oldTypeWorkout.isPresent()) {
            log.debug("Тип тренировки есть в системе, продолжаем обновление");
        } else {
            log.warn("Типа тренировки нет с таким typeWorkoutId={}", typeWorkoutId);
            return Optional.empty();
        }

        Connection connection = connectionManager.getConnection();
        String queryUpdateTypeWorkout = String.format("""
                        UPDATE %s.type_workout 
                           SET type_name = ?, calorie = ?, detail_id = ?
                         WHERE id = ?""",
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryUpdateTypeWorkout)) {
            statement.setString(1, typeWorkoutEntity.getTypeName());
            statement.setInt(2, typeWorkoutEntity.getCaloriePerHour());
            statement.setInt(3, typeWorkoutEntity.getDetailOfTypeWorkoutEntity().getId());
            statement.setLong(4, typeWorkoutId);

            if (statement.executeUpdate() > 0) {
                log.debug("В БД произошло обновление. Старые данные: {}\n Новые данные: {}\n",
                        oldTypeWorkout, typeWorkoutEntity);
                return Optional.of(typeWorkoutEntity);
            } else {
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
            log.error("БД не обновила существующий тип тренировки. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<TypeWorkoutEntity> findByTypeWorkoutId(Long typeWorkoutId) {
        log.debug("Пришел запрос на получение данных типа тренировки по typeWorkoutId={}", typeWorkoutId);
        Connection connection = connectionManager.getConnection();
        String sqlFindTypeWorkoutById = String.format("""
                        SELECT tw.id, tw.type_name, tw.calorie, tw.detail_id, detail.name , detail.filling_required
                          FROM %s.type_workout AS tw
                          LEFT 
                               JOIN %s.detail_type AS detail ON tw.detail_id = detail.id
                         WHERE tw.id = ? """,
                config.getMainSchema(), config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindTypeWorkoutById)) {
            statement.setLong(1, typeWorkoutId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(typeWorkoutRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД такого типа тренировки нет");
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
            log.error("БД не нашла тип тренировки по typeWorkoutId. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public List<TypeWorkoutEntity> findAllTypeWorkoutsNoSort() {
        log.debug("Пришел запрос на получение всех возможных типов тренировки");
        Connection connection = connectionManager.getConnection();
        String sqlFindAllTypeWorkout = String.format("""
                        SELECT tw.id, tw.type_name, tw.calorie, tw.detail_id, detail.name , detail.filling_required
                          FROM %s.type_workout AS tw
                          LEFT 
                               JOIN %s.detail_type AS detail ON tw.detail_id = detail.id """,
                config.getMainSchema(), config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindAllTypeWorkout)) {
            ResultSet resultSet = statement.executeQuery();
            List<TypeWorkoutEntity> listTypeWorkout = new ArrayList<>();

            while (resultSet.next()) {
                TypeWorkoutEntity typeWorkoutEntity = typeWorkoutRowMapper.mapRow(resultSet);
                listTypeWorkout.add(typeWorkoutEntity);
            }
            log.debug("Найдены все типы тренировок в количестве count={}", listTypeWorkout.size());
            return listTypeWorkout;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла все возможные типы тренировки. Смотрите ошибки выше.");
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<DetailOfTypeWorkoutEntity> findDetailOfTypeWorkout(Integer detailOfTypeId) {
        log.debug("Пришел запрос на получение деталей тренировки по detailOfTypeId={}", detailOfTypeId);
        Connection connection = connectionManager.getConnection();
        String sqlFindDetailOfTypeWorkoutById = String.format("""
                        SELECT *
                          FROM %s.detail_type 
                         WHERE id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindDetailOfTypeWorkoutById)) {
            statement.setInt(1, detailOfTypeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(detailOfTypeWorkoutRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД нет такой детали типа тренировки");
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
            log.error("БД не нашла детали типа тренировки по detailOfTypeId. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public List<DetailOfTypeWorkoutEntity> findAllDetailOfTypeWorkoutNoSort() {
        log.debug("Пришел запрос на получение всех возможных деталей типа тренировки");
        Connection connection = connectionManager.getConnection();
        String sqlFindAllDetailOfTypeWorkout = String.format("""
                        SELECT *
                          FROM %s.detail_type """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindAllDetailOfTypeWorkout)) {
            ResultSet resultSet = statement.executeQuery();
            List<DetailOfTypeWorkoutEntity> listDetailOfTypeWorkout = new ArrayList<>();

            while (resultSet.next()) {
                DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntity = detailOfTypeWorkoutRowMapper.mapRow(resultSet);
                listDetailOfTypeWorkout.add(detailOfTypeWorkoutEntity);
            }
            log.debug("Найдены все детали для типов тренировок в количестве count={}", listDetailOfTypeWorkout.size());
            return listDetailOfTypeWorkout;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла все возможные детали для типа тренировки. Смотрите ошибки выше.");
            return Collections.emptyList();
        }
    }
}
