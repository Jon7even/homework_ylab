package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.TypeWorkoutEntity;

import java.util.Optional;

/**
 * Интерфейс DAO для типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public interface TypeWorkoutDao {
    /**
     * Метод для создания нового типа тренировки
     *
     * @param typeWorkoutEntity новый тип тренировки без ID
     * @return новый тип тренировки со сгенерированным ID
     */
    Optional<TypeWorkoutEntity> createTypeWorkout(TypeWorkoutEntity typeWorkoutEntity);

    /**
     * Метод для обновления существующего типа тренировки
     *
     * @param typeWorkoutEntity существующий тип тренировки
     * @return обновленный тип тренировки, если он есть в системе
     */
    Optional<TypeWorkoutEntity> updateTypeWorkout(TypeWorkoutEntity typeWorkoutEntity);

    /**
     * Метод для поиска типа тренировки по ID
     *
     * @param typeWorkoutId существующий ID типа тренировки
     * @return тип тренировки, если он есть в системе
     */
    Optional<TypeWorkoutEntity> findByTypeWorkoutId(Long typeWorkoutId);
}
