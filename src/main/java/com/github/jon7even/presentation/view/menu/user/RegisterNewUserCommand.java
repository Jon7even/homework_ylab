package com.github.jon7even.presentation.view.menu.user;

import com.github.jon7even.application.dto.user.UserCreateDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.application.services.UserService;
import com.github.jon7even.application.services.impl.UserServiceImpl;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.main.StartCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.config.Settings.DEFAULT_ID_FOR_GROUP;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню с регистрацией нового пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class RegisterNewUserCommand extends ServiceCommand {
    private final UserService userService;

    public RegisterNewUserCommand() {
        userService = UserServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        UserCreateDto userCreateDto;

        System.out.println(REGISTER_NEW_USER_MESSAGE);
        System.out.println(REGISTER_NEW_LOGIN_MESSAGE);
        userCreateDto = UserCreateDto.builder()
                .login(scanner.nextLine())
                .build();

        System.out.println(REGISTER_NEW_PASSWORD_MESSAGE);
        userCreateDto.setPassword(scanner.nextLine());
        userCreateDto.setIdGroupPermissions(DEFAULT_ID_FOR_GROUP);

        try {
            UserShortResponseDto createdUser = userService.createUser(userCreateDto);
            System.out.println(REGISTER_NEW_USER_SUCCESS + createdUser.getLogin());
            System.out.println(REGISTER_NEXT_ITERATOR);
            setCommandNextMenu(new AuthorizationCommand());
        } catch (BadLoginException e) {
            System.out.println(REGISTER_BAD_LOGIN_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        } catch (NotCreatedException e) {
            System.out.println(UNKNOWN_ERROR_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        }
    }
}