package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryUpdateDtoValidator implements Validator<DiaryUpdateDto> {
    private static DiaryUpdateDtoValidator instance;

    public static DiaryUpdateDtoValidator getInstance() {
        if (instance == null) {
            instance = new DiaryUpdateDtoValidator();
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
