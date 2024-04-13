package com.github.jon7even.core.domain.v1.entities.workout;

import lombok.Builder;
import lombok.Data;

/**
 * Класс описывающий сущность типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class TypeWorkoutEntity {
    private Long id;
    private String typeName;
    private Double kcalPerHour;
    private Integer idTypeService;
}
