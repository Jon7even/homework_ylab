package com.github.jon7even.core.domain.v1.entities.workout;

import lombok.Builder;
import lombok.Data;

/**
 * Класс описывающий дополнительные параметры типа тренировки и возможность описывания самой тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class DetailOfTypeWorkoutEntity {
    private Integer id;
    private String name;
    private Boolean isFillingRequired;
}
