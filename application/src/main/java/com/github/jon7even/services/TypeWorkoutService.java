package com.github.jon7even.services;

import com.github.jon7even.core.domain.v1.dto.typeworkout.*;

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
     * Метод, который возвращает однозначный ответ, существует ли тип тренировки по заданному ID
     *
     * @param typeWorkoutId существующий ID типа тренировки
     * @return boolean с ответом - есть ли такой тип тренировки
     */
    boolean isExistTypeWorkoutByTypeWorkoutId(Long typeWorkoutId);

    /**
     * Метод, который получает все типы тренировок
     *
     * @return несортированный список DTO всех типов тренировок в кратком представлении TypeWorkoutShortDto
     */
    List<TypeWorkoutShortDto> findAllTypeWorkoutsNoSort();

    /**
     * Метод, который получает дополнительные параметры к типу тренировки
     *
     * @param detailOfTypeId существующий ID параметра
     * @return объект DTO DetailOfTypeWorkoutResponseDto с деталями о типе тренировки
     */
    DetailOfTypeWorkoutResponseDto findDetailOfTypeByDetailOfTypeId(Integer detailOfTypeId);

    /**
     * Метод, который возвращает однозначный ответ, существует такой параметр детализации
     *
     * @param detailOfTypeId существующий ID параметра
     * @return boolean с ответом - есть ли такой параметр детализации
     */
    boolean isExistDetailOfTypeByDetailOfTypeId(Integer detailOfTypeId);

    /**
     * Метод, который получает все возможные детали для типов тренировок
     *
     * @return несортированный список DTO всех деталей в представлении DetailOfTypeWorkoutResponseDto
     */
    List<DetailOfTypeWorkoutResponseDto> findAllDetailOfTypeWorkoutNoSort();
}
