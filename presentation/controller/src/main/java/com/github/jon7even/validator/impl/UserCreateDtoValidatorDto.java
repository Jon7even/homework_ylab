package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class UserCreateDtoValidatorDto implements ValidatorDto<UserCreateDto> {
    private static UserCreateDtoValidatorDto instance;
    private final ObjectValidator objectValidator;

    private UserCreateDtoValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
    }

    public static UserCreateDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new UserCreateDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(UserCreateDto dto) {
        objectValidator.validate(dto, OBJECT_DTO);

        objectValidator.validate(dto.getLogin(), LOGIN);
        if (dto.getLogin().length() < 3 || dto.getLogin().length() > 64) {
            throw new MethodArgumentNotValidException(LOGIN,
                    "количество символов в поле должно находиться в диапазоне от 3 до 64 символов");
        }

        objectValidator.validate(dto.getPassword(), PASSWORD);
        if (dto.getPassword().length() < 4 || dto.getPassword().length() > 128) {
            throw new MethodArgumentNotValidException(PASSWORD,
                    "количество символов в поле должно находиться в диапазоне от 4 до 128 символов");
        }
    }
}
