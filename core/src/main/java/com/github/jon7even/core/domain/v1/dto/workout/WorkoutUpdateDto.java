package com.github.jon7even.core.domain.v1.dto.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.github.jon7even.core.domain.v1.constant.DataTimePattern.DATE_TIME_IN;

/**
 * Класс DTO для обновления тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutUpdateDto {
    private Long id;

    private Long idDiary;

    private Long idTypeWorkout;

    @JsonFormat(pattern = DATE_TIME_IN)
    private LocalDateTime timeStartOn;

    @JsonFormat(pattern = DATE_TIME_IN)
    private LocalDateTime timeEndOn;

    private Long timeOfRest;

    private Float currentWeightUser;

    private String personalNote;

    private String detailOfWorkout;
}
