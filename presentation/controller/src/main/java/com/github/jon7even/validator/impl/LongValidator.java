package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LongValidator implements Validator<String> {
    private static LongValidator instance;

    public static LongValidator getInstance() {
        if (instance == null) {
            instance = new LongValidator();
        }
        return instance;
    }

    @Override
    public void validate(String obj, String name) {
        if (obj == null || obj.isBlank()) {
            throw new MethodArgumentNotValidException(name, "не может быть пустым");
        }
        long id;

        try {
            id = Long.parseLong(obj);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            throw new MethodArgumentNotValidException(name, "должен быть числом");
        }

        if (id < 1) {
            throw new MethodArgumentNotValidException(name, "должен быть положительным");
        }
    }
}