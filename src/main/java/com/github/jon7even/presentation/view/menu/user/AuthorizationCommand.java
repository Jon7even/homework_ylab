package com.github.jon7even.presentation.view.menu.user;

import com.github.jon7even.application.dto.user.UserLoginAuthDto;
import com.github.jon7even.application.services.AuthorizationService;
import com.github.jon7even.application.services.UserService;
import com.github.jon7even.application.services.impl.AuthorizationServiceImpl;
import com.github.jon7even.application.services.impl.UserServiceImpl;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.main.StartCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

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

        System.out.println(AUTH_LOGIN_MESSAGE);
        userLoginAuthDto = UserLoginAuthDto.builder()
                .login(scanner.nextLine())
                .build();

        System.out.println(AUTH_PASSWORD_MESSAGE);
        userLoginAuthDto.setPassword(scanner.nextLine());

        try {
            if (authorizationService.processAuthorization(userLoginAuthDto)) {
                System.out.println(AUTH_SUCCESS);
                setUserInMemory(userService.findUserForAuthorization(userLoginAuthDto));
                setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            } else {
                System.out.println(UNKNOWN_ERROR_EXCEPTION);
                setCommandNextMenu(new StartCommand());
            }
        } catch (NotFoundException e) {
            System.out.println(AUTH_LOGIN_DENIED_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        } catch (AccessDeniedException e) {
            System.out.println(AUTH_PASSWORD_DENIED_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        }
    }
}