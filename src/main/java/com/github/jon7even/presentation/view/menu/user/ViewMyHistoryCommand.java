package com.github.jon7even.presentation.view.menu.user;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.VIEWING_MY_HISTORY;

public class ViewMyHistoryCommand extends ServiceCommand {
    public ViewMyHistoryCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр своей истории действий")
                .build());

        System.out.println(VIEWING_MY_HISTORY);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuUserCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new ViewMyHistoryCommand(getUserInMemory()));
        }
    }
}