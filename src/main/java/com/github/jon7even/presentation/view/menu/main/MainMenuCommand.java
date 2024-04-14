package com.github.jon7even.presentation.view.menu.main;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.diary.MainMenuDiaryCommand;
import com.github.jon7even.presentation.view.menu.user.MainMenuUserCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;
import com.github.jon7even.presentation.view.menu.workout.MainMenuWorkoutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_MENU;

public class MainMenuCommand extends ServiceCommand {
    public MainMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню приложения")
                .build());

        System.out.println(MAIN_MENU);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuDiaryCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new MainMenuUserCommand(getUserInMemory()));
            case 4 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
        }
    }
}