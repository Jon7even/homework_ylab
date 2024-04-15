package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * Интерфейс для маппинга DTO и сущностей типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper
public interface TypeWorkoutMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "typeWorkoutCreateDto.typeName", target = "typeName")
    @Mapping(source = "typeWorkoutCreateDto.caloriePerHour", target = "caloriePerHour")
    @Mapping(source = "idTypeService", target = "idTypeService")
    TypeWorkoutEntity toTypeWorkoutEntityFromDtoCreate(TypeWorkoutCreateDto typeWorkoutCreateDto,
                                                       Integer idTypeService);

    @Mapping(source = "typeWorkoutEntity.id", target = "typeWorkoutId")
    @Mapping(source = "typeWorkoutEntity.typeName", target = "typeName")
    @Mapping(source = "typeWorkoutEntity.caloriePerHour", target = "caloriePerHour")
    @Mapping(source = "typeWorkoutEntity.idTypeService", target = "idTypeService")
    TypeWorkoutResponseDto toTypeWorkoutResponseDtoFromEntity(TypeWorkoutEntity typeWorkoutEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "typeWorkoutUpdateDto.typeName", target = "typeName")
    @Mapping(source = "typeWorkoutUpdateDto.caloriePerHour", target = "caloriePerHour")
    @Mapping(target = "idTypeService", ignore = true)
    void updateTypeWorkoutEntityFromDtoUpdate(@MappingTarget TypeWorkoutEntity typeWorkoutEntity,
                                              TypeWorkoutUpdateDto typeWorkoutUpdateDto);

    @Mapping(source = "listTypeWorkoutsEntity.id", target = "typeWorkoutId")
    @Mapping(source = "listTypeWorkoutsEntity.typeName", target = "typeName")
    List<TypeWorkoutShortDto> toListTypeWorkoutDtoFromEntity(List<TypeWorkoutEntity> listTypeWorkoutsEntity);
}
