package com.github.jon7even.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.dao.HistoryUserDao;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.core.ConnectionManager;
import com.github.jon7even.dataproviders.core.impl.ConnectionManagerImpl;
import com.github.jon7even.dataproviders.core.mapper.HistoryRowMapper;
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
 * Реализация аудита действий пользователя с помощью JDBC
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
public class HistoryUserJdbcRepository implements HistoryUserDao {
    private final MainConfig config;
    private final ConnectionManager connectionManager;
    private final HistoryRowMapper historyRowMapper;

    public HistoryUserJdbcRepository(MainConfig config) {
        this.config = config;
        this.connectionManager = new ConnectionManagerImpl(config);
        this.historyRowMapper = HistoryRowMapper.getInstance();
    }

    @Override
    public Optional<HistoryUserEntity> createHistoryOfUser(HistoryUserEntity historyUserEntity) {
        log.debug("Пришел запрос на добавление нового действия пользователя {}", historyUserEntity);
        Connection connection = connectionManager.getConnection();
        String queryCreate = String.format("""
                     INSERT INTO %s.history (user_id, created_on, event) 
                     VALUES (?,?,?) 
                     RETURNING ID """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(queryCreate)) {
            statement.setLong(1, historyUserEntity.getUserId());
            statement.setObject(2, historyUserEntity.getDateTimeOn());
            statement.setString(3, historyUserEntity.getEvent());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                long idKeyHolder = resultSet.getLong(1);
                historyUserEntity.setId(idKeyHolder);
                log.debug("Событию присвоен новый ID={}", idKeyHolder);
            } else {
                log.error("ID событию не присвоено!");
            }

            log.debug("В БД добавлено новое событие {}", historyUserEntity);
            return Optional.of(historyUserEntity);
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
    public List<HistoryUserEntity> findAllHistoryByUserId(Long userId) {
        log.debug("Пришел запрос на получение всей истории пользователя userId={}", userId);
        Connection connection = connectionManager.getConnection();
        String sqlFindAllHistoryById = String.format("""
                        SELECT *
                          FROM %s.history
                         WHERE user_id=? """,
                config.getMainSchema());
        try (PreparedStatement statement = connection.prepareStatement(sqlFindAllHistoryById)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<HistoryUserEntity> listHistoryFromBD = new ArrayList<>();

            while (resultSet.next()) {
                HistoryUserEntity historyUserEntity = historyRowMapper.mapRow(resultSet);
                listHistoryFromBD.add(historyUserEntity);
            }
            log.debug("Найдены события пользователя в количестве count={}", listHistoryFromBD.size());
            return listHistoryFromBD;
        } catch (SQLException exc) {
            SQLException throwables = exc;
            while (throwables != null) {
                System.out.println("Сообщение ошибки: " + throwables.getMessage());
                System.out.println("Статус ошибки: " + throwables.getSQLState());
                System.out.println("Код ошибки: " + throwables.getErrorCode());
                throwables = throwables.getNextException();
            }
            log.error("БД не нашла список пользователей. Смотрите ошибки выше.");
            return Collections.emptyList();
        }
    }
}
