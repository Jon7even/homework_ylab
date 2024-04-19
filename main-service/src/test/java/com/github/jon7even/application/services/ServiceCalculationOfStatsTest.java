package com.github.jon7even.application.services;

import com.github.jon7even.application.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ServiceCalculationOfStatsTest extends PreparationForTests {
    private ServiceCalculationOfStatsImpl serviceCalculationOfStats;

    @BeforeEach
    public void setup() {
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
        initTypeWorkoutDto();
        initWorkoutDto();
        initLocalDateTime();
    }

    @Test
    @DisplayName("Должен правильно посчитать количество сожженных Калорий нза одну тренировку")
    public void shouldCalculateCalorieOfWorkout_ReturnRightCalorie() {
        Integer actualResult = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutFullResponseDtoFirst);
        int caloriePerHourExpected = workoutFullResponseDtoFirst.getTypeWorkoutResponseDto().getCaloriePerHour();
        assertNotNull(actualResult);
        assertEquals(caloriePerHourExpected, actualResult);
    }

    @Test
    @DisplayName("Должен правильно посчитать реальное количество минут тренировки")
    public void shouldCalculateMinutesOfWorkoutMinusRest_ReturnRightMinutes() {
        Integer actualResult = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                workoutFullResponseDtoFirst
        );
        long timeOfWorkout = Duration.between(timeStartFirst, timeEndFirst).toMinutes();
        int timeOfWorkoutMinusRestExpected
                = (int) (timeOfWorkout - workoutFullResponseDtoFirst.getTimeOfRest().toMinutes());
        assertNotNull(actualResult);
        assertEquals(timeOfWorkoutMinusRestExpected, actualResult);
    }

}

