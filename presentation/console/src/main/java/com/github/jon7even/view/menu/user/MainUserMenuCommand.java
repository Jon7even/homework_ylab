package com.github.jon7even.view.menu.user;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.Scanner;

/**
 * Главное меню взаимодействия с аккаунтом пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainUserMenuCommand extends ServiceCommand {
    public MainUserMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню пользователя")
                .build());

        System.out.println(LocalMessages.MAIN_MENU_USER);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new ViewMyHistoryCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new MainUserMenuCommand(getUserInMemory()));
        }
    }
}