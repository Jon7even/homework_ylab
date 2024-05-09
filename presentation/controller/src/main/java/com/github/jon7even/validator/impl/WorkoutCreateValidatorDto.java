package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.validator.DataTimeValidator;
import com.github.jon7even.validator.ValidatorDto;

import java.time.LocalDateTime;

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