package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.workout.WorkoutCreateDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;

/**
 * Интерфейс для взаимодействия с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
public interface WorkoutService {
    /**
     * Метод, который сохраняет новую тренировку
     *
     * @param workoutCreateDto заполненный объект DTO
     * @return полная форма ответа WorkoutFullResponseDto
     */
    WorkoutFullResponseDto saveWorkout(WorkoutCreateDto workoutCreateDto);
}