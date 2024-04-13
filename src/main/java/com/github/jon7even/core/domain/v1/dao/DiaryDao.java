package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;

import java.util.Optional;

/**
 * Интерфейс DAO для дневника
 *
 * @author Jon7even
 * @version 1.0
 */
public interface DiaryDao {
    /**
     * Метод для создания нового дневника
     *
     * @param diaryEntity новый дневник без ID
     * @return новый дневник со сгенерированным ID
     */
    Optional<DiaryEntity> createDiary(DiaryEntity diaryEntity);

    /**
     * Метод для обновления дневника
     *
     * @param diaryEntity существующий дневник
     * @return обновленный дневник, если он есть в системе
     */
    Optional<DiaryEntity> updateDiary(DiaryEntity diaryEntity);

    /**
     * Метод для поиска дневника по ID
     *
     * @param diaryId существующий ID дневника
     * @return дневник, если он есть в системе
     */
    Optional<DiaryEntity> findByDiaryId(Long diaryId);

    /**
     * Метод для поиска дневника по ID пользователя
     *
     * @param userId существующий ID пользователя
     * @return дневник относящийся к конкретному пользователю
     */
    Optional<DiaryEntity> findByUserId(Long userId);
}
