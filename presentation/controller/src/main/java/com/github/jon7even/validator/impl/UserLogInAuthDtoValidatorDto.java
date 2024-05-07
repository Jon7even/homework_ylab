package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

public class UserLogInAuthDtoValidatorDto implements ValidatorDto<UserLogInAuthDto> {
    private final StringValidator stringValidator;
    private static UserLogInAuthDtoValidatorDto instance;

    public static UserLogInAuthDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new UserLogInAuthDtoValidatorDto();
        }
        return instance;
    }

    private UserLogInAuthDtoValidatorDto() {
        this.stringValidator = StringValidator.getInstance();
    }

    @Override
    public void validate(UserLogInAuthDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }

        stringValidator.validate(dto.getLogin(), "login");

        if (dto.getLogin().length() < 3 || dto.getLogin().length() > 64) {
            throw new MethodArgumentNotValidException("login",
                    "количество символов в поле должно находиться в диапазоне от 3 до 64 символов");
        }

        stringValidator.validate(dto.getPassword(), "password");

        if (dto.getPassword().length() < 4 || dto.getPassword().length() > 128) {
            throw new MethodArgumentNotValidException("password",
                    "количество символов в поле должно находиться в диапазоне от 4 до 128 символов");
        }
    }
}

