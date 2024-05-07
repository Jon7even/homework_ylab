package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateDtoValidatorDto implements ValidatorDto<UserCreateDto> {
    private static UserCreateDtoValidatorDto instance;

    public static UserCreateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new UserCreateDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(UserCreateDto dto) {
        if (dto == null) {
            throw new MethodArgumentNotValidException("Object DTO", "не может быть пустым");
        }

        if (dto.getLogin() == null || dto.getLogin().isBlank()) {
            throw new MethodArgumentNotValidException("login", "не может быть пустым");
        }

        if (dto.getLogin().length() < 3 || dto.getLogin().length() > 64) {
            throw new MethodArgumentNotValidException("login",
                    "количество символов в поле должно находиться в диапазоне от 3 до 64 символов");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new MethodArgumentNotValidException("password", "не может быть пустым");
        }

        if (dto.getPassword().length() < 4 || dto.getPassword().length() > 128) {
            throw new MethodArgumentNotValidException("password",
                    "количество символов в поле должно находиться в диапазоне от 4 до 128 символов");
        }
    }
}
