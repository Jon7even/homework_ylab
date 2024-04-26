package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import org.mapstruct.*;

import java.util.List;

/**
 * Интерфейс для маппинга DTO и сущностей тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper
public interface WorkoutMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "workoutCreateDto.idDiary", target = "idDiary")
    @Mapping(source = "workoutCreateDto.idTypeWorkout", target = "idTypeWorkout")
    @Mapping(source = "workoutCreateDto.timeStartOn", target = "timeStartOn")
    @Mapping(source = "workoutCreateDto.timeEndOn", target = "timeEndOn")
    @Mapping(source = "workoutCreateDto.timeOfRest", target = "timeOfRest")
    @Mapping(source = "workoutCreateDto.currentWeightUser", target = "currentWeightUser")
    @Mapping(source = "workoutCreateDto.personalNote", target = "personalNote")
    @Mapping(source = "workoutCreateDto.detailOfWorkout", target = "detailOfWorkout")
    WorkoutEntity toWorkoutEntityFromDtoCreate(WorkoutCreateDto workoutCreateDto);

    @Mapping(source = "workoutEntity.id", target = "id")
    @Mapping(source = "workoutEntity.idDiary", target = "idDiary")
    @Mapping(source = "typeWorkoutResponseDto", target = "typeWorkoutResponseDto")
    @Mapping(source = "workoutEntity.timeStartOn", target = "timeStartOn")
    @Mapping(source = "workoutEntity.timeEndOn", target = "timeEndOn")
    @Mapping(source = "workoutEntity.timeOfRest", target = "timeOfRest")
    @Mapping(source = "workoutEntity.currentWeightUser", target = "currentWeightUser")
    @Mapping(source = "workoutEntity.personalNote", target = "personalNote")
    @Mapping(source = "workoutEntity.detailOfWorkout", target = "detailOfWorkout")
    WorkoutFullResponseDto toWorkoutFullResponseDtoFromEntity(WorkoutEntity workoutEntity,
                                                              TypeWorkoutResponseDto typeWorkoutResponseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idDiary", ignore = true)
    @Mapping(source = "workoutUpdate.idTypeWorkout", target = "idTypeWorkout")
    @Mapping(source = "workoutUpdate.timeStartOn", target = "timeStartOn")
    @Mapping(source = "workoutUpdate.timeEndOn", target = "timeEndOn")
    @Mapping(source = "workoutUpdate.timeOfRest", target = "timeOfRest")
    @Mapping(source = "workoutUpdate.currentWeightUser", target = "currentWeightUser")
    @Mapping(source = "workoutUpdate.personalNote", target = "personalNote")
    @Mapping(source = "workoutUpdate.detailOfWorkout", target = "detailOfWorkout")
    void updateWorkoutEntityFromDtoUpdate(@MappingTarget WorkoutEntity workoutEntity, WorkoutUpdateDto workoutUpdate);

    @Mapping(source = "workoutEntity.id", target = "id")
    @Mapping(source = "workoutEntity.timeStartOn", target = "timeStartOn")
    @Mapping(source = "workoutEntity.timeEndOn", target = "timeEndOn")
    @Mapping(source = "workoutEntity.timeOfRest", target = "timeOfRest")
    @Mapping(source = "workoutEntity.currentWeightUser", target = "currentWeightUser")
    @Mapping(source = "workoutEntity.personalNote", target = "personalNote")
    WorkoutShortResponseDto toWorkoutShortResponseDtoFromEntity(WorkoutEntity workoutEntity);

    @Mapping(source = "workoutEntity.timeStartOn", target = "timeStartOn")
    @Mapping(source = "workoutEntity.timeEndOn", target = "timeEndOn")
    @Mapping(source = "workoutEntity.timeOfRest", target = "timeOfRest")
    @Mapping(source = "workoutEntity.currentWeightUser", target = "currentWeightUser")
    @Mapping(source = "workoutEntity.personalNote", target = "personalNote")
    List<WorkoutShortResponseDto> toListWorkoutShortResponseDtoFromEntity(List<WorkoutEntity> workoutEntity);
}