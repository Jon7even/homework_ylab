package com.github.jon7even.view.menu.main;

import com.github.jon7even.view.menu.user.AuthorizationCommand;
import com.github.jon7even.view.menu.user.RegisterNewUserCommand;

import java.util.Scanner;

import static com.github.jon7even.view.ru.LocalMessages.GREET_MESSAGE;
import static com.github.jon7even.view.ru.LocalMessages.START_MENU;

/**
 * Начальное меню с аутентификацией
 *
 * @author Jon7even
 * @version 1.0
 */
public class StartCommand extends ServiceCommand {
    @Override
    public void handle() {
        Scanner scanner = getScanner();
        System.out.println(GREET_MESSAGE);
        System.out.println(START_MENU);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new AuthorizationCommand());
            case 2 -> setCommandNextMenu(new RegisterNewUserCommand());
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new StartCommand());
        }
    }
}
