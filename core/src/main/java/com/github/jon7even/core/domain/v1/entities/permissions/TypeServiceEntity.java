package com.github.jon7even.core.domain.v1.entities.permissions;

import lombok.Builder;
import lombok.Data;

/**
 * Класс описывающий тип сервиса, для которого требуется выставить разрешения для GroupPermissionsEntity
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class TypeServiceEntity {
    private Integer id;
    private NameType nameType;
    private Boolean write;
    private Boolean read;
    private Boolean update;
    private Boolean delete;
}
