package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryCreateDtoValidatorDto implements ValidatorDto<DiaryCreateDto> {
    private static DiaryCreateDtoValidatorDto instance;

    public static DiaryCreateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new DiaryCreateDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(DiaryCreateDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }
    }
}
