package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.PARAM_USER_ID;
import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class DiaryCreateDtoValidatorDto implements ValidatorDto<DiaryCreateDto> {
    private static DiaryCreateDtoValidatorDto instance;
    private final ObjectValidator objectValidator;
    private final NumberValidator numberValidator;

    private DiaryCreateDtoValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
        this.numberValidator = NumberValidator.getInstance();
    }

    public static DiaryCreateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new DiaryCreateDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(DiaryCreateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);
        numberValidator.validate(dto.getUserId(), PARAM_USER_ID);
        numberValidator.validate(dto.getWeightUser(), USER_WEIGHT);
        numberValidator.validate(dto.getGrowthUser(), USER_GROWTH);
    }
}