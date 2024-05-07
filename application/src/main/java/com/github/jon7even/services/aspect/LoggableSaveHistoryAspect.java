package com.github.jon7even.services.aspect;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.enums.HistoryUserMessages;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.services.HistoryUserService;
import com.github.jon7even.services.UserService;
import com.github.jon7even.services.config.BeanConfig;
import com.github.jon7even.services.impl.DiaryServiceImpl;
import com.github.jon7even.services.impl.HistoryUserServiceImpl;
import com.github.jon7even.services.impl.UserServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.List;

/**
 * Обработка сохранения истории действий пользователей для методов, которые помечены аннотацией @Loggable
 *
 * @author Jon7even
 * @version 1.0
 */
@Aspect
public class LoggableSaveHistoryAspect {
    private final HistoryUserService historyUserService;
    private final UserService userService;
    private final DiaryService diaryService;

    public LoggableSaveHistoryAspect() {
        BeanConfig beanConfig = BeanConfig.getInstance();
        this.historyUserService = new HistoryUserServiceImpl(
                beanConfig.getUserDao(), beanConfig.getHistoryUserDao(), beanConfig.getGroupPermissionsDao()
        );
        this.userService = new UserServiceImpl(beanConfig.getUserDao());
        this.diaryService = new DiaryServiceImpl(beanConfig.getDiaryDao());
    }

