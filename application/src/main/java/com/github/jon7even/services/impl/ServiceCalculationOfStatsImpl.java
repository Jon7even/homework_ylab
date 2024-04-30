package com.github.jon7even.services.impl;

import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.services.ServiceCalculationOfStats;

import java.time.Duration;

/**
 * Реализация сервиса для различных расчетов статистики тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
public class ServiceCalculationOfStatsImpl implements ServiceCalculationOfStats {
    private static ServiceCalculationOfStatsImpl instance;

    public static ServiceCalculationOfStatsImpl getInstance() {
        if (instance == null) {
            instance = new ServiceCalculationOfStatsImpl();
        }
        return instance;
    }

    @Override
    public int getTotalCalorieFromWorkoutDto(WorkoutFullResponseDto workoutFullResponseDto) {
        System.out.println("Начинаю расчет количества Калорий на тренировку:" + workoutFullResponseDto);
        long timeOfWorkoutMinusRest = getRealMinutesOfWorkoutFromWorkoutDto(workoutFullResponseDto);
        float caloriePerMinutes = workoutFullResponseDto.getTypeWorkoutResponseDto()
                .getCaloriePerHour().floatValue() / 60;
        return (int) (caloriePerMinutes * timeOfWorkoutMinusRest);
    }

    @Override
    public int getRealMinutesOfWorkoutFromWorkoutDto(WorkoutFullResponseDto workoutFullResponseDto) {
        System.out.println("Начинаю расчет количества минут тренировки:" + workoutFullResponseDto);
        long timeOfWorkout = Duration.between(workoutFullResponseDto.getTimeStartOn(),
                workoutFullResponseDto.getTimeEndOn()).toMinutes();
        return Math.toIntExact(timeOfWorkout - workoutFullResponseDto.getTimeOfRest().toMinutes());
    }
}
