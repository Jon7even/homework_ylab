package com.github.jon7even.application.dto.typeworkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для обновления данных о типе тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeWorkoutUpdateDto {
    private Long requesterId;
    private Long typeWorkoutId;
    private String typeName;
    private Integer caloriePerHour;
    private Integer detailOfTypeId;
}
