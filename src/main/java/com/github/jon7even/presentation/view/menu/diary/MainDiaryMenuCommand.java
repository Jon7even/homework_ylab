package com.github.jon7even.presentation.view.menu.diary;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_MENU_DIARY;

/**
 * Главное меню взаимодействия с дневником
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainDiaryMenuCommand extends ServiceCommand {
    public MainDiaryMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню дневника")
                .build());

        System.out.println(MAIN_MENU_DIARY);

        switch (scanner.nextInt()) {
            //TODO
            // TODO
            case 3 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 4 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new MainDiaryMenuCommand(getUserInMemory()));
        }
    }
}