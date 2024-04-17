package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutCreateDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    @Mapping(source = "idTypeService", target = "idTypeService")
    WorkoutEntity toWorkoutEntityFromDtoCreate(WorkoutCreateDto workoutCreateDto, Integer idTypeService);

    @Mapping(source = "workoutEntity.id", target = "id")
    @Mapping(source = "workoutEntity.idDiary", target = "idDiary")
    @Mapping(source = "typeWorkoutResponseDto", target = "typeWorkoutResponseDto")
    @Mapping(source = "workoutEntity.timeStartOn", target = "timeStartOn")
    @Mapping(source = "workoutEntity.timeEndOn", target = "timeEndOn")
    @Mapping(source = "workoutEntity.timeOfRest", target = "timeOfRest")
    @Mapping(source = "workoutEntity.currentWeightUser", target = "currentWeightUser")
    @Mapping(source = "workoutEntity.personalNote", target = "personalNote")
    @Mapping(source = "workoutEntity.detailOfWorkout", target = "detailOfWorkout")
    WorkoutFullResponseDto toDiaryResponseDtoFromEntity(WorkoutEntity workoutEntity,
                                                        TypeWorkoutResponseDto typeWorkoutResponseDto);
}