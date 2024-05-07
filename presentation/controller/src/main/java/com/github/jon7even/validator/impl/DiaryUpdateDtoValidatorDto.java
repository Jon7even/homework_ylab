package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryUpdateDtoValidatorDto implements ValidatorDto<DiaryUpdateDto> {
    private static DiaryUpdateDtoValidatorDto instance;

    public static DiaryUpdateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new DiaryUpdateDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(DiaryUpdateDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }
    }
}
