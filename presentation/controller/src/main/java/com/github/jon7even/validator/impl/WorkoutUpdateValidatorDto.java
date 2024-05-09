package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutUpdateDto;
import com.github.jon7even.validator.DataTimeValidator;
import com.github.jon7even.validator.ValidatorDto;

import java.time.LocalDateTime;

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

        objectValidator.validate(dto.getTimeStartOn(), OBJECT_DTO);
        LocalDateTime startTimeStartOn = dto.getTimeStartOn();
        DataTimeValidator.validationTimeOfStart(LocalDateTime.now(), startTimeStartOn);

        objectValidator.validate(dto.getTimeEndOn(), OBJECT_DTO);
        LocalDateTime startTimeEndOn = dto.getTimeEndOn();
        DataTimeValidator.validationTimeOfEnd(startTimeStartOn, startTimeEndOn);

        numberValidator.validate(dto.getTimeOfRest(), TIME_OF_REST);
        DataTimeValidator.validationTimeOfRest(startTimeStartOn, startTimeEndOn, dto.getTimeOfRest());

        numberValidator.validate(dto.getCurrentWeightUser(), CURRENT_WEIGHT);
        objectValidator.validate(dto.getPersonalNote(), PERSONAL_NOTE);
        objectValidator.validate(dto.getDetailOfWorkout(), DETAIL_OF_WORKOUT);
    }
}