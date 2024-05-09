package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;

public class ParamValidator implements Validator<Object> {
    private static ParamValidator instance;
    private final NumberValidator numberValidator;

    private ParamValidator() {
        this.numberValidator = NumberValidator.getInstance();
    }

    public static ParamValidator getInstance() {
        if (instance == null) {
            instance = new ParamValidator();
        }
        return instance;
    }

    @Override
    public void validate(Object number, String name) {
        numberValidator.validate(number, name);

        if (Long.parseLong(String.valueOf(number)) < 1) {
            throw new MethodArgumentNotValidException(name, "должен быть положительным");
        }
    }
}