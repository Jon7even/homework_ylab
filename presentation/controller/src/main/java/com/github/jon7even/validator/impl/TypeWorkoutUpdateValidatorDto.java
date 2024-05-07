package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.PARAM_REQUESTER_ID;
import static com.github.jon7even.constants.ControllerParam.PARAM_TYPE_WORKOUT_ID;

public class TypeWorkoutUpdateValidatorDto implements ValidatorDto<TypeWorkoutUpdateDto> {
    private final LongValidator validatorLong;
    private final StringValidator validatorString;
    private static TypeWorkoutUpdateValidatorDto instance;

    public static TypeWorkoutUpdateValidatorDto getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutUpdateValidatorDto();
        }
        return instance;
    }

    private TypeWorkoutUpdateValidatorDto() {
        this.validatorLong = LongValidator.getInstance();
        this.validatorString = StringValidator.getInstance();
    }

    @Override
    public void validate(TypeWorkoutUpdateDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }
        validatorLong.validate(String.valueOf(dto.getRequesterId()), PARAM_REQUESTER_ID);
        validatorLong.validate(String.valueOf(dto.getTypeWorkoutId()), PARAM_TYPE_WORKOUT_ID);
        validatorString.validate(dto.getTypeName(), "typeName");
    }
}