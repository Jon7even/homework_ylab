package com.github.jon7even.core.domain.v1.dto.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.github.jon7even.core.domain.v1.constant.DataTimePattern.DATE_TIME_DEFAULT;

/**
 * Класс DTO для краткого предоставления данных о тренировке
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutShortResponseDto {
    private Long id;

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private LocalDateTime timeStartOn;

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private LocalDateTime timeEndOn;

    private Long timeOfRest;

    private Float currentWeightUser;

    private String personalNote;
}