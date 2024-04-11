package com.github.jon7even.core.domain.v1.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Класс описывающий сущность типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class WorkoutTypeEntity {
    private Long id;
    private String typeName;
    private Double kcalPerHour;
    private Set<GroupPermissionsEntity> permissions;
}
