package com.github.jon7even.setup;

import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.application.dto.user.UserUpdateDto;
import com.github.jon7even.core.domain.v1.entities.UserEntity;

public class PreparationForTests {
    protected Long firstIdLong = 1L;
    protected Long secondIdLong = 2L;
    protected Long thirdIdLong = 3L;
    protected Integer firstIdInteger = 1;
    protected Integer secondIdInteger = 2;
    protected Integer thirdIdInteger = 3;
    protected UserEntity userEntityFirstExpected;
    protected UserEntity userEntityFirstForCreate;
    protected UserEntity userEntitySecondExpected;
    protected UserEntity userEntitySecondForCreate;
    protected UserEntity userAdmin;
    protected String userLoginFirst = "UserFirst";
    protected String userLoginSecond = "UserSecond";
    protected String userPasswordFirst = "qwertyfirst";
    protected String userPasswordSecond = "qwertysecond";

    public static final String ADMIN_LOGIN = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    protected UserCreateDto userCreateDtoFirst;
    protected UserUpdateDto userUpdateDto;
    protected UserShortResponseDto userShortResponseDto;

    protected void initUsers() {
        userEntityFirstExpected = UserEntity.builder()
                .id(secondIdLong)
                .login(userLoginFirst)
                .password(userPasswordFirst)
                .idGroupPermissions(firstIdInteger)
                .build();
        userEntitySecondExpected = UserEntity.builder()
                .id(thirdIdLong)
                .login(userLoginSecond)
                .password(userPasswordSecond)
                .idGroupPermissions(secondIdInteger)
                .build();
        userEntityFirstForCreate = UserEntity.builder()
                .login(userLoginFirst)
                .password(userPasswordFirst)
                .idGroupPermissions(firstIdInteger)
                .build();
        userEntitySecondForCreate = UserEntity.builder()
                .login(userLoginSecond)
                .password(userPasswordSecond)
                .idGroupPermissions(secondIdInteger)
                .build();
        userAdmin = UserEntity.builder()
                .id(firstIdLong)
                .login(ADMIN_LOGIN)
                .password(ADMIN_PASSWORD)
                .idGroupPermissions(secondIdInteger)
                .build();
    }

    protected void initUsersDto() {
        userCreateDtoFirst = UserCreateDto.builder()
                .login("UserDtoCreate")
                .password("UserDtoUpdate")
                .idGroupPermissions(1)
                .build();
        userUpdateDto = UserUpdateDto.builder()
                .password("newPassword")
                .idGroupPermissions(1)
                .build();
        userShortResponseDto = UserShortResponseDto.builder()
                .login("LoginForResponse")
                .build();
    }
}
