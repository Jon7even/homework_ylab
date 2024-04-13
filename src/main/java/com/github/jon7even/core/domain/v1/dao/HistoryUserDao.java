package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс DAO для аудита действий пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public interface HistoryUserDao {
    /**
     * Метод для сохранения действия пользователя
     *
     * @param historyUserEntity новое действие пользователя без ID
     * @return созданное действие пользователя со сгенерированным ID
     */
    Optional<HistoryUserEntity> createHistoryOfUser(HistoryUserEntity historyUserEntity);

    /**
     * Метод для поиска действий пользователя по ID пользователя
     *
     * @param userId существующий ID пользователя
     * @return вся история действия пользователя без параметров
     */
    List<HistoryUserEntity> findAllHistoryByUserId(Long userId);
}
