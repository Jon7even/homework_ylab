package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.PARAM_REQUESTER_ID;
import static com.github.jon7even.constants.ControllerParam.PARAM_TYPE_WORKOUT_ID;
import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class TypeWorkoutUpdateValidatorDto implements ValidatorDto<TypeWorkoutUpdateDto> {
    private static TypeWorkoutUpdateValidatorDto instance;
    private final ObjectValidator objectValidator;
    private final NumberValidator numberValidator;

    private TypeWorkoutUpdateValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
        this.numberValidator = NumberValidator.getInstance();
    }

    public static TypeWorkoutUpdateValidatorDto getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutUpdateValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(TypeWorkoutUpdateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);
        numberValidator.validate(dto.getRequesterId(), PARAM_REQUESTER_ID);
        numberValidator.validate(dto.getRequesterId(), PARAM_TYPE_WORKOUT_ID);
        objectValidator.validate(dto.getTypeName(), TYPE_NAME);
        numberValidator.validate(dto.getCaloriePerHour(), CALORIE_PER_HOUR);
        numberValidator.validate(dto.getDetailOfTypeId(), DETAIL_OF_TYPE_ID);
    }
}