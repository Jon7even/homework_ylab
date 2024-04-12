package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.UserHistoryEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс DAO для истории
 *
 * @author Jon7even
 * @version 1.0
 */
public interface HistoryUserDao {
    /**
     * Метод для сохранения действия пользователя
     *
     * @param userHistoryEntity новое действие пользователя без ID
     * @return созданное действие пользователя со сгенерированным ID
     */
    Optional<UserHistoryEntity> createWorkout(UserHistoryEntity userHistoryEntity);

    /**
     * Метод для поиска действий пользователя по ID пользователя
     *
     * @param userId существующий ID пользователя
     * @return вся история действия пользователя без параметров
     */
    List<UserHistoryEntity> findAllHistoryByUserId(Long userId);
}