    /**
     * Срез метода авторизации
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.AuthorizationServiceImpl.processAuthorization(..))")
    public void processAuthorization() {
    }

    /**
     * Реализация метода авторизации ДО возвращения результата от сервиса
     */
    @Before("processAuthorization()")
    public void processAuthorizationBefore(JoinPoint joinPoint) throws Throwable {
        UserLogInAuthDto userLoginAuthDto = (UserLogInAuthDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserIdByLogin(userLoginAuthDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.IN_PROGRESS.getMessage()
            ));
        }
    }

    /**
     * Реализация метода авторизации ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "processAuthorization()", returning = "result")
    public void processAuthorizationAfter(JoinPoint joinPoint, Object result) throws Throwable {
        UserLogInAuthDto userLoginAuthDto = (UserLogInAuthDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Boolean isAuth = (Boolean) result;
        Long userId = getUserIdByLogin(userLoginAuthDto.getLogin());

        if (userId > 0) {
            if (isAuth) {
                historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                        HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.SUCCESS.getMessage()
                ));
            } else {
                historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                        HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.FAILURE.getMessage()
                ));
            }
        }
    }

    /**
     * Срез метода поиска пользователя для авторизации
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.UserServiceImpl.findUserForAuthorization(..))")
    public void findUserForAuthorization() {
    }

    /**
     * Реализация метода поиска пользователя ДО возвращения результата от сервиса
     */
    @Before("findUserForAuthorization()")
    public void findUserForAuthorizationBefore(JoinPoint joinPoint) throws Throwable {
        UserLogInAuthDto userLoginAuthDto = (UserLogInAuthDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserIdByLogin(userLoginAuthDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.IN_PROGRESS.getMessage()
            ));
        }
    }

    /**
     * Срез метода регистрации нового пользователя
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.UserServiceImpl.createUser(..))")
    public void createUser() {
    }

    /**
     * Реализация метода регистрации нового пользователя ДО возвращения результата от сервиса
     */
    @Before("createUser()")
    public void createUserBefore(JoinPoint joinPoint) throws Throwable {
        UserCreateDto userCreateDto = (UserCreateDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserIdByLogin(userCreateDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    HistoryUserMessages.SIGN_UP.getMessage() + HistoryUserMessages.WARN.getMessage()
                            + " кто-то пытается при регистрации указать этот логин!"
            ));
        }
    }

    /**
     * Реализация метода регистрации нового пользователя ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "createUser()", returning = "result")
    public void createUserAfter(Object result) throws Throwable {
        UserShortResponseDto userShortResponseDto = (UserShortResponseDto) result;
        Long userId = getUserIdByLogin(userShortResponseDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    HistoryUserMessages.SIGN_UP.getMessage() + HistoryUserMessages.SUCCESS.getMessage()
            ));
        }
    }

    /**
     * Срез метода выхода из приложения
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.AuthorizationServiceImpl.processLogOut(..))")
    public void processLogOut() {
    }

    /**
     * Реализация метода выхода из приложения пользователя ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning("processLogOut()")
    public void processLogOutAfter(JoinPoint joinPoint) throws Throwable {
        UserLogInResponseDto userLogInResponseDto = (UserLogInResponseDto) Arrays.stream(joinPoint.getArgs())
                .findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userLogInResponseDto.getId(),
                HistoryUserMessages.SIGN_OUT.getMessage() + HistoryUserMessages.SUCCESS.getMessage())
        );
    }

    /**
     * Срез метода создания нового дневника
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.DiaryServiceImpl.createDiary(..))")
    public void createDiary() {
    }

    /**
     * Реализация метода создания нового дневника ДО возвращения результата от сервиса
     */
    @Before(value = "createDiary()")
    public void createDiaryBefore(JoinPoint joinPoint) throws Throwable {
        DiaryCreateDto diaryCreateDto = (DiaryCreateDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(diaryCreateDto.getUserId(),
                HistoryUserMessages.DIARY_CREATE_SHORT.getMessage() + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация метода создания нового дневника ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "createDiary()", returning = "result")
    public void createDiaryAfter(JoinPoint joinPoint, Object result) throws Throwable {
        DiaryCreateDto diaryCreateDto = (DiaryCreateDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        DiaryResponseDto responseDto = (DiaryResponseDto) result;

        if (responseDto != null) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(diaryCreateDto.getUserId(),
                    String.format(
                            HistoryUserMessages.DIARY_CREATE_FULL.getMessage(),
                            responseDto.getCreatedOn(),
                            responseDto.getWeightUser(),
                            responseDto.getGrowthUser())
                            + HistoryUserMessages.SUCCESS.getMessage()
            ));
        }
    }

    /**
     * Срез метода обновления дневника
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.DiaryServiceImpl.updateDiary(..))")
    public void updateDiary() {
    }

    /**
     * Реализация метода обновления существующего дневника ДО возвращения результата от сервиса
     */
    @Before(value = "updateDiary()")
    public void updateDiaryBefore(JoinPoint joinPoint) throws Throwable {
        DiaryUpdateDto diaryUpdateDto = (DiaryUpdateDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(diaryUpdateDto.getUserId(),
                HistoryUserMessages.DIARY_UPDATE_SHORT.getMessage() + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация метода обновления существующего дневника ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "updateDiary()", returning = "result")
    public void updateDiaryAfter(JoinPoint joinPoint, Object result) throws Throwable {
        DiaryUpdateDto diaryUpdateDto = (DiaryUpdateDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        DiaryResponseDto responseDto = (DiaryResponseDto) result;

        if (responseDto != null) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(diaryUpdateDto.getUserId(),
                    String.format(
                            HistoryUserMessages.DIARY_UPDATE_FULL.getMessage(),
                            responseDto.getUpdatedOn(),
                            responseDto.getWeightUser(),
                            responseDto.getGrowthUser())
                            + HistoryUserMessages.SUCCESS.getMessage()
            ));
        }
    }

    /**
     * Срез метода поиска дневника по userId
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.DiaryServiceImpl.getDiaryDtoByUserId(..))")
    public void getDiaryDtoByUserId() {
    }

    /**
     * Реализация метода поиска дневника по userId ДО возвращения результата от сервиса
     */
    @Before(value = "getDiaryDtoByUserId()")
    public void getDiaryDtoByUserIdBefore(JoinPoint joinPoint) throws Throwable {
        Long userId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                String.format(HistoryUserMessages.DIARY_FIND_BY_USER_ID.getMessage(), userId)
                        + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация метода поиска дневника по userId ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "getDiaryDtoByUserId()", returning = "result")
    public void getDiaryDtoByUserIdAfter(JoinPoint joinPoint, Object result) throws Throwable {
        Long userId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        DiaryResponseDto responseDto = (DiaryResponseDto) result;

        if (responseDto != null) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    String.format(HistoryUserMessages.DIARY_FIND_BY_USER_ID.getMessage(), userId)
                            + HistoryUserMessages.SUCCESS.getMessage()
            ));
        }
    }

    /**
     * Срез метода поиска дневника по diaryId
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.DiaryServiceImpl.findDiaryByDiaryId(..))")
    public void findDiaryByDiaryId() {
    }

    /**
     * Реализация метода поиска дневника по diaryId ДО возвращения результата от сервиса
     */
    @Before(value = "findDiaryByDiaryId()")
    public void findDiaryByDiaryIdBefore(JoinPoint joinPoint) throws Throwable {
        Long diaryId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserIdByDiaryId(diaryId);

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    String.format(HistoryUserMessages.DIARY_FIND_BY_DIARY_ID.getMessage(), diaryId)
                            + HistoryUserMessages.IN_PROGRESS.getMessage()
            ));
        }
    }

    /**
     * Реализация метода поиска дневника по diaryId ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "findDiaryByDiaryId()", returning = "result")
    public void findDiaryByDiaryIdAfter(JoinPoint joinPoint, Object result) throws Throwable {
        Long diaryId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserIdByDiaryId(diaryId);
        DiaryResponseDto responseDto = (DiaryResponseDto) result;

        if (userId > 0) {
            if (responseDto != null) {
                historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                        String.format(HistoryUserMessages.DIARY_FIND_BY_DIARY_ID.getMessage(), diaryId)
                                + HistoryUserMessages.SUCCESS.getMessage()
                ));
            }
        }
    }

    /**
     * Срез метода проверки существования дневника по userId
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.DiaryServiceImpl.isExistByUserId(..))")
    public void isExistByUserId() {
    }

    /**
     * Реализация метода проверки существования дневника ДО возвращения результата от сервиса
     */
    @Before(value = "isExistByUserId()")
    public void isExistByUserIdBefore(JoinPoint joinPoint) throws Throwable {
        Long userId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                String.format(HistoryUserMessages.DIARY_EXIST_BY_USER_ID.getMessage(), userId)
                        + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация проверки существования дневника ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "isExistByUserId()", returning = "result")
    public void isExistByUserIdAfter(JoinPoint joinPoint, Object result) throws Throwable {
        Long userId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Boolean isExist = (Boolean) result;

        if (isExist) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    String.format(HistoryUserMessages.DIARY_EXIST_BY_USER_ID.getMessage(), userId)
                            + HistoryUserMessages.SUCCESS.getMessage()
            ));
        } else {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    String.format(HistoryUserMessages.DIARY_EXIST_BY_USER_ID.getMessage(), userId)
                            + HistoryUserMessages.FAILURE.getMessage()
            ));
        }
    }

    /**
     * Срез метода проверки доступа у пользователя к определенному действию
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution("
            + "* com.github.jon7even.services.impl.GroupPermissionsServiceImpl.getPermissionForService(..))")
    public void getPermissionForService() {
    }

    /**
     * Реализация метода получения доступа пользователя к определенному действию ДО возвращения результата от сервиса
     */
    @Before(value = "getPermissionForService()")
    public void getPermissionForServiceBefore(JoinPoint joinPoint) throws Throwable {
        Long userId = (Long) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                String.format(HistoryUserMessages.PERMISSION_GET_START.getMessage(), userId)
                        + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация метода получения доступа пользователя к определенному действию ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "getPermissionForService()", returning = "result")
    public void getPermissionForServiceAfter(JoinPoint joinPoint, Object result) throws Throwable {
        List<Object> args = Arrays.stream(joinPoint.getArgs()).toList();

        Long userId = (Long) args.get(0);
        Integer groupPermissionsId = (Integer) args.get(1);
        Integer nameTypeServiceId = (Integer) args.get(2);
        FlagPermissions flag = (FlagPermissions) args.get(3);
        Boolean isAllowed = (Boolean) result;

        if (isAllowed) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    String.format(HistoryUserMessages.PERMISSION_GET_END.getMessage(),
                            userId,
                            groupPermissionsId,
                            nameTypeServiceId,
                            flag)
                            + HistoryUserMessages.SUCCESS.getMessage()
            ));
        } else {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(userId,
                    String.format(HistoryUserMessages.PERMISSION_GET_END.getMessage(),
                            userId,
                            groupPermissionsId,
                            nameTypeServiceId,
                            flag)
                            + HistoryUserMessages.FAILURE.getMessage()
            ));
        }
    }

    /**
     * Срез метода создания нового типа тренировки
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution("
            + "* com.github.jon7even.services.impl.TypeWorkoutServiceImpl.createTypeWorkout(..))")
    public void createTypeWorkout() {
    }

    /**
     * Реализация метода создания нового типа тренировки ДО возвращения результата от сервиса
     */
    @Before(value = "createTypeWorkout()")
    public void createTypeWorkoutBefore(JoinPoint joinPoint) throws Throwable {
        TypeWorkoutCreateDto typeWorkoutCreateDto = (TypeWorkoutCreateDto) Arrays.stream(joinPoint.getArgs())
                .findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(typeWorkoutCreateDto.getRequesterId(),
                String.format(HistoryUserMessages.TYPE_WORKOUT_CREATE.getMessage(),
                        typeWorkoutCreateDto.getRequesterId())
                        + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация метода создания нового типа тренировки ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "createTypeWorkout()", returning = "result")
    public void createTypeWorkoutAfter(JoinPoint joinPoint, Object result) throws Throwable {
        TypeWorkoutCreateDto typeWorkoutCreateDto = (TypeWorkoutCreateDto) Arrays.stream(joinPoint.getArgs())
                .findFirst().get();
        TypeWorkoutResponseDto typeWorkoutResponseDto = (TypeWorkoutResponseDto) result;

        if (typeWorkoutResponseDto != null) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(typeWorkoutCreateDto.getRequesterId(),
                    String.format(HistoryUserMessages.TYPE_WORKOUT_CREATE.getMessage(),
                            typeWorkoutCreateDto.getRequesterId())
                            + HistoryUserMessages.SUCCESS.getMessage()
            ));
        }
    }

    /**
     * Срез метода обновления существующего типа тренировки
     */
    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution("
            + "* com.github.jon7even.services.impl.TypeWorkoutServiceImpl.updateTypeWorkout(..))")
    public void updateTypeWorkout() {
    }

    /**
     * Реализация метода обновления типа тренировки ДО возвращения результата от сервиса
     */
    @Before(value = "updateTypeWorkout()")
    public void updateTypeWorkoutBefore(JoinPoint joinPoint) throws Throwable {
        TypeWorkoutUpdateDto typeWorkoutUpdateDto = (TypeWorkoutUpdateDto) Arrays.stream(joinPoint.getArgs())
                .findFirst().get();
        historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(typeWorkoutUpdateDto.getRequesterId(),
                String.format(HistoryUserMessages.TYPE_WORKOUT_UPDATE.getMessage(),
                        typeWorkoutUpdateDto.getRequesterId(),
                        typeWorkoutUpdateDto.getTypeWorkoutId())
                        + HistoryUserMessages.IN_PROGRESS.getMessage()
        ));
    }

    /**
     * Реализация метода обновления типа тренировки ПОСЛЕ возвращения результата от сервиса
     */
    @AfterReturning(value = "updateTypeWorkout()", returning = "result")
    public void updateTypeWorkoutAfter(JoinPoint joinPoint, Object result) throws Throwable {
        TypeWorkoutUpdateDto typeWorkoutUpdateDto = (TypeWorkoutUpdateDto) Arrays.stream(joinPoint.getArgs())
                .findFirst().get();
        TypeWorkoutResponseDto typeWorkoutResponseDto = (TypeWorkoutResponseDto) result;

        if (typeWorkoutResponseDto != null) {
            historyUserService.createHistoryOfUser(getHistoryUserForCreateDto(typeWorkoutUpdateDto.getRequesterId(),
                    String.format(HistoryUserMessages.TYPE_WORKOUT_UPDATE.getMessage(),
                            typeWorkoutUpdateDto.getRequesterId(),
                            typeWorkoutUpdateDto.getTypeWorkoutId())
                            + HistoryUserMessages.IN_PROGRESS.getMessage()
            ));
        }
    }

    private Long getUserIdByLogin(String login) {
        Long userId;
        try {
            userId = userService.getUserIdByLogin(login);
        } catch (Exception e) {
            userId = -1L;
        }
        return userId;
    }

    private Long getUserIdByDiaryId(Long diaryId) {
        Long userId;
        try {
            userId = diaryService.getIdUserByDiaryId(diaryId);
        } catch (Exception e) {
            userId = -1L;
        }
        return userId;
    }

    private HistoryUserCreateDto getHistoryUserForCreateDto(Long userId, String event) {
        return HistoryUserCreateDto.builder()
                .userId(userId)
                .event(event)
                .build();
    }
}
