package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutUpdateDto;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class WorkoutUpdateValidatorDto implements ValidatorDto<WorkoutUpdateDto> {
    private static WorkoutUpdateValidatorDto instance;
    private final ObjectValidator objectValidator;
    private final NumberValidator numberValidator;

    private WorkoutUpdateValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
        this.numberValidator = NumberValidator.getInstance();
    }

    public static WorkoutUpdateValidatorDto getInstance() {
        if (instance == null) {
            instance = new WorkoutUpdateValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(WorkoutUpdateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);
        numberValidator.validate(dto.getIdDiary(), ID);
        numberValidator.validate(dto.getIdDiary(), DIARY_ID);
        numberValidator.validate(dto.getIdTypeWorkout(), TYPE_WORKOUT_ID);
        numberValidator.validate(dto.getTimeOfRest(), TIME_OF_REST);
        numberValidator.validate(dto.getCurrentWeightUser(), CURRENT_WEIGHT);
        objectValidator.validate(dto.getPersonalNote(), PERSONAL_NOTE);
        objectValidator.validate(dto.getDetailOfWorkout(), DETAIL_OF_WORKOUT);
    }
}