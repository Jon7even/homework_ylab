package com.github.jon7even.setup;

import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.application.dto.user.UserUpdateDto;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.NameType;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;

import java.util.HashSet;
import java.util.Set;

public class PreparationForTests {
    protected Long firstIdLong = 1L;
    protected Long secondIdLong = 2L;
    protected Long thirdIdLong = 3L;
    protected Integer firstIdInteger = 1;
    protected Integer secondIdInteger = 2;
    protected Integer thirdIdInteger = 3;
    protected Integer fourthIdInteger = 4;
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
    protected UserUpdateDto userUpdateDtoFirst;
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
        userUpdateDtoFirst = UserUpdateDto.builder()
                .password("newPassword")
                .idGroupPermissions(2)
                .build();
        userShortResponseDto = UserShortResponseDto.builder()
                .login("LoginForResponse")
                .build();
    }

    protected GroupPermissionsEntity groupPermissionsForCreateFirst;
    protected GroupPermissionsEntity groupPermissionsForCreateSecond;
    protected GroupPermissionsEntity groupPermissionsForExpectedFirst;
    protected GroupPermissionsEntity groupPermissionsForExpectedSecond;
    protected Set<TypeServiceEntity> adminListPermissionsFirst;
    protected Set<TypeServiceEntity> userListPermissionsSecond;
    protected NameType nameTypeHistory;
    protected NameType nameTypeDiary;
    protected NameType nameTypeWorkout;
    protected NameType nameTypeWorkoutType;

    protected TypeServiceEntity historyAdmin;

    protected TypeServiceEntity diaryAdmin;

    protected TypeServiceEntity workoutAdmin;

    protected TypeServiceEntity workoutTypeAdmin;

    protected TypeServiceEntity historyUser;

    protected TypeServiceEntity diaryUser;

    protected TypeServiceEntity workoutUser;

    protected TypeServiceEntity workoutTypeUser;

    protected void initGroupPermissions() {
        nameTypeHistory = NameType.builder()
                .id(1)
                .name("History Service")
                .build();
        nameTypeDiary = NameType.builder()
                .id(2)
                .name("Diary Service")
                .build();
        nameTypeWorkout = NameType.builder()
                .id(3)
                .name("Workout Service")
                .build();
        nameTypeWorkoutType = NameType.builder()
                .id(4)
                .name("TypeWorkout Service")
                .build();

        historyAdmin = TypeServiceEntity.builder()
                .id(1)
                .nameType(nameTypeHistory)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        diaryAdmin = TypeServiceEntity.builder()
                .id(2)
                .nameType(nameTypeDiary)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        workoutAdmin = TypeServiceEntity.builder()
                .id(3)
                .nameType(nameTypeWorkout)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        workoutTypeAdmin = TypeServiceEntity.builder()
                .id(4)
                .nameType(nameTypeWorkoutType)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        historyUser = TypeServiceEntity.builder()
                .id(5)
                .nameType(nameTypeHistory)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        diaryUser = TypeServiceEntity.builder()
                .id(6)
                .nameType(nameTypeDiary)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        workoutUser = TypeServiceEntity.builder()
                .id(7)
                .nameType(nameTypeWorkout)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        workoutTypeUser = TypeServiceEntity.builder()
                .id(8)
                .nameType(nameTypeWorkoutType)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        adminListPermissionsFirst = new HashSet<>(
                Set.of(historyUser, diaryUser, workoutUser, workoutTypeUser)
        );

        userListPermissionsSecond = new HashSet<>(
                Set.of(historyAdmin, diaryAdmin, workoutAdmin, workoutTypeAdmin)
        );

        groupPermissionsForCreateFirst = GroupPermissionsEntity.builder()
                .name("Moderator")
                .servicesList(adminListPermissionsFirst)
                .build();

        groupPermissionsForCreateSecond = GroupPermissionsEntity.builder()
                .name("Vip User")
                .servicesList(userListPermissionsSecond)
                .build();

        groupPermissionsForExpectedFirst = GroupPermissionsEntity.builder()
                .id(thirdIdInteger)
                .name("Moderator")
                .servicesList(adminListPermissionsFirst)
                .build();

        groupPermissionsForExpectedSecond = GroupPermissionsEntity.builder()
                .id(fourthIdInteger)
                .name("Vip User")
                .servicesList(userListPermissionsSecond)
                .build();
    }
}
