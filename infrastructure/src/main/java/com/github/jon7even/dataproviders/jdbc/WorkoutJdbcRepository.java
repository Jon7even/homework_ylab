package com.github.jon7even.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.dao.WorkoutDao;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import com.github.jon7even.core.domain.v1.exception.NotDeleteException;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;
import com.github.jon7even.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.dataproviders.core.mapper.WorkoutRowMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Реализация репозитория тренировки с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
public class WorkoutJdbcRepository implements WorkoutDao {
    private final MainConfig config;
    private final ConnectionManager connectionManager;
    private final WorkoutRowMapper workoutRowMapper;

    public WorkoutJdbcRepository(MainConfig config) {
        this.config = config;
        this.connectionManager = new ConnectionManagerImpl(config);
        this.workoutRowMapper = WorkoutRowMapper.getInstance();
    }

    @Override
    public Optional<WorkoutEntity> createWorkout(WorkoutEntity workoutEntity) {
        log.debug("Пришел запрос на создание новой тренировки {}", workoutEntity);
        Connection connection = connectionManager.getConnection();
        String queryCreateWorkout = String.format("""
                        INSERT INTO %s.workout 
                               (diary_id, type_workout_id, start_on, end_on, time_rest, weight, note, detail) 
                        VALUES (?,?,?,?,?,?,?,?) 
                        RETURNING ID """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryCreateWorkout)) {
            statement.setLong(1, workoutEntity.getIdDiary());
            statement.setLong(2, workoutEntity.getIdTypeWorkout());
            statement.setObject(3, workoutEntity.getTimeStartOn());
            statement.setObject(4, workoutEntity.getTimeEndOn());
            statement.setLong(5, workoutEntity.getTimeOfRest().toMinutes());
            statement.setFloat(6, workoutEntity.getCurrentWeightUser());
            statement.setString(7, workoutEntity.getPersonalNote());
            statement.setString(8, workoutEntity.getDetailOfWorkout());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                long idKeyHolder = resultSet.getLong(1);
                workoutEntity.setId(idKeyHolder);
                log.debug("Тренировке присвоен новый ID={}", idKeyHolder);
            } else {
                log.error("ID тренировке не присвоен");
            }

            log.debug("В БД добавлена новая тренировки {}", workoutEntity);
            return Optional.of(workoutEntity);
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не сохранила новую тренировку. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<WorkoutEntity> updateWorkout(WorkoutEntity workoutEntity) {
        log.debug("Пришел запрос на обновление тренировки {}", workoutEntity);
        Long workoutId = workoutEntity.getId();
        Optional<WorkoutEntity> oldWorkout = findWorkoutByWorkoutId(workoutId);
        if (oldWorkout.isPresent()) {
            log.debug("Тренировка есть в системе, продолжаем обновление");
        } else {
            log.warn("Тренировки нет с таким workoutId={}", workoutId);
            return Optional.empty();
        }

        Connection connection = connectionManager.getConnection();
        String queryUpdateWorkout = String.format("""
                        UPDATE %s.workout 
                           SET diary_id = ?, type_workout_id = ?, start_on = ?, end_on = ?, 
                               time_rest = ?, weight = ?, note = ?, detail = ?
                         WHERE id = ?""",
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryUpdateWorkout)) {
            statement.setLong(1, workoutEntity.getIdDiary());
            statement.setLong(2, workoutEntity.getIdTypeWorkout());
            statement.setObject(3, workoutEntity.getTimeStartOn());
            statement.setObject(4, workoutEntity.getTimeEndOn());
            statement.setLong(5, workoutEntity.getTimeOfRest().toMinutes());
            statement.setFloat(6, workoutEntity.getCurrentWeightUser());
            statement.setString(7, workoutEntity.getPersonalNote());
            statement.setString(8, workoutEntity.getDetailOfWorkout());
            statement.setLong(9, workoutId);

            if (statement.executeUpdate() > 0) {
                log.debug("В БД произошло обновление. Старые данные: {}\n Новые данные: {}\n",
                        oldWorkout, workoutEntity);
                return Optional.of(workoutEntity);
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
            log.error("БД не обновила существующую тренировку. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<WorkoutEntity> findWorkoutByWorkoutId(Long workoutId) {
        log.debug("Пришел запрос на получение данных тренировки по workoutId={}", workoutId);
        Connection connection = connectionManager.getConnection();
        String sqlFindTypeWorkoutById = String.format("""
                        SELECT wt.id, wt.diary_id, wt.type_workout_id, wt.start_on,
                               wt.end_on, wt.time_rest, wt.weight, wt.note, wt.detail
                          FROM %s.workout AS wt
                         WHERE wt.id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindTypeWorkoutById)) {
            statement.setLong(1, workoutId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(workoutRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД такой тренировки нет");
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
            log.error("БД не нашла тренировку по workoutId. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<WorkoutEntity> findByWorkoutByTypeWorkoutIdAndDate(Long idTypeWorkout, LocalDate dayOfWorkout) {
        log.debug("Пришел запрос на получение данных тренировки по idTypeWorkout={} и dayOfWorkout={}",
                idTypeWorkout, dayOfWorkout);
        Connection connection = connectionManager.getConnection();
        String sqlFindWorkoutByTypeWorkoutAndDay = String.format("""
                        SELECT wt.id, wt.diary_id, wt.type_workout_id, wt.start_on,
                               wt.end_on, wt.time_rest, wt.weight, wt.note, wt.detail
                          FROM %s.workout AS wt
                         WHERE wt.type_workout_id = ? 
                           AND wt.start_on::date = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindWorkoutByTypeWorkoutAndDay)) {
            statement.setLong(1, idTypeWorkout);
            statement.setObject(2, dayOfWorkout);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(workoutRowMapper.mapRow(resultSet));
            } else {
                log.debug("В БД такой тренировки нет");
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
            log.error("БД не нашла тренировку по idTypeWorkout и dayOfWorkout. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public List<WorkoutEntity> findAllWorkoutByDiaryId(Long diaryId) {
        log.debug("Пришел запрос на получение всех тренировок по diaryId={}", diaryId);
        Connection connection = connectionManager.getConnection();
        String sqlFindWorkoutByDiaryId = String.format("""
                        SELECT wt.id, wt.diary_id, wt.type_workout_id, wt.start_on,
                               wt.end_on, wt.time_rest, wt.weight, wt.note, wt.detail
                          FROM %s.workout AS wt
                         WHERE wt.diary_id = ? """,
                config.getMainSchema());

        try (PreparedStatement statement = connection.prepareStatement(sqlFindWorkoutByDiaryId)) {
            statement.setLong(1, diaryId);

            ResultSet resultSet = statement.executeQuery();
            List<WorkoutEntity> listWorkoutByDiary = new ArrayList<>();

            while (resultSet.next()) {
                WorkoutEntity workoutEntity = workoutRowMapper.mapRow(resultSet);
                listWorkoutByDiary.add(workoutEntity);
            }
            log.debug("Найдены все тренировки в количестве count={}", listWorkoutByDiary.size());
            return listWorkoutByDiary;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла все тренировки. Смотрите ошибки выше.");
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteWorkoutByWorkoutId(Long workoutId) {
        log.debug("Пришел запрос на удаление тренировки по workoutId={}", workoutId);
        Optional<WorkoutEntity> oldWorkout = findWorkoutByWorkoutId(workoutId);
        if (oldWorkout.isEmpty()) {
            log.warn("Тренировки нет с таким workoutId={}", workoutId);
        } else {
            log.debug("Тренировка есть в системе, продолжаем удаление");
            Connection connection = connectionManager.getConnection();

            String queryDeleteWorkout = String.format("""
                            DELETE FROM %s.workout
                                  WHERE id = ? """,
                    config.getMainSchema());
            try (PreparedStatement statement = connection.prepareStatement(queryDeleteWorkout)) {
                statement.setLong(1, workoutId);

                if (statement.executeUpdate() > 0) {
                    log.debug("В БД произошло удаление тренировки {}", oldWorkout);
                } else {
                    log.error("В БД произошла ошибка, тренировку не смогли удалить {}", oldWorkout);
                    throw new NotDeleteException(String.format("Workout with workoutId=%d", workoutId));
                }
            } catch (SQLException exc) {
                SQLException throwables = exc;
                while (throwables != null) {
                    System.out.println("Сообщение ошибки: " + throwables.getMessage());
                    System.out.println("Статус ошибки: " + throwables.getSQLState());
                    System.out.println("Код ошибки: " + throwables.getErrorCode());
                    throwables = throwables.getNextException();
                }
                log.error("БД не удалила существующую тренировку. Смотрите ошибки выше.");
            }
        }
    }
}