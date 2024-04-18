package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutMapperTest extends PreparationForTests {
    private WorkoutMapper workoutMapper;

    @BeforeEach
    public void setUp() {
        initWorkoutDto();
        workoutMapper = new WorkoutMapperImpl();
    }

    @Test
    @DisplayName("Маппинг из DTO в сущность для сохранения новой тренировки")
    public void should_toWorkoutEntityFromDtoCreate_ReturnEntityNotId() {
        WorkoutEntity actualResultFirst = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDtoFirst, idTypeServiceDiary
        );
        assertNotNull(actualResultFirst);
        assertNull(actualResultFirst.getId());
        assertEquals(workoutEntityFirst.getIdDiary(), actualResultFirst.getIdDiary());
        assertEquals(workoutEntityFirst.getIdTypeWorkout(), actualResultFirst.getIdTypeWorkout());
        assertEquals(workoutEntityFirst.getTimeStartOn(), actualResultFirst.getTimeStartOn());
        assertEquals(workoutEntityFirst.getTimeEndOn(), actualResultFirst.getTimeEndOn());
        assertEquals(workoutEntityFirst.getTimeOfRest(), actualResultFirst.getTimeOfRest());
        assertEquals(workoutEntityFirst.getCurrentWeightUser(), actualResultFirst.getCurrentWeightUser());
        assertEquals(workoutEntityFirst.getPersonalNote(), actualResultFirst.getPersonalNote());
        assertEquals(workoutEntityFirst.getDetailOfWorkout(), actualResultFirst.getDetailOfWorkout());

        WorkoutEntity actualResultSecond = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDtoSecond, idTypeServiceDiary
        );
        assertNotNull(actualResultSecond);
        assertNull(actualResultSecond.getId());
        assertEquals(workoutEntitySecond.getIdDiary(), actualResultSecond.getIdDiary());
        assertEquals(workoutEntitySecond.getIdTypeWorkout(), actualResultSecond.getIdTypeWorkout());
        assertEquals(workoutEntitySecond.getTimeStartOn(), actualResultSecond.getTimeStartOn());
        assertEquals(workoutEntitySecond.getTimeEndOn(), actualResultSecond.getTimeEndOn());
        assertEquals(workoutEntitySecond.getTimeOfRest(), actualResultSecond.getTimeOfRest());
        assertEquals(workoutEntitySecond.getCurrentWeightUser(), actualResultSecond.getCurrentWeightUser());
        assertEquals(workoutEntitySecond.getPersonalNote(), actualResultSecond.getPersonalNote());
        assertEquals(workoutEntitySecond.getDetailOfWorkout(), actualResultSecond.getDetailOfWorkout());

        WorkoutEntity actualResultThird = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDtoThird, idTypeServiceDiary
        );
        assertNotNull(actualResultThird);
        assertNull(actualResultThird.getId());
        assertEquals(workoutEntityThird.getIdDiary(), actualResultThird.getIdDiary());
        assertEquals(workoutEntityThird.getIdTypeWorkout(), actualResultThird.getIdTypeWorkout());
        assertEquals(workoutEntityThird.getTimeStartOn(), actualResultThird.getTimeStartOn());
        assertEquals(workoutEntityThird.getTimeEndOn(), actualResultThird.getTimeEndOn());
        assertEquals(workoutEntityThird.getTimeOfRest(), actualResultThird.getTimeOfRest());
        assertEquals(workoutEntityThird.getCurrentWeightUser(), actualResultThird.getCurrentWeightUser());
        assertEquals(workoutEntityThird.getPersonalNote(), actualResultThird.getPersonalNote());
        assertEquals(workoutEntityThird.getDetailOfWorkout(), actualResultThird.getDetailOfWorkout());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления полной информации о тренировке")
    public void should_toDiaryResponseDtoFromEntity_ReturnDtoResponse() {
        WorkoutFullResponseDto actualResultFirst = workoutMapper.toDiaryResponseDtoFromEntity(
                workoutEntityFirst, typeWorkoutResponseDtoWalking
        );
        assertNotNull(actualResultFirst);
        assertEquals(workoutEntityFirst.getId(), actualResultFirst.getId());
        assertEquals(workoutEntityFirst.getIdDiary(), actualResultFirst.getIdDiary());
        assertEquals(workoutEntityFirst.getIdTypeWorkout(),
                actualResultFirst.getTypeWorkoutResponseDto().getTypeWorkoutId());
        assertEquals(workoutEntityFirst.getTimeStartOn(), actualResultFirst.getTimeStartOn());
        assertEquals(workoutEntityFirst.getTimeEndOn(), actualResultFirst.getTimeEndOn());
        assertEquals(workoutEntityFirst.getTimeOfRest(), actualResultFirst.getTimeOfRest());
        assertEquals(workoutEntityFirst.getCurrentWeightUser(), actualResultFirst.getCurrentWeightUser());
        assertEquals(workoutEntityFirst.getPersonalNote(), actualResultFirst.getPersonalNote());
        assertEquals(workoutEntityFirst.getDetailOfWorkout(), actualResultFirst.getDetailOfWorkout());

        WorkoutFullResponseDto actualResultSecond = workoutMapper.toDiaryResponseDtoFromEntity(
                workoutEntitySecond, typeWorkoutResponseDtoStrength
        );
        assertNotNull(actualResultSecond);
        assertEquals(workoutEntitySecond.getId(), actualResultSecond.getId());
        assertEquals(workoutEntitySecond.getIdDiary(), actualResultSecond.getIdDiary());
        assertEquals(workoutEntitySecond.getIdTypeWorkout(),
                actualResultSecond.getTypeWorkoutResponseDto().getTypeWorkoutId());
        assertEquals(workoutEntitySecond.getTimeStartOn(), actualResultSecond.getTimeStartOn());
        assertEquals(workoutEntitySecond.getTimeEndOn(), actualResultSecond.getTimeEndOn());
        assertEquals(workoutEntitySecond.getTimeOfRest(), actualResultSecond.getTimeOfRest());
        assertEquals(workoutEntitySecond.getCurrentWeightUser(), actualResultSecond.getCurrentWeightUser());
        assertEquals(workoutEntitySecond.getPersonalNote(), actualResultSecond.getPersonalNote());
        assertEquals(workoutEntitySecond.getDetailOfWorkout(), actualResultSecond.getDetailOfWorkout());

        WorkoutFullResponseDto actualResultThird = workoutMapper.toDiaryResponseDtoFromEntity(
                workoutEntityThird, typeWorkoutResponseDtoYoga
        );
        assertNotNull(actualResultThird);
        assertEquals(workoutEntityThird.getId(), actualResultThird.getId());
        assertEquals(workoutEntityThird.getIdDiary(), actualResultThird.getIdDiary());
        assertEquals(workoutEntityThird.getIdTypeWorkout(),
                actualResultThird.getTypeWorkoutResponseDto().getTypeWorkoutId());
        assertEquals(workoutEntityThird.getTimeStartOn(), actualResultThird.getTimeStartOn());
        assertEquals(workoutEntityThird.getTimeEndOn(), actualResultThird.getTimeEndOn());
        assertEquals(workoutEntityThird.getTimeOfRest(), actualResultThird.getTimeOfRest());
        assertEquals(workoutEntityThird.getCurrentWeightUser(), actualResultThird.getCurrentWeightUser());
        assertEquals(workoutEntityThird.getPersonalNote(), actualResultThird.getPersonalNote());
        assertEquals(workoutEntityThird.getDetailOfWorkout(), actualResultThird.getDetailOfWorkout());
    }
}