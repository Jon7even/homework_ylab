package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringValidator implements Validator<String> {
    private static StringValidator instance;

    public static StringValidator getInstance() {
        if (instance == null) {
            instance = new StringValidator();
        }
        return instance;
    }

    @Override
    public void validate(String obj, String name) {
        if (obj == null || obj.isBlank()) {
            throw new MethodArgumentNotValidException(name, "не может быть пустым");
        }
    }
}