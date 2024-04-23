package com.github.jon7even.view.menu.user;

import com.github.jon7even.core.domain.v1.dto.user.UserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.services.UserService;
import com.github.jon7even.services.impl.UserServiceImpl;
import com.github.jon7even.view.config.Settings;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.main.StartCommand;
import com.github.jon7even.view.ru.LocalException;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.Scanner;

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

        System.out.println(LocalMessages.REGISTER_NEW_USER_MESSAGE);
        System.out.println(LocalMessages.REGISTER_NEW_LOGIN_MESSAGE);
        userCreateDto = UserCreateDto.builder()
                .login(scanner.nextLine())
                .build();

        System.out.println(LocalMessages.REGISTER_NEW_PASSWORD_MESSAGE);
        userCreateDto.setPassword(scanner.nextLine());
        userCreateDto.setIdGroupPermissions(Settings.DEFAULT_ID_FOR_GROUP);

        try {
            UserShortResponseDto createdUser = userService.createUser(userCreateDto);
            System.out.println(LocalMessages.REGISTER_NEW_USER_SUCCESS + createdUser.getLogin());
            System.out.println(LocalMessages.REGISTER_NEXT_ITERATOR);
            setCommandNextMenu(new AuthorizationCommand());
        } catch (BadLoginException e) {
            System.out.println(LocalException.REGISTER_BAD_LOGIN_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        } catch (NotCreatedException e) {
            System.out.println(LocalException.UNKNOWN_ERROR_EXCEPTION);
            setCommandNextMenu(new StartCommand());
        }
    }
}