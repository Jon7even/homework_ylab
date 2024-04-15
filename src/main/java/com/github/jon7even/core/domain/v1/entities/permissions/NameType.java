package com.github.jon7even.core.domain.v1.entities.permissions;

import lombok.Builder;
import lombok.Data;

/**
 * Класс описывающий сущность сервиса(его названия)
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class NameType {
    private Integer id;
    private String name;
}
