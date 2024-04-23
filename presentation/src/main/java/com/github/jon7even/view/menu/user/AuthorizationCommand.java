package com.github.jon7even.view.menu.user;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserLoginAuthDto;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.services.AuthorizationService;
import com.github.jon7even.services.UserService;
import com.github.jon7even.services.impl.AuthorizationServiceImpl;
import com.github.jon7even.services.impl.UserServiceImpl;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.main.StartCommand;
import com.github.jon7even.view.ru.LocalException;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.Scanner;

/**
 * Меню с авторизацией пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class AuthorizationCommand extends ServiceCommand {
    private final AuthorizationService authorizationService;
    private final UserService userService;

    public AuthorizationCommand() {
        authorizationService = AuthorizationServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        UserLoginAuthDto userLoginAuthDto;

        System.out.println(LocalMessages.AUTH_LOGIN_MESSAGE);
        userLoginAuthDto = UserLoginAuthDto.builder()
                .login(scanner.nextLine())
                .build();

        System.out.println(LocalMessages.AUTH_PASSWORD_MESSAGE);
        userLoginAuthDto.setPassword(scanner.nextLine());

        try {
            if (authorizationService.processAuthorization(userLoginAuthDto)) {
                System.out.println(LocalMessages.AUTH_SUCCESS);
                setUserInMemory(userService.findUserForAuthorization(userLoginAuthDto));

                getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(getUserInMemory().getId())
                        .event("Успешная авторизация пользователя")
                        .build());

                setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            } else {
                System.out.println(LocalException.UNKNOWN_ERROR_EXCEPTION);
                setCommandNextMenu(new StartCommand());
            }
        } catch (NotFoundException e) {
            System.out.println(LocalException.AUTH_LOGIN_DENIED_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        } catch (AccessDeniedException e) {
            System.out.println(LocalException.AUTH_PASSWORD_DENIED_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        }
    }
}