package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.constants.ControllerParam.PARAM_USER_ID;
import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class DiaryUpdateDtoValidatorDto implements ValidatorDto<DiaryUpdateDto> {
    private static DiaryUpdateDtoValidatorDto instance;
    private final ObjectValidator objectValidator;
    private final NumberValidator numberValidator;

    private DiaryUpdateDtoValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
        this.numberValidator = NumberValidator.getInstance();
    }

    public static DiaryUpdateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new DiaryUpdateDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(DiaryUpdateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);
        numberValidator.validate(dto.getUserId(), PARAM_USER_ID);
        numberValidator.validate(dto.getWeightUser(), USER_WEIGHT);
        numberValidator.validate(dto.getGrowthUser(), USER_GROWTH);
    }
}
