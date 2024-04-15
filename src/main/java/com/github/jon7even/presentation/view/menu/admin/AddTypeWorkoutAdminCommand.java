package com.github.jon7even.presentation.view.menu.admin;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.ADD_NEW_TYPE_WORKOUT_MENU;
import static com.github.jon7even.presentation.view.ru.LocalMessages.MENU_ADMINISTRATOR_COMPLETE_ADD_WORKOUT;

/**
 * Меню добавления нового типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class AddTypeWorkoutAdminCommand extends ServiceCommand {
    public AddTypeWorkoutAdminCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр меню добавления нового типа тренировки")
                .build());

        System.out.println(ADD_NEW_TYPE_WORKOUT_MENU);

        // TODO

        System.out.println(MENU_ADMINISTRATOR_COMPLETE_ADD_WORKOUT);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new AddTypeWorkoutAdminCommand(getUserInMemory()));
        }
    }
}