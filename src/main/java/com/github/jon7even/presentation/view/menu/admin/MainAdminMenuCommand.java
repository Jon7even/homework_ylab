package com.github.jon7even.presentation.view.menu.admin;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_MENU_ADMINISTRATOR;

/**
 * Главное меню администратора приложения
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainAdminMenuCommand extends ServiceCommand {
    public MainAdminMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню администратора")
                .build());

        System.out.println(MAIN_MENU_ADMINISTRATOR);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new ViewHistoryAnyUserCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new AddTypeWorkoutAdminCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 4 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
        }
    }
}
