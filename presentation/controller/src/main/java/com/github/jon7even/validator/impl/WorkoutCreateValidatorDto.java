package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class WorkoutCreateValidatorDto implements ValidatorDto<WorkoutCreateDto> {
    private static WorkoutCreateValidatorDto instance;
    private final ObjectValidator objectValidator;
    private final NumberValidator numberValidator;

    private WorkoutCreateValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
        this.numberValidator = NumberValidator.getInstance();
    }

    public static WorkoutCreateValidatorDto getInstance() {
        if (instance == null) {
            instance = new WorkoutCreateValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(WorkoutCreateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);
        numberValidator.validate(dto.getIdDiary(), DIARY_ID);
        numberValidator.validate(dto.getIdTypeWorkout(), TYPE_WORKOUT_ID);
        numberValidator.validate(dto.getTimeOfRest(), TIME_OF_REST);
        numberValidator.validate(dto.getCurrentWeightUser(), CURRENT_WEIGHT);
        objectValidator.validate(dto.getPersonalNote(), PERSONAL_NOTE);
        objectValidator.validate(dto.getDetailOfWorkout(), DETAIL_OF_WORKOUT);
    }
}