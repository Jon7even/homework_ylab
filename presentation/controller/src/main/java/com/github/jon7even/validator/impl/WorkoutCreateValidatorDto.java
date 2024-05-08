package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.*;

public class WorkoutCreateValidatorDto implements ValidatorDto<WorkoutCreateDto> {
    private final LongValidator validatorLong;
    private final StringValidator validatorString;
    private static WorkoutCreateValidatorDto instance;

    public static WorkoutCreateValidatorDto getInstance() {
        if (instance == null) {
            instance = new WorkoutCreateValidatorDto();
        }
        return instance;
    }

    private WorkoutCreateValidatorDto() {
        this.validatorLong = LongValidator.getInstance();
        this.validatorString = StringValidator.getInstance();
    }

    @Override
    public void validate(WorkoutCreateDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }
        validatorLong.validate(String.valueOf(dto.getIdDiary()), DIARY_ID);
        validatorLong.validate(String.valueOf(dto.getIdTypeWorkout()), TYPE_WORKOUT_ID);
        validatorLong.validate(String.valueOf(dto.getIdTypeWorkout()), TIME_OF_REST);
        validatorString.validate(dto.getPersonalNote(), "personalNote");
        validatorString.validate(dto.getDetailOfWorkout(), "detailOfWorkout");
    }
}