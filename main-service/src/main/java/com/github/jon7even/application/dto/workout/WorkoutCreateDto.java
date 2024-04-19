package com.github.jon7even.application.dto.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Класс DTO для сохранения новой тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutCreateDto {
    private Long idDiary;
    private Long idTypeWorkout;
    private LocalDateTime timeStartOn;
    private LocalDateTime timeEndOn;
    private Duration timeOfRest;
    private Float currentWeightUser;
    private String personalNote;
    private String detailOfWorkout;
}
