package com.github.jon7even.application.dto.typeworkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для отображения дополнительной информации о типе тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailOfTypeWorkoutResponseDto {
    private Integer id;
    private String name;
    private Boolean isFillingRequired;
}
