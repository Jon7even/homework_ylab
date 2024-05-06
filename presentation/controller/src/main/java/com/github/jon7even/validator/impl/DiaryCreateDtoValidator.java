package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryCreateDtoValidator implements Validator<DiaryCreateDto> {
    private static DiaryCreateDtoValidator instance;

    public static DiaryCreateDtoValidator getInstance() {
        if (instance == null) {
            instance = new DiaryCreateDtoValidator();
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
