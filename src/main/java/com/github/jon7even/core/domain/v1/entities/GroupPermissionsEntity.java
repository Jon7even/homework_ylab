package com.github.jon7even.core.domain.v1.entities;

import com.github.jon7even.core.domain.v1.enums.AccessLevel;
import lombok.Builder;
import lombok.Data;

/**
 * Класс описывающий сущность идентификатора разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class GroupPermissionsEntity {
    private Integer id;
    private String name;
    private AccessLevel write;
    private AccessLevel read;
    private AccessLevel update;
    private AccessLevel delete;
}
