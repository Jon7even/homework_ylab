package com.github.jon7even.application.dto.typeworkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для краткого предоставления данных о типе тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeWorkoutShortDto {
    private Long id;
    private String typeName;
}
