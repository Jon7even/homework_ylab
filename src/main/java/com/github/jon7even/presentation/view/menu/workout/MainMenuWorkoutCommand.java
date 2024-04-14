package com.github.jon7even.presentation.view.menu.workout;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_WORKOUT;

/**
 * Главное меню взаимодействия с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainMenuWorkoutCommand extends ServiceCommand {
    public MainMenuWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню тренировок")
                .build());

        System.out.println(MAIN_WORKOUT);

        switch (scanner.nextInt()) {
            // TODO
            // TODO
            // TODO
            // TODO
            // TODO
            case 6 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 7 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
        }
    }
}
