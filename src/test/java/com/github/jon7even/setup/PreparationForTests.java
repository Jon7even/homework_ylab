package com.github.jon7even.setup;

import com.github.jon7even.application.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.application.dto.user.UserUpdateDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.NameType;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;

import java.time.Duration;
import java.time.LocalDateTime;
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

    protected DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntityTraveled;
    protected DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntityExercises;
    protected DetailOfTypeWorkoutEntity detailOfTypeWorkoutEntityNotDetails;

    protected TypeWorkoutEntity typeWorkoutEntityWalking;
    protected TypeWorkoutEntity typeWorkoutEntityRunning;
    protected TypeWorkoutEntity typeWorkoutEntityStrength;
    protected TypeWorkoutEntity typeWorkoutEntityTreadmill;
    protected TypeWorkoutEntity typeWorkoutEntityBicycling;
    protected TypeWorkoutEntity typeWorkoutEntityYoga;
    protected TypeWorkoutEntity typeWorkoutEntitySwimming;
    protected TypeWorkoutEntity typeWorkoutEntityTennis;
    protected TypeWorkoutEntity typeWorkoutEntitySkiing;

    protected DetailOfTypeWorkoutResponseDto detailOfTypeWorkoutResponseDtoTraveled;
    protected DetailOfTypeWorkoutResponseDto detailOfTypeWorkoutResponseDtoExercises;
    protected DetailOfTypeWorkoutResponseDto detailOfTypeWorkoutResponseDtoNotDetails;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoWalking;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoRunning;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoStrength;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoTreadmill;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoBicycling;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoYoga;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoSwimming;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoTennis;
    protected TypeWorkoutResponseDto typeWorkoutResponseDtoSkiing;

    protected void initTypeWorkoutResponseDto() {
        detailOfTypeWorkoutResponseDtoTraveled = DetailOfTypeWorkoutResponseDto.builder()
                .id(1)
                .name("Пройденное расстояние (м)")
                .isFillingRequired(true)
                .build();

        detailOfTypeWorkoutResponseDtoExercises = DetailOfTypeWorkoutResponseDto.builder()
                .id(2)
                .name("Количество выполненных упражнений")
                .isFillingRequired(true)
                .build();

        detailOfTypeWorkoutResponseDtoNotDetails = DetailOfTypeWorkoutResponseDto.builder()
                .id(3)
                .name("Занятие не требует дополнительных параметров")
                .isFillingRequired(false)
                .build();

        typeWorkoutResponseDtoWalking = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Простая ходьба")
                .caloriePerHour(200)
                .build();

        typeWorkoutResponseDtoRunning = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Бег на улице")
                .caloriePerHour(500)
                .build();

        typeWorkoutResponseDtoStrength = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoExercises)
                .typeName("Силовая тренировка")
                .caloriePerHour(520)
                .build();

        typeWorkoutResponseDtoTreadmill = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Бег на беговой дорожке")
                .caloriePerHour(400)
                .build();

        typeWorkoutResponseDtoBicycling = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Велоспорт")
                .caloriePerHour(450)
                .build();

        typeWorkoutResponseDtoYoga = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoNotDetails)
                .typeName("Занятия йогой")
                .caloriePerHour(225)
                .build();

        typeWorkoutResponseDtoSwimming = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Плавать в бассейне")
                .caloriePerHour(230)
                .build();

        typeWorkoutResponseDtoTennis = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoNotDetails)
                .typeName("Игра в теннис")
                .caloriePerHour(400)
                .build();

        typeWorkoutResponseDtoSkiing = TypeWorkoutResponseDto.builder()
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Ходьба на лыжах")
                .caloriePerHour(485)
                .build();
    }

    protected void initTypeWorkoutEntity() {
        detailOfTypeWorkoutEntityTraveled = DetailOfTypeWorkoutEntity.builder()
                .id(1)
                .name("Пройденное расстояние (м)")
                .isFillingRequired(true)
                .build();

        detailOfTypeWorkoutEntityExercises = DetailOfTypeWorkoutEntity.builder()
                .id(2)
                .name("Количество выполненных упражнений")
                .isFillingRequired(true)
                .build();

        detailOfTypeWorkoutEntityNotDetails = DetailOfTypeWorkoutEntity.builder()
                .id(3)
                .name("Занятие не требует дополнительных параметров")
                .isFillingRequired(false)
                .build();

        typeWorkoutEntityWalking = TypeWorkoutEntity.builder()
                .id(1L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Простая ходьба")
                .caloriePerHour(200)
                .idTypeService(4)
                .build();

        typeWorkoutEntityRunning = TypeWorkoutEntity.builder()
                .id(2L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Бег на улице")
                .caloriePerHour(500)
                .idTypeService(4)
                .build();

        typeWorkoutEntityStrength = TypeWorkoutEntity.builder()
                .id(3L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityExercises)
                .typeName("Силовая тренировка")
                .caloriePerHour(520)
                .idTypeService(4)
                .build();

        typeWorkoutEntityTreadmill = TypeWorkoutEntity.builder()
                .id(4L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Бег на беговой дорожке")
                .caloriePerHour(400)
                .idTypeService(4)
                .build();

        typeWorkoutEntityBicycling = TypeWorkoutEntity.builder()
                .id(5L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Велоспорт")
                .caloriePerHour(450)
                .idTypeService(4)
                .build();

        typeWorkoutEntityYoga = TypeWorkoutEntity.builder()
                .id(6L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityNotDetails)
                .typeName("Занятия йогой")
                .caloriePerHour(225)
                .idTypeService(4)
                .build();

        typeWorkoutEntitySwimming = TypeWorkoutEntity.builder()
                .id(7L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Плавать в бассейне")
                .caloriePerHour(230)
                .idTypeService(4)
                .build();

        typeWorkoutEntityTennis = TypeWorkoutEntity.builder()
                .id(8L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityNotDetails)
                .typeName("Игра в теннис")
                .caloriePerHour(400)
                .idTypeService(4)
                .build();

        typeWorkoutEntitySkiing = TypeWorkoutEntity.builder()
                .id(9L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Ходьба на лыжах")
                .caloriePerHour(485)
                .idTypeService(4)
                .build();
    }

    protected LocalDateTime timeStartOne;
    protected LocalDateTime timeStartSecond;
    protected LocalDateTime timeStartThird;
    protected LocalDateTime timeEndOne;
    protected LocalDateTime timeEndSecond;
    protected LocalDateTime timeEndThird;
    protected Duration periodOne;
    protected Duration periodSecond;
    protected Duration periodThird;

    protected void initLocalDateTime() {
        timeStartOne = LocalDateTime.of(2024, 4, 14, 10, 0);
        timeEndOne = LocalDateTime.of(2024, 4, 14, 11, 20);
        timeStartSecond = LocalDateTime.of(2024, 4, 15, 17, 17);
        timeEndSecond = LocalDateTime.of(2024, 4, 15, 17, 57);
        timeStartThird = LocalDateTime.of(2024, 4, 16, 17, 17);
        timeEndThird = LocalDateTime.of(2024, 4, 16, 20, 7);
        periodOne = Duration.ofMinutes(20L);
        periodSecond = Duration.ofMinutes(37L);
        periodThird = Duration.ofMinutes(47L);
    }

    protected WorkoutFullResponseDto workoutFullResponseDtoFirst;
    protected WorkoutFullResponseDto workoutFullResponseDtoSecond;
    protected WorkoutFullResponseDto workoutFullResponseDtoThird;

    protected void initWorkoutDto() {
        workoutFullResponseDtoFirst = WorkoutFullResponseDto.builder()
                .id(firstIdLong)
                .idDiary(firstIdLong)
                .typeWorkoutResponseDto(typeWorkoutResponseDtoWalking)
                .timeStartOn(timeStartOne)
                .timeEndOn(timeEndOne)
                .timeOfRest(periodOne)
                .currentWeightUser(80.5F)
                .personalNote("One")
                .detailOfWorkout("One")
                .build();
        workoutFullResponseDtoSecond = WorkoutFullResponseDto.builder()
                .id(secondIdLong)
                .idDiary(secondIdLong)
                .typeWorkoutResponseDto(typeWorkoutResponseDtoStrength)
                .timeStartOn(timeStartSecond)
                .timeEndOn(timeEndSecond)
                .timeOfRest(periodSecond)
                .currentWeightUser(79.9F)
                .personalNote("Second")
                .detailOfWorkout("Second")
                .build();
        workoutFullResponseDtoThird = WorkoutFullResponseDto.builder()
                .id(thirdIdLong)
                .idDiary(thirdIdLong)
                .typeWorkoutResponseDto(typeWorkoutResponseDtoYoga)
                .timeStartOn(timeStartThird)
                .timeEndOn(timeEndThird)
                .timeOfRest(periodThird)
                .currentWeightUser(79.7F)
                .personalNote("Third")
                .detailOfWorkout("Без деталей")
                .build();
    }

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
