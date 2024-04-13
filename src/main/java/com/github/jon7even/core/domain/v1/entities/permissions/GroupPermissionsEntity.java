package com.github.jon7even.core.domain.v1.entities.permissions;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Класс описывающий сущность группы разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class GroupPermissionsEntity {
    private Integer id;
    private String name;
    private Set<TypeServiceEntity> servicesList;
}
