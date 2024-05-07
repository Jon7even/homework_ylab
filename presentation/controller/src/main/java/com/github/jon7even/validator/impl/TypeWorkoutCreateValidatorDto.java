package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.PARAM_REQUESTER_ID;

public class TypeWorkoutCreateValidatorDto implements ValidatorDto<TypeWorkoutCreateDto> {
    private final LongValidator validatorLong;
    private final StringValidator validatorString;
    private static TypeWorkoutCreateValidatorDto instance;

    public static TypeWorkoutCreateValidatorDto getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutCreateValidatorDto();
        }
        return instance;
    }

    private TypeWorkoutCreateValidatorDto() {
        this.validatorLong = LongValidator.getInstance();
        this.validatorString = StringValidator.getInstance();
    }

    @Override
    public void validate(TypeWorkoutCreateDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }
        validatorLong.validate(String.valueOf(dto.getRequesterId()), PARAM_REQUESTER_ID);
        validatorString.validate(dto.getTypeName(), "typeName");
    }
}

