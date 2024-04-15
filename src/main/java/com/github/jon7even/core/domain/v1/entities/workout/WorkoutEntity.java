package com.github.jon7even.core.domain.v1.entities.workout;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс описывающий сущность тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class WorkoutEntity {
    private Long id;
    private Long idDiary;
    private LocalDateTime timeStartOn;
    private LocalDateTime timeEndOn;
    private Double currentWeightUser;
    private Long idTypeWorkout;
    private Integer idTypeService;
}
