package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectValidator implements Validator<Object> {
    private static ObjectValidator instance;

    public static ObjectValidator getInstance() {
        if (instance == null) {
            instance = new ObjectValidator();
        }
        return instance;
    }

    @Override
    public void validate(Object obj, String name) {
        if (obj == null || obj instanceof String str && str.isBlank()) {
            throw new MethodArgumentNotValidException(name, "не может быть пустым");
        }
    }
}