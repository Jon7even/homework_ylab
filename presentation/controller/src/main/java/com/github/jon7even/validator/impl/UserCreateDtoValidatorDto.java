package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

public class UserCreateDtoValidatorDto implements ValidatorDto<UserCreateDto> {
    private final StringValidator stringValidator;
    private static UserCreateDtoValidatorDto instance;

    public static UserCreateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new UserCreateDtoValidatorDto();
        }
        return instance;
    }

    private UserCreateDtoValidatorDto() {
        this.stringValidator = StringValidator.getInstance();
    }

    @Override
    public void validate(UserCreateDto dto) {
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
