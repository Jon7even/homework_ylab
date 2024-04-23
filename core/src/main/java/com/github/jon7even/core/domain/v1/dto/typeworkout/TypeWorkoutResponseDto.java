package com.github.jon7even.core.domain.v1.dto.typeworkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для полного предоставления данных о типе тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeWorkoutResponseDto {
    private Long typeWorkoutId;
    private String typeName;
    private Integer caloriePerHour;
    private DetailOfTypeWorkoutResponseDto detailOfTypeWorkoutResponseDto;
}
