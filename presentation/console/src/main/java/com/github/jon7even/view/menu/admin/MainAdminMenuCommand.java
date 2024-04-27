package com.github.jon7even.view.menu.admin;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.Scanner;

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
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню администратора")
                .build());

        System.out.println(LocalMessages.MAIN_MENU_ADMINISTRATOR);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new ViewHistoryAnyUserCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new ViewWorkoutsAnyUserCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new AddTypeWorkoutAdminCommand(getUserInMemory()));
            case 4 -> setCommandNextMenu(new UpdateTypeWorkoutAdminCommand(getUserInMemory()));
            case 5 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 6 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
        }
    }
}
