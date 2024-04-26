package com.github.jon7even.core.domain.v1.dto.workout;

import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Класс DTO для полного предоставления данных о тренировке
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutFullResponseDto {
    private Long id;
    private Long idDiary;
    private TypeWorkoutResponseDto typeWorkoutResponseDto;
    private LocalDateTime timeStartOn;
    private LocalDateTime timeEndOn;
    private Duration timeOfRest;
    private Float currentWeightUser;
    private String personalNote;
    private String detailOfWorkout;
}