package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.core.domain.v1.dto.typeworkout.*;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
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
    @Mapping(source = "detailOfTypeWorkoutEntity", target = "detailOfTypeWorkoutEntity")
    TypeWorkoutEntity toTypeWorkoutEntityFromDtoCreate(TypeWorkoutCreateDto typeWorkoutCreateDto,
                                                       DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntity);

    @Mapping(source = "typeWorkoutEntity.id", target = "typeWorkoutId")
    @Mapping(source = "typeWorkoutEntity.typeName", target = "typeName")
    @Mapping(source = "typeWorkoutEntity.caloriePerHour", target = "caloriePerHour")
    @Mapping(source = "typeWorkoutEntity.detailOfTypeWorkoutEntity.id", target = "detailOfTypeWorkoutResponseDto.id")
    @Mapping(source = "typeWorkoutEntity.detailOfTypeWorkoutEntity.name",
            target = "detailOfTypeWorkoutResponseDto.name")
    @Mapping(source = "typeWorkoutEntity.detailOfTypeWorkoutEntity.isFillingRequired",
            target = "detailOfTypeWorkoutResponseDto.isFillingRequired")
    TypeWorkoutResponseDto toTypeWorkoutResponseDtoFromEntity(TypeWorkoutEntity typeWorkoutEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "typeWorkoutUpdateDto.typeName", target = "typeName")
    @Mapping(source = "typeWorkoutUpdateDto.caloriePerHour", target = "caloriePerHour")
    @Mapping(target = "detailOfTypeWorkoutEntity", ignore = true)
    void updateTypeWorkoutEntityFromDtoUpdate(@MappingTarget TypeWorkoutEntity typeWorkoutEntity,
                                              TypeWorkoutUpdateDto typeWorkoutUpdateDto);

    @Mapping(source = "entities.id", target = "id")
    @Mapping(source = "entities.typeName", target = "typeName")
    List<TypeWorkoutShortDto> toListTypeWorkoutDtoFromEntity(List<TypeWorkoutEntity> entities);

    @Mapping(source = "detailEntity.id", target = "id")
    @Mapping(source = "detailEntity.name", target = "name")
    @Mapping(source = "detailEntity.isFillingRequired", target = "isFillingRequired")
    DetailOfTypeWorkoutResponseDto toDtoDetailOfTypeResponseFromEntity(DetailOfTypeWorkoutEntity detailEntity);

    @Mapping(source = "detailResponseDto.id", target = "id")
    @Mapping(source = "detailResponseDto.name", target = "name")
    @Mapping(source = "detailResponseDto.isFillingRequired", target = "isFillingRequired")
    DetailOfTypeWorkoutEntity toEntityDetailOfTypeFromDtoResponse(DetailOfTypeWorkoutResponseDto detailResponseDto);

    @Mapping(source = "entities.id", target = "id")
    @Mapping(source = "entities.name", target = "name")
    @Mapping(source = "entities.isFillingRequired", target = "isFillingRequired")
    List<DetailOfTypeWorkoutResponseDto> toListDetailResponseDtoFromEntity(List<DetailOfTypeWorkoutEntity> entities);
}