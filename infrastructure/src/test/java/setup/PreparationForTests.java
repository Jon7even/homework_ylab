package setup;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserUpdateDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.NameType;
import com.github.jon7even.core.domain.v1.entities.permissions.TypeServiceEntity;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreparationForTests {
    protected Integer idTypeServiceHistory = 1;
    protected Integer idTypeServiceDiary = 2;
    protected Integer idTypeServiceWorkout = 3;
    protected Integer idTypeServiceTypeWorkout = 4;
    protected Integer idTypeServiceUser = 5;
    protected Long firstIdLong = 1L;
    protected Long secondIdLong = 2L;
    protected Long thirdIdLong = 3L;
    protected Integer firstIdInteger = 1;
    protected Integer secondIdInteger = 2;
    protected Integer thirdIdInteger = 3;
    protected Integer fourthIdInteger = 4;
    protected Integer DEFAULT_ADMIN_GROUP = firstIdInteger;
    protected Integer DEFAULT_USER_GROUP = secondIdInteger;
    protected UserEntity userEntityFirst;
    protected UserEntity userEntityForCreateFirst;
    protected UserEntity userEntityForUpdateFirst;
    protected UserEntity userEntitySecond;
    protected UserEntity userEntityForCreateSecond;
    protected UserEntity userEntityForUpdateSecond;
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

    protected TypeWorkoutCreateDto typeWorkoutCreateDtoWalking;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoRunning;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoStrength;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoTreadmill;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoBicycling;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoYoga;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoSwimming;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoTennis;
    protected TypeWorkoutCreateDto typeWorkoutCreateDtoSkiing;

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

    protected TypeWorkoutUpdateDto typeWorkoutUpdateFirst;
    protected TypeWorkoutUpdateDto typeWorkoutUpdateSecond;
    protected TypeWorkoutUpdateDto typeWorkoutUpdateThird;

    protected void initTypeWorkoutDto() {
        initTypeWorkoutEntity();
        typeWorkoutUpdateFirst = TypeWorkoutUpdateDto.builder()
                .typeName("Full update")
                .caloriePerHour(240)
                .build();

        typeWorkoutUpdateSecond = TypeWorkoutUpdateDto.builder()
                .typeName("Only name")
                .build();

        typeWorkoutUpdateThird = TypeWorkoutUpdateDto.builder()
                .caloriePerHour(270)
                .build();

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
                .typeWorkoutId(1L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Простая ходьба")
                .caloriePerHour(200)
                .build();

        typeWorkoutResponseDtoRunning = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(2L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Бег на улице")
                .caloriePerHour(500)
                .build();

        typeWorkoutResponseDtoStrength = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(3L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoExercises)
                .typeName("Силовая тренировка")
                .caloriePerHour(520)
                .build();

        typeWorkoutResponseDtoTreadmill = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(4L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Бег на беговой дорожке")
                .caloriePerHour(400)
                .build();

        typeWorkoutResponseDtoBicycling = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(5L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Велоспорт")
                .caloriePerHour(450)
                .build();

        typeWorkoutResponseDtoYoga = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(6L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoNotDetails)
                .typeName("Занятия йогой")
                .caloriePerHour(225)
                .build();

        typeWorkoutResponseDtoSwimming = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(7L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Плавать в бассейне")
                .caloriePerHour(230)
                .build();

        typeWorkoutResponseDtoTennis = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(8L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoNotDetails)
                .typeName("Игра в теннис")
                .caloriePerHour(400)
                .build();

        typeWorkoutResponseDtoSkiing = TypeWorkoutResponseDto.builder()
                .typeWorkoutId(9L)
                .detailOfTypeWorkoutResponseDto(detailOfTypeWorkoutResponseDtoTraveled)
                .typeName("Ходьба на лыжах")
                .caloriePerHour(485)
                .build();

        typeWorkoutCreateDtoWalking = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .typeName("Простая ходьба")
                .caloriePerHour(200)
                .detailOfTypeId(detailOfTypeWorkoutEntityTraveled.getId())
                .build();

        typeWorkoutCreateDtoRunning = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityTraveled.getId())
                .typeName("Бег на улице")
                .caloriePerHour(500)
                .build();

        typeWorkoutCreateDtoStrength = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityExercises.getId())
                .typeName("Силовая тренировка")
                .caloriePerHour(520)
                .build();

        typeWorkoutCreateDtoTreadmill = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityTraveled.getId())
                .typeName("Бег на беговой дорожке")
                .caloriePerHour(400)
                .build();

        typeWorkoutCreateDtoBicycling = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityTraveled.getId())
                .typeName("Велоспорт")
                .caloriePerHour(450)
                .build();

        typeWorkoutCreateDtoYoga = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityNotDetails.getId())
                .typeName("Занятия йогой")
                .caloriePerHour(225)
                .build();

        typeWorkoutCreateDtoSwimming = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityTraveled.getId())
                .typeName("Плавать в бассейне")
                .caloriePerHour(230)
                .build();

        typeWorkoutCreateDtoTennis = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityNotDetails.getId())
                .typeName("Игра в теннис")
                .caloriePerHour(400)
                .build();

        typeWorkoutCreateDtoSkiing = TypeWorkoutCreateDto.builder()
                .requesterId(firstIdLong)
                .detailOfTypeId(detailOfTypeWorkoutEntityTraveled.getId())
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
                .build();

        typeWorkoutEntityRunning = TypeWorkoutEntity.builder()
                .id(2L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Бег на улице")
                .caloriePerHour(500)
                .build();

        typeWorkoutEntityStrength = TypeWorkoutEntity.builder()
                .id(3L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityExercises)
                .typeName("Силовая тренировка")
                .caloriePerHour(520)
                .build();

        typeWorkoutEntityTreadmill = TypeWorkoutEntity.builder()
                .id(4L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Бег на беговой дорожке")
                .caloriePerHour(400)
                .build();

        typeWorkoutEntityBicycling = TypeWorkoutEntity.builder()
                .id(5L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Велоспорт")
                .caloriePerHour(450)
                .build();

        typeWorkoutEntityYoga = TypeWorkoutEntity.builder()
                .id(6L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityNotDetails)
                .typeName("Занятия йогой")
                .caloriePerHour(225)
                .build();

        typeWorkoutEntitySwimming = TypeWorkoutEntity.builder()
                .id(7L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Плавать в бассейне")
                .caloriePerHour(230)
                .build();

        typeWorkoutEntityTennis = TypeWorkoutEntity.builder()
                .id(8L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityNotDetails)
                .typeName("Игра в теннис")
                .caloriePerHour(400)
                .build();

        typeWorkoutEntitySkiing = TypeWorkoutEntity.builder()
                .id(9L)
                .detailOfTypeWorkoutEntity(detailOfTypeWorkoutEntityTraveled)
                .typeName("Ходьба на лыжах")
                .caloriePerHour(485)
                .build();
    }

    protected LocalDateTime timeStartFirst;
    protected LocalDateTime timeStartSecond;
    protected LocalDateTime timeStartThird;
    protected LocalDateTime timeEndFirst;
    protected LocalDateTime timeEndSecond;
    protected LocalDateTime timeEndThird;
    protected LocalDateTime timeUpdateFirst;
    protected LocalDateTime timeUpdateSecond;
    protected LocalDateTime timeUpdateThird;
    protected Duration periodOne;
    protected Duration periodSecond;
    protected Duration periodThird;

    protected void initLocalDateTime() {
        timeStartFirst = LocalDateTime.of(2024, 4, 14, 10, 0);
        timeEndFirst = LocalDateTime.of(2024, 4, 14, 11, 20);
        timeStartSecond = LocalDateTime.of(2024, 4, 15, 17, 17);
        timeEndSecond = LocalDateTime.of(2024, 4, 15, 17, 57);
        timeStartThird = LocalDateTime.of(2024, 4, 16, 17, 17);
        timeEndThird = LocalDateTime.of(2024, 4, 16, 20, 7);
        periodOne = Duration.ofMinutes(20L);
        periodSecond = Duration.ofMinutes(37L);
        periodThird = Duration.ofMinutes(47L);
        timeUpdateFirst = timeStartFirst.plusDays(1);
        timeUpdateSecond = timeStartSecond.plusDays(1);
        timeUpdateThird = timeStartThird.plusDays(1);
    }

    protected void initUsersEntity() {
        userEntityFirst = UserEntity.builder()
                .id(secondIdLong)
                .login(userLoginFirst)
                .password(userPasswordFirst)
                .idGroupPermissions(firstIdInteger)
                .build();
        userEntitySecond = UserEntity.builder()
                .id(thirdIdLong)
                .login(userLoginSecond)
                .password(userPasswordSecond)
                .idGroupPermissions(secondIdInteger)
                .build();
        userEntityForCreateFirst = UserEntity.builder()
                .login(userLoginFirst)
                .password(userPasswordFirst)
                .idGroupPermissions(firstIdInteger)
                .build();
        userEntityForCreateSecond = UserEntity.builder()
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
        userEntityForUpdateFirst = UserEntity.builder()
                .id(firstIdLong)
                .login("UpdatedUserFirst")
                .password("UpdatedUserFirst")
                .idGroupPermissions(secondIdInteger)
                .build();
        userEntityForUpdateSecond = UserEntity.builder()
                .id(secondIdLong)
                .login("UpdatedUserSecond")
                .password("UpdatedUserSecond")
                .idGroupPermissions(secondIdInteger)
                .build();
    }

    protected void initUsersDto() {
        userCreateDtoFirst = UserCreateDto.builder()
                .login("UserDtoCreate")
                .password("UserDtoUpdate")
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
    protected NameType nameTypeUser;

    protected TypeServiceEntity historyTypeAdmin;
    protected TypeServiceEntity diaryTypeAdmin;
    protected TypeServiceEntity workoutTypeAdmin;
    protected TypeServiceEntity typeWorkoutTypeAdmin;
    protected TypeServiceEntity userTypeAdmin;
    protected TypeServiceEntity historyTypeUser;
    protected TypeServiceEntity diaryTypeUser;
    protected TypeServiceEntity workoutTypeUser;
    protected TypeServiceEntity typeWorkoutTypeUser;
    protected TypeServiceEntity userTypeUser;

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
        nameTypeUser = NameType.builder()
                .id(5)
                .name("User Service")
                .build();

        historyTypeAdmin = TypeServiceEntity.builder()
                .id(1)
                .nameType(nameTypeHistory)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        diaryTypeAdmin = TypeServiceEntity.builder()
                .id(2)
                .nameType(nameTypeDiary)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        workoutTypeAdmin = TypeServiceEntity.builder()
                .id(3)
                .nameType(nameTypeWorkout)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        typeWorkoutTypeAdmin = TypeServiceEntity.builder()
                .id(4)
                .nameType(nameTypeWorkoutType)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        userTypeAdmin = TypeServiceEntity.builder()
                .id(5)
                .nameType(nameTypeUser)
                .update(true)
                .write(true)
                .read(true)
                .delete(true)
                .build();

        historyTypeUser = TypeServiceEntity.builder()
                .id(6)
                .nameType(nameTypeHistory)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        diaryTypeUser = TypeServiceEntity.builder()
                .id(7)
                .nameType(nameTypeDiary)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        workoutTypeUser = TypeServiceEntity.builder()
                .id(8)
                .nameType(nameTypeWorkout)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        typeWorkoutTypeUser = TypeServiceEntity.builder()
                .id(9)
                .nameType(nameTypeWorkoutType)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        userTypeUser = TypeServiceEntity.builder()
                .id(10)
                .nameType(nameTypeUser)
                .update(false)
                .write(false)
                .read(false)
                .delete(false)
                .build();

        adminListPermissionsFirst = new HashSet<>(
                Set.of(historyTypeUser, diaryTypeUser, workoutTypeUser, typeWorkoutTypeUser)
        );

        userListPermissionsSecond = new HashSet<>(
                Set.of(historyTypeAdmin, diaryTypeAdmin, workoutTypeAdmin, typeWorkoutTypeAdmin)
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

    protected HistoryUserCreateDto historyUserCreateDtoFirst;
    protected HistoryUserCreateDto historyUserCreateDtoSecond;
    protected HistoryUserCreateDto historyUserCreateDtoThird;
    protected List<HistoryUserResponseByAdminDto> historyUserResponseByAdminDtoListFirst;
    protected List<HistoryUserResponseByUserDto> historyUserResponseByUserDtoListFirst;

    protected void initHistoryDto() {
        initLocalDateTime();
        historyUserCreateDtoFirst = HistoryUserCreateDto.builder()
                .userId(firstIdLong)
                .event("First event")
                .build();
        historyUserCreateDtoSecond = HistoryUserCreateDto.builder()
                .userId(secondIdLong)
                .event("Second event")
                .build();
        historyUserCreateDtoThird = HistoryUserCreateDto.builder()
                .userId(thirdIdLong)
                .event("Third event")
                .build();

        HistoryUserResponseByAdminDto firstAdmin = HistoryUserResponseByAdminDto.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeStartFirst)
                .event("First event")
                .build();
        HistoryUserResponseByAdminDto secondAdmin = HistoryUserResponseByAdminDto.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeEndSecond)
                .event("Second event")
                .build();
        HistoryUserResponseByAdminDto thirdAdmin = HistoryUserResponseByAdminDto.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeStartThird)
                .event("Third event")
                .build();

        HistoryUserResponseByUserDto firstUser = HistoryUserResponseByUserDto.builder()
                .dateTimeOn(timeStartFirst)
                .event("First event")
                .build();
        HistoryUserResponseByUserDto secondUser = HistoryUserResponseByUserDto.builder()
                .dateTimeOn(timeEndSecond)
                .event("Second event")
                .build();
        HistoryUserResponseByUserDto thirdUser = HistoryUserResponseByUserDto.builder()
                .dateTimeOn(timeStartThird)
                .event("Third event")
                .build();

        historyUserResponseByAdminDtoListFirst = new ArrayList<>(List.of(firstAdmin, secondAdmin, thirdAdmin));
        historyUserResponseByUserDtoListFirst = new ArrayList<>(List.of(firstUser, secondUser, thirdUser));
    }

    protected HistoryUserEntity historyUserEntityFirst;
    protected HistoryUserEntity historyEntitySecond;
    protected HistoryUserEntity historyEntityThird;

    protected List<HistoryUserEntity> historyUserEntityListAdminFirst;
    protected List<HistoryUserEntity> historyUserEntityListUserFirst;

    protected void initHistoryEntity() {
        initLocalDateTime();
        historyUserEntityFirst = HistoryUserEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeStartFirst)
                .event("First event")
                .build();
        historyEntitySecond = HistoryUserEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .dateTimeOn(timeStartSecond)
                .event("Second event")
                .build();
        historyEntityThird = HistoryUserEntity.builder()
                .id(thirdIdLong)
                .userId(thirdIdLong)
                .dateTimeOn(timeStartThird)
                .event("Third event")
                .build();

        HistoryUserEntity firstAdmin = HistoryUserEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeStartFirst)
                .event("First event")
                .build();
        HistoryUserEntity secondAdmin = HistoryUserEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeEndSecond)
                .event("Second event")
                .build();
        HistoryUserEntity thirdAdmin = HistoryUserEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .dateTimeOn(timeStartThird)
                .event("Third event")
                .build();

        HistoryUserEntity firstUser = HistoryUserEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .dateTimeOn(timeStartFirst)
                .event("First event")
                .build();
        HistoryUserEntity secondUser = HistoryUserEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .dateTimeOn(timeEndSecond)
                .event("Second event")
                .build();
        HistoryUserEntity thirdUser = HistoryUserEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .dateTimeOn(timeStartThird)
                .event("Third event")
                .build();

        historyUserEntityListAdminFirst = new ArrayList<>(List.of(firstAdmin, secondAdmin, thirdAdmin));
        historyUserEntityListUserFirst = new ArrayList<>(List.of(firstUser, secondUser, thirdUser));
    }

    protected Float weightUserFirst = 75.5F;
    protected Float weightUserSecond = 85.1F;
    protected Float weightUserThird = 85.1F;
    protected Float weightUserUpdateFirst = 76.2F;
    protected Float weightUserUpdateSecond = 83.4F;
    protected Float weightUserUpdateThird = 63.4F;
    protected Float growthUserFirst = 165.1F;
    protected Float growthUserSecond = 177.7F;
    protected Float growthUserThird = 157.7F;
    protected Float growthUserUpdateFirst = 165.3F;
    protected Float growthUserUpdateSecond = 177.9F;

    protected DiaryEntity diaryEntityFirst;
    protected DiaryEntity diaryEntitySecond;
    protected DiaryEntity diaryEntityThird;
    protected DiaryEntity diaryEntityForUpdateFirst;
    protected DiaryEntity diaryEntityForUpdateSecond;

    protected void initDiaryEntity() {
        initLocalDateTime();
        diaryEntityFirst = DiaryEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .weightUser(weightUserFirst)
                .growthUser(growthUserFirst)
                .createdOn(timeStartFirst)
                .updatedOn(timeEndFirst)
                .build();
        diaryEntitySecond = DiaryEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .weightUser(weightUserSecond)
                .growthUser(growthUserSecond)
                .createdOn(timeStartSecond)
                .updatedOn(timeEndSecond)
                .build();
        diaryEntityThird = DiaryEntity.builder()
                .id(thirdIdLong)
                .userId(thirdIdLong)
                .weightUser(weightUserThird)
                .growthUser(growthUserThird)
                .createdOn(timeStartThird)
                .updatedOn(timeUpdateThird)
                .build();
        diaryEntityForUpdateFirst = DiaryEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .weightUser(weightUserUpdateFirst)
                .growthUser(growthUserUpdateFirst)
                .createdOn(timeUpdateFirst)
                .updatedOn(timeEndFirst)
                .build();
        diaryEntityForUpdateSecond = DiaryEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .weightUser(weightUserUpdateSecond)
                .growthUser(growthUserUpdateSecond)
                .createdOn(timeUpdateFirst)
                .updatedOn(timeEndSecond)
                .build();
    }

    protected DiaryCreateDto diaryCreateDtoFirst;
    protected DiaryCreateDto diaryCreateDtoSecond;
    protected DiaryUpdateDto diaryUpdateDtoFirst;
    protected DiaryUpdateDto diaryUpdateDtoSecond;
    protected DiaryUpdateDto diaryUpdateDtoThird;
    protected DiaryUpdateDto diaryUpdateDtoFourth;

    protected void initDiaryDto() {
        diaryUpdateDtoFirst = DiaryUpdateDto.builder()
                .userId(firstIdLong)
                .weightUser(weightUserUpdateFirst)
                .growthUser(growthUserUpdateFirst)
                .updatedOn(timeUpdateFirst)
                .build();
        diaryUpdateDtoSecond = DiaryUpdateDto.builder()
                .userId(firstIdLong)
                .updatedOn(timeUpdateSecond)
                .build();
        diaryUpdateDtoThird = DiaryUpdateDto.builder()
                .userId(secondIdLong)
                .weightUser(weightUserUpdateSecond)
                .updatedOn(timeUpdateThird)
                .build();
        diaryUpdateDtoFourth = DiaryUpdateDto.builder()
                .userId(secondIdLong)
                .growthUser(growthUserUpdateSecond)
                .build();

        diaryCreateDtoFirst = DiaryCreateDto.builder()
                .userId(firstIdLong)
                .weightUser(weightUserFirst)
                .growthUser(growthUserFirst)
                .build();
        diaryCreateDtoSecond = DiaryCreateDto.builder()
                .userId(secondIdLong)
                .weightUser(weightUserSecond)
                .growthUser(growthUserSecond)
                .build();
    }

    protected WorkoutFullResponseDto workoutFullResponseDtoFirst;
    protected WorkoutFullResponseDto workoutFullResponseDtoSecond;
    protected WorkoutFullResponseDto workoutFullResponseDtoThird;

    protected WorkoutCreateDto workoutCreateDtoFirst;
    protected WorkoutCreateDto workoutCreateDtoSecond;
    protected WorkoutCreateDto workoutCreateDtoThird;

    protected void initWorkoutDto() {
        initWorkoutEntity();
        initLocalDateTime();
        initTypeWorkoutDto();
        initTypeWorkoutEntity();


        workoutFullResponseDtoFirst = WorkoutFullResponseDto.builder()
                .id(workoutEntityFirst.getId())
                .idDiary(workoutEntityFirst.getIdDiary())
                .typeWorkoutResponseDto(typeWorkoutResponseDtoWalking)
                .timeStartOn(workoutEntityFirst.getTimeStartOn())
                .timeEndOn(workoutEntityFirst.getTimeEndOn())
                .timeOfRest(workoutEntityFirst.getTimeOfRest())
                .currentWeightUser(workoutEntityFirst.getCurrentWeightUser())
                .personalNote(workoutEntityFirst.getPersonalNote())
                .detailOfWorkout(workoutEntityFirst.getDetailOfWorkout())
                .build();
        workoutFullResponseDtoSecond = WorkoutFullResponseDto.builder()
                .id(workoutEntitySecond.getId())
                .idDiary(workoutEntitySecond.getIdDiary())
                .typeWorkoutResponseDto(typeWorkoutResponseDtoStrength)
                .timeStartOn(workoutEntitySecond.getTimeStartOn())
                .timeEndOn(workoutEntitySecond.getTimeEndOn())
                .timeOfRest(workoutEntitySecond.getTimeOfRest())
                .currentWeightUser(workoutEntitySecond.getCurrentWeightUser())
                .personalNote(workoutEntitySecond.getPersonalNote())
                .detailOfWorkout(workoutEntitySecond.getDetailOfWorkout())
                .build();
        workoutFullResponseDtoThird = WorkoutFullResponseDto.builder()
                .id(workoutEntityThird.getId())
                .idDiary(workoutEntityThird.getIdDiary())
                .typeWorkoutResponseDto(typeWorkoutResponseDtoYoga)
                .timeStartOn(workoutEntityThird.getTimeStartOn())
                .timeEndOn(workoutEntityThird.getTimeEndOn())
                .timeOfRest(workoutEntityThird.getTimeOfRest())
                .currentWeightUser(workoutEntityThird.getCurrentWeightUser())
                .personalNote(workoutEntityThird.getPersonalNote())
                .detailOfWorkout(workoutEntityThird.getDetailOfWorkout())
                .build();

        workoutCreateDtoFirst = WorkoutCreateDto.builder()
                .idDiary(workoutEntityFirst.getId())
                .idTypeWorkout(workoutEntityFirst.getIdTypeWorkout())
                .timeStartOn(workoutEntityFirst.getTimeStartOn())
                .timeEndOn(workoutEntityFirst.getTimeEndOn())
                .timeOfRest(workoutEntityFirst.getTimeOfRest())
                .currentWeightUser(workoutEntityFirst.getCurrentWeightUser())
                .personalNote(workoutEntityFirst.getPersonalNote())
                .detailOfWorkout(workoutEntityFirst.getDetailOfWorkout())
                .build();
        workoutCreateDtoSecond = WorkoutCreateDto.builder()
                .idDiary(workoutEntitySecond.getId())
                .idTypeWorkout(workoutEntitySecond.getIdTypeWorkout())
                .timeStartOn(workoutEntitySecond.getTimeStartOn())
                .timeEndOn(workoutEntitySecond.getTimeEndOn())
                .timeOfRest(workoutEntitySecond.getTimeOfRest())
                .currentWeightUser(workoutEntitySecond.getCurrentWeightUser())
                .personalNote(workoutEntitySecond.getPersonalNote())
                .detailOfWorkout(workoutEntitySecond.getDetailOfWorkout())
                .build();
        workoutCreateDtoThird = WorkoutCreateDto.builder()
                .idDiary(workoutEntityThird.getId())
                .idTypeWorkout(workoutEntityThird.getIdTypeWorkout())
                .timeStartOn(workoutEntityThird.getTimeStartOn())
                .timeEndOn(workoutEntityThird.getTimeEndOn())
                .timeOfRest(workoutEntityThird.getTimeOfRest())
                .currentWeightUser(workoutEntityThird.getCurrentWeightUser())
                .personalNote(workoutEntityThird.getPersonalNote())
                .detailOfWorkout(workoutEntityThird.getDetailOfWorkout())
                .build();
    }

    protected WorkoutEntity workoutEntityFirst;
    protected WorkoutEntity workoutEntitySecond;
    protected WorkoutEntity workoutEntityThird;

    protected void initWorkoutEntity() {
        initTypeWorkoutEntity();
        initLocalDateTime();
        workoutEntityFirst = WorkoutEntity.builder()
                .id(firstIdLong)
                .idDiary(firstIdLong)
                .idTypeWorkout(typeWorkoutEntityWalking.getId())
                .timeStartOn(timeStartFirst)
                .timeEndOn(timeEndFirst)
                .timeOfRest(periodOne)
                .currentWeightUser(weightUserFirst)
                .personalNote("One Workout")
                .detailOfWorkout("My One Workout")
                .build();
        workoutEntitySecond = WorkoutEntity.builder()
                .id(secondIdLong)
                .idDiary(secondIdLong)
                .idTypeWorkout(typeWorkoutEntityStrength.getId())
                .timeStartOn(timeStartSecond)
                .timeEndOn(timeEndSecond)
                .timeOfRest(periodSecond)
                .currentWeightUser(weightUserSecond)
                .personalNote("Second Workout")
                .detailOfWorkout("My Second Workout")
                .build();
        workoutEntityThird = WorkoutEntity.builder()
                .id(thirdIdLong)
                .idDiary(thirdIdLong)
                .idTypeWorkout(typeWorkoutEntityYoga.getId())
                .timeStartOn(timeStartThird)
                .timeEndOn(timeEndThird)
                .timeOfRest(periodThird)
                .currentWeightUser(weightUserThird)
                .personalNote("Third Workout")
                .detailOfWorkout("Без деталей")
                .build();
    }
}
