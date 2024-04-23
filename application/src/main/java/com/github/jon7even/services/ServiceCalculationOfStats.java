package com.github.jon7even.services;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;

/**
 * Интерфейс для получения расчета статистики
 *
 * @author Jon7even
 * @version 1.0
 */
public interface ServiceCalculationOfStats {
    /**
     * Метод, который получает количество сожженных Калорий за всю тренировку
     *
     * @param workoutFullResponseDto заполненный объект DTO
     * @return integer целое число полученных Калорий, можно перевести в Килокалории, если требуется во View
     */
    int getTotalCalorieFromWorkoutDto(WorkoutFullResponseDto workoutFullResponseDto);

    /**
     * Метод, который получает реальное время тренировки за вычетом отдыха
     *
     * @param workoutFullResponseDto заполненный объект DTO
     * @return Integer целое число количества реального количества тренировки
     */
    int getRealMinutesOfWorkoutFromWorkoutDto(WorkoutFullResponseDto workoutFullResponseDto);
}
