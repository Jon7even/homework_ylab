package com.github.jon7even.services.aspect;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInAuthDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLogInResponseDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.enums.HistoryUserMessages;
import com.github.jon7even.services.HistoryUserService;
import com.github.jon7even.services.UserService;
import com.github.jon7even.services.config.BeanConfig;
import com.github.jon7even.services.impl.HistoryUserServiceImpl;
import com.github.jon7even.services.impl.UserServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

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

    public LoggableSaveHistoryAspect() {
        BeanConfig beanConfig = BeanConfig.getInstance();
        this.historyUserService = new HistoryUserServiceImpl(
                beanConfig.getUserDao(), beanConfig.getHistoryUserDao(), beanConfig.getGroupPermissionsDao()
        );
        this.userService = new UserServiceImpl(beanConfig.getUserDao());
    }

    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.AuthorizationServiceImpl.processAuthorization(..))")
    public void processAuthorization() {
    }

    @Before("processAuthorization()")
    public void processAuthorizationBefore(JoinPoint joinPoint) throws Throwable {
        UserLogInAuthDto userLoginAuthDto = (UserLogInAuthDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserId(userLoginAuthDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userId,
                    HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.IN_PROGRESS.getMessage()
            ));
        }
    }

    @AfterReturning(value = "processAuthorization()", returning = "result")
    public void processAuthorizationAfter(JoinPoint joinPoint, Object result) throws Throwable {
        UserLogInAuthDto userLoginAuthDto = (UserLogInAuthDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Boolean isAuth = (Boolean) result;
        Long userId = getUserId(userLoginAuthDto.getLogin());

        if (userId > 0) {
            if (isAuth) {
                historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userId,
                        HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.SUCCESS.getMessage()
                ));
            } else {
                historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userId,
                        HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.FAILURE.getMessage()
                ));
            }
        }
    }

    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.UserServiceImpl.findUserForAuthorization(..))")
    public void findUserForAuthorization() {
    }

    @Before("findUserForAuthorization()")
    public void findUserForAuthorizationAfter(JoinPoint joinPoint) throws Throwable {
        UserLogInAuthDto userLoginAuthDto = (UserLogInAuthDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserId(userLoginAuthDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userId,
                    HistoryUserMessages.SIGN_IN.getMessage() + HistoryUserMessages.IN_PROGRESS.getMessage()
            ));
        }
    }

    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.UserServiceImpl.createUser(..))")
    public void createUser() {
    }

    @Before("createUser()")
    public void createUserBefore(JoinPoint joinPoint) throws Throwable {
        UserCreateDto userCreateDto = (UserCreateDto) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        Long userId = getUserId(userCreateDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userId,
                    HistoryUserMessages.SIGN_UP.getMessage() + HistoryUserMessages.WARN.getMessage()
                            + " кто-то пытается при регистрации указать этот логин!"
            ));
        }
    }

    @AfterReturning(value = "createUser()", returning = "result")
    public void createUserAfter(Object result) throws Throwable {
        UserShortResponseDto userShortResponseDto = (UserShortResponseDto) result;
        Long userId = getUserId(userShortResponseDto.getLogin());

        if (userId > 0) {
            historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userId,
                    HistoryUserMessages.SIGN_UP.getMessage() + HistoryUserMessages.SUCCESS.getMessage()
            ));
        }
    }

    @Pointcut("within(@com.github.jon7even.annotations.Loggable *) "
            + "&& execution(* com.github.jon7even.services.impl.AuthorizationServiceImpl.processLogOut(..))")
    public void processLogOut() {
    }

    @AfterReturning("processLogOut()")
    public void processLogOutAfter(JoinPoint joinPoint) throws Throwable {
        UserLogInResponseDto userLogInResponseDto = (UserLogInResponseDto) Arrays.stream(joinPoint.getArgs())
                .findFirst().get();

        historyUserService.createHistoryOfUser(getHistoryUserCreateDto(userLogInResponseDto.getId(),
                HistoryUserMessages.SIGN_OUT.getMessage() + HistoryUserMessages.SUCCESS.getMessage())
        );
    }

    private Long getUserId(String login) {
        Long userId;
        try {
            userId = userService.findByUserIdByLogin(login);
        } catch (Exception e) {
            userId = -1L;
        }
        return userId;
    }

    private HistoryUserCreateDto getHistoryUserCreateDto(Long userId, String event) {
        return HistoryUserCreateDto.builder()
                .userId(userId)
                .event(event)
                .build();
    }
}
