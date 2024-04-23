package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.core.domain.v1.dto.permission.GroupPermissionsServiceDto;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для маппинга DTO и сущностей групп разрешений
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper
public interface GroupPermissionsMapper {
    @Mapping(source = "groupPermissionsEntity.id", target = "id")
    @Mapping(source = "groupPermissionsEntity.name", target = "nameGroup")
    @Mapping(source = "typeServiceEntity.nameType.name", target = "nameService")
    @Mapping(source = "typeServiceEntity.write", target = "write")
    @Mapping(source = "typeServiceEntity.read", target = "read")
    @Mapping(source = "typeServiceEntity.update", target = "update")
    @Mapping(source = "typeServiceEntity.delete", target = "delete")
    GroupPermissionsServiceDto toPermissionsServiceDtoFromEntity(GroupPermissionsEntity groupPermissionsEntity,
                                                                 TypeServiceEntity typeServiceEntity
    );
}
