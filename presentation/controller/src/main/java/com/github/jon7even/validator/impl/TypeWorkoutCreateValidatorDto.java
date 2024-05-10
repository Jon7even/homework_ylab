package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.PARAM_REQUESTER_ID;
import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class TypeWorkoutCreateValidatorDto implements ValidatorDto<TypeWorkoutCreateDto> {
    private static TypeWorkoutCreateValidatorDto instance;
    private final ObjectValidator objectValidator;
    private final NumberValidator numberValidator;

    private TypeWorkoutCreateValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
        this.numberValidator = NumberValidator.getInstance();
    }

    public static TypeWorkoutCreateValidatorDto getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutCreateValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(TypeWorkoutCreateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);
        numberValidator.validate(dto.getRequesterId(), PARAM_REQUESTER_ID);
        objectValidator.validate(dto.getTypeName(), TYPE_NAME);
        numberValidator.validate(dto.getCaloriePerHour(), CALORIE_PER_HOUR);
        numberValidator.validate(dto.getDetailOfTypeId(), DETAIL_OF_TYPE_ID);
    }
}