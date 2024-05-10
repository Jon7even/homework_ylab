package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.ValidatorDto;

import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

public class UserLogInAuthDtoValidatorDto implements ValidatorDto<UserLogInAuthDto> {
    private static UserLogInAuthDtoValidatorDto instance;
    private final ObjectValidator objectValidator;

    private UserLogInAuthDtoValidatorDto() {
        this.objectValidator = ObjectValidator.getInstance();
    }

    public static UserLogInAuthDtoValidatorDto getInstance() {
        if (instance == null) {
            instance = new UserLogInAuthDtoValidatorDto();
        }
        return instance;
    }

    @Override
    public void validate(UserLogInAuthDto dto) {
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

