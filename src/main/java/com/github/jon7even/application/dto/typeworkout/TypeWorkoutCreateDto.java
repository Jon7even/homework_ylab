package com.github.jon7even.application.dto.typeworkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Класс DTO для создания нового типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeWorkoutCreateDto {
    @NonNull
    private Long requesterId;
    @NonNull
    private String typeName;
    @NonNull
    private Integer caloriePerHour;
}
