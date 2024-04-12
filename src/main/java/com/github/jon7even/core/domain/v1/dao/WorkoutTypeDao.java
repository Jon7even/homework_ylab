package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.WorkoutTypeEntity;

import java.util.Optional;

/**
 * Интерфейс DAO для типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public interface WorkoutTypeDao {
    /**
     * Метод для создания нового типа тренировки
     *
     * @param workoutTypeEntity новый тип тренировки без ID
     * @return новый тип тренировки со сгенерированным ID
     */
    Optional<WorkoutTypeEntity> createWorkoutType(WorkoutTypeEntity workoutTypeEntity);

    /**
     * Метод для обновления существующего типа тренировки
     *
     * @param workoutTypeEntity существующий тип тренировки
     * @return обновленный тип тренировки, если он есть в системе
     */
    Optional<WorkoutTypeEntity> updateWorkoutType(WorkoutTypeEntity workoutTypeEntity);

    /**
     * Метод для поиска типа тренировки по ID
     *
     * @param workoutTypeId существующий ID типа тренировки
     * @return тип тренировки, если он есть в системе
     */
    Optional<WorkoutTypeEntity> findByWorkoutTypeId(Long workoutTypeId);
}
