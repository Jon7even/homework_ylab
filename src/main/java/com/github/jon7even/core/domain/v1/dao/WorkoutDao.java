package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс DAO для тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public interface WorkoutDao {
    /**
     * Метод для создания новой тренировки
     *
     * @param workoutEntity новая тренировка без ID
     * @return новая Entity тренировка со сгенерированным ID
     */
    Optional<WorkoutEntity> createWorkout(WorkoutEntity workoutEntity);

    /**
     * Метод для обновления существующей тренировки
     *
     * @param workoutEntity существующая тренировка
     * @return обновленная Entity тренировка, если она есть в системе
     */
    Optional<WorkoutEntity> updateWorkout(WorkoutEntity workoutEntity);

    /**
     * Метод для поиска тренировки по ID
     *
     * @param workoutId существующий ID тренировки
     * @return тренировку Entity, если она есть в системе
     */
    Optional<WorkoutEntity> findByWorkoutByTypeIdAndDate(Long workoutId);

    /**
     * Метод для поиска определенной тренировки по дню и типу
     *
     * @param idTypeWorkout существующий ID типа тренировки
     * @param dayOfWorkout  день в который была тренировка
     * @return тренировку Entity, если она подпадает под параметры поиска
     */
    Optional<WorkoutEntity> findByWorkoutByTypeIdAndDate(Long idTypeWorkout, LocalDate dayOfWorkout);

    /**
     * Метод для поиска всех тренировок по ID дневника
     *
     * @param diaryId существующий ID дневника
     * @return весь найденный список Entity всех существующих тренировок без параметров сортировки относящихся
     * к определенному дневнику
     */
    List<WorkoutEntity> findAllWorkoutByDiaryId(Long diaryId);

    /**
     * Метод для поиска всех тренировок без параметров
     *
     * @return весь найденный список Entity всех существующих тренировок без параметров сортировки
     */
    List<WorkoutEntity> findAllWorkout();
}
