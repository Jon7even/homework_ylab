package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;

import java.util.List;
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
     * @return новый Entity тип тренировки со сгенерированным ID
     */
    Optional<TypeWorkoutEntity> createTypeWorkout(TypeWorkoutEntity typeWorkoutEntity);

    /**
     * Метод для обновления существующего типа тренировки
     *
     * @param typeWorkoutEntity существующий тип тренировки
     * @return обновленный Entity тип тренировки, если он есть в системе
     */
    Optional<TypeWorkoutEntity> updateTypeWorkout(TypeWorkoutEntity typeWorkoutEntity);

    /**
     * Метод для поиска типа тренировки по ID
     *
     * @param typeWorkoutId существующий ID типа тренировки
     * @return тип тренировки Entity, если он есть в системе
     */
    Optional<TypeWorkoutEntity> findByTypeWorkoutId(Long typeWorkoutId);

    /**
     * Метод, который получает все типы тренировок
     *
     * @return весь найденный список Entity всех существующих типов тренировок без параметров сортировки
     */
    List<TypeWorkoutEntity> findAllTypeWorkoutsNoSort();
}
