package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import liquibase.repackaged.org.apache.commons.lang3.math.NumberUtils;

public class NumberValidator implements Validator<Object> {
    private static NumberValidator instance;
    private final ObjectValidator objectValidator;

    private NumberValidator() {
        this.objectValidator = ObjectValidator.getInstance();
    }

    public static NumberValidator getInstance() {
        if (instance == null) {
            instance = new NumberValidator();
        }
        return instance;
    }

    @Override
    public void validate(Object number, String name) {
        objectValidator.validate(number, name);

        if (!NumberUtils.isCreatable(number.toString())) {
            throw new MethodArgumentNotValidException(name, "должен быть числом");
        }

        if (number instanceof Long) {
            Long numberLong = (Long) number;
            if (numberLong < 1L) {
                throw new MethodArgumentNotValidException(name, "должен быть положительным");
            }
        }

        if (number instanceof Float) {
            Float numberFloat = (Float) number;
            if (numberFloat < 1.0f) {
                throw new MethodArgumentNotValidException(name, "должен быть положительным");
            }
        }

        if (number instanceof Integer) {
            Integer numberInteger = (Integer) number;
            if (numberInteger < 1) {
                throw new MethodArgumentNotValidException(name, "должен быть положительным");
            }
        }
    }
}