package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutUpdateDto;

import java.util.List;

/**
 * Интерфейс для взаимодействия с типом тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
public interface TypeWorkoutService {
    /**
     * Метод, который добавляет новый тип тренировки
     *
     * @param typeWorkoutCreateDto заполненный объект DTO
     * @return объект DTO ResponseTypeWorkoutDto
     */
    TypeWorkoutResponseDto createTypeWorkout(TypeWorkoutCreateDto typeWorkoutCreateDto);

    /**
     * Метод, который получает DTO типа тренировки
     *
     * @param typeWorkoutId существующий ID типа тренировки
     * @return объект DTO ResponseTypeWorkoutDto
     */
    TypeWorkoutResponseDto findTypeWorkoutByTypeWorkoutId(Long typeWorkoutId);

    /**
     * Метод, который обновляет тип тренировки
     *
     * @param typeWorkoutUpdateDto заполненный DTO для обновления
     */
    TypeWorkoutResponseDto updateTypeWorkout(TypeWorkoutUpdateDto typeWorkoutUpdateDto);

    /**
     * Метод, который даёт однозначный ответ существует ли тип тренировки по заданному ID
     *
     * @param typeWorkoutId существующий ID типа тренировки
     * @return boolean с ответом - есть ли такой тип тренировки
     */
    boolean isExistByTypeWorkoutId(Long typeWorkoutId);

    /**
     * Метод, который получает все типы тренировок
     *
     * @return несортированный список DTO всех типов тренировок в кратком представлении TypeWorkoutShortDto
     */
    List<TypeWorkoutShortDto> findAllTypeWorkoutsNoSort();
}
