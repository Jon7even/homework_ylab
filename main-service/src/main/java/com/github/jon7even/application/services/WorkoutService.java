package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.workout.WorkoutCreateDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutUpdateDto;

import java.util.List;

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

    /**
     * Метод, который получает DTO тренировки
     *
     * @param workoutId существующий ID типа тренировки
     * @return объект DTO WorkoutFullResponseDto
     */
    WorkoutFullResponseDto getWorkoutById(Long workoutId);

    /**
     * Метод, который удаляет тренировку по ID
     *
     * @param workoutId   существующий ID типа тренировки
     * @param requesterId существующий ID пользователя - владелец дневника тренировки
     */
    void deleteWorkoutByWorkoutIdAndOwnerId(Long workoutId, Long requesterId);

    /**
     * Метод, который возвращает однозначный ответ, существует ли тренировка по заданному ID
     *
     * @param workoutId существующий ID типа тренировки
     * @return boolean с ответом - есть ли такой тип тренировки
     */
    boolean isExistWorkoutByWorkoutId(Long workoutId);

    /**
     * Метод, который обновляет дневник пользователя
     *
     * @param workoutUpdateDto заполненный объект DTO
     * @return объект DTO WorkoutFullResponseDto
     */
    WorkoutFullResponseDto updateWorkout(WorkoutUpdateDto workoutUpdateDto);

    /**
     * Метод, который получает список своих тренировок по ID дневника пользователя
     *
     * @param idDiary     существующий ID дневника
     * @param requesterId requesterId существующий ID пользователя - владелец дневника тренировки
     * @return сортированный по дате список DTO в коротком представлении WorkoutShortResponseDto
     */
    List<WorkoutShortResponseDto> findAllWorkoutByOwnerDiaryBySortByDeskDate(Long idDiary, Long requesterId);

    /**
     * Метод, который получает список тренировок по ID пользователя
     *
     * @param userId      существующий ID пользователя
     * @param requesterId requesterId существующий ID пользователя у которого есть доступ для этой операции
     * @return сортированный по дате список DTO в коротком представлении WorkoutShortResponseDto
     */
    List<WorkoutShortResponseDto> findAllWorkoutByAdminDiaryBySortByDeskDate(Long userId, Long requesterId);
}