package com.github.jon7even.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;
import com.github.jon7even.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.dataproviders.core.mapper.DiaryRowMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Реализация репозитория дневника с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
public class DiaryJdbcRepository implements DiaryDao {
    private final MainConfig config;
    private final ConnectionManager connectionManager;
    private final DiaryRowMapper diaryRowMapper;

    public DiaryJdbcRepository(MainConfig config) {
        this.config = config;
        this.connectionManager = new ConnectionManagerImpl(config);
        this.diaryRowMapper = DiaryRowMapper.getInstance();
    }

    @Override
    public Optional<DiaryEntity> createDiary(DiaryEntity diaryEntity) {
        log.debug("Пришел запрос на создание нового дневника {}", diaryEntity);
        Connection connection = connectionManager.getConnection();
        String queryCreateDiary = String.format("""
                        INSERT INTO %s.diary (user_id, weight, growth, created_on, updated_on) 
                        VALUES (?,?,?,?,?) 
                        RETURNING ID """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryCreateDiary)) {
            statement.setLong(1, diaryEntity.getUserId());
            statement.setFloat(2, diaryEntity.getWeightUser());
            statement.setFloat(3, diaryEntity.getGrowthUser());
            statement.setObject(4, diaryEntity.getCreatedOn());
            statement.setObject(5, diaryEntity.getUpdatedOn());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                long idKeyHolder = resultSet.getLong(1);
                diaryEntity.setId(idKeyHolder);
                log.debug("Дневнику присвоен новый ID={}", idKeyHolder);
            } else {
                log.error("ID дневнику не присвоен");
            }

            log.debug("В БД добавлен новый дневник {}", diaryEntity);
            return Optional.of(diaryEntity);
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не сохранила нового пользователя. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<DiaryEntity> updateDiary(DiaryEntity diaryEntity) {
        log.debug("Пришел запрос на обновление дневника {}", diaryEntity);
        Long diaryId = diaryEntity.getId();
        Optional<DiaryEntity> oldDiary = findByUserId(diaryId);
        if (oldDiary.isPresent()) {
            log.debug("Дневник есть в системе, продолжаем обновление");
        } else {
            log.warn("Дневника нет с таким userId={}", diaryId);
            return Optional.empty();
        }

        Connection connection = connectionManager.getConnection();
        String queryUpdateDiary = String.format("""
                        UPDATE %s.diary 
                           SET user_id = ?, weight = ?, growth = ?, created_on = ?, updated_on = ?
                         WHERE id = ?""",
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryUpdateDiary)) {
            statement.setLong(1, diaryEntity.getUserId());
            statement.setFloat(2, diaryEntity.getWeightUser());
            statement.setFloat(3, diaryEntity.getGrowthUser());
            statement.setObject(4, diaryEntity.getCreatedOn());
            statement.setObject(5, diaryEntity.getUpdatedOn());
            statement.setLong(6, diaryId);

            if (statement.executeUpdate() > 0) {
                log.debug("В БД произошло обновление. Старые данные: {}\n Новые данные: {}\n", oldDiary, diaryEntity);
                return Optional.of(diaryEntity);
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
            log.error("БД не обновила существующего пользователя. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<DiaryEntity> findByDiaryId(Long diaryId) {
        log.debug("Пришел запрос на получение данных дневника по diaryId={}", diaryId);
        Connection connection = connectionManager.getConnection();
        String sqlFindDiaryById = String.format("""
                        SELECT *
                          FROM %s.diary 
                         WHERE id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindDiaryById)) {
            statement.setLong(1, diaryId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(diaryRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД такого дневника нет");
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
            log.error("БД не нашла пользователя по diaryId. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<DiaryEntity> findByUserId(Long userId) {
        log.debug("Пришел запрос на получение данных дневника по userId={}", userId);
        Connection connection = connectionManager.getConnection();
        String sqlFindDiaryById = String.format("""
                        SELECT *
                          FROM %s.diary 
                         WHERE user_id = ? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindDiaryById)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(diaryRowMapper.mapRow(resultSet));
            } else {
                log.warn("В БД такого дневника нет");
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
            log.error("БД не нашла пользователя по userId. Смотрите ошибки выше.");
            return Optional.empty();
        }
    }
}
