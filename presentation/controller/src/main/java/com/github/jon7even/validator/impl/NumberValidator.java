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

        if (!NumberUtils.isCreatable(String.valueOf(number))) {
            throw new MethodArgumentNotValidException(name, "должен быть числом");
        }

        if (number instanceof Long ln && ln < 1L
                || number instanceof Float fl && fl < 1.0f
                || number instanceof Integer inr && inr < 1) {
            throw new MethodArgumentNotValidException(name, "должен быть положительным");
        }
    }
}