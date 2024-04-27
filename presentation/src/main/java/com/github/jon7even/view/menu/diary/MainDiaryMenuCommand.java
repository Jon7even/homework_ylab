package com.github.jon7even.view.menu.diary;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.Scanner;

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
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню дневника")
                .build());

        if (!getDiaryService().isExistByUserId(getUserInMemory().getId())) {
            System.out.println(LocalMessages.MENU_DIARY_NOT_FOUND);
            setCommandNextMenu(new CreateMyDiaryCommand(getUserInMemory()));
        } else {
            Scanner scanner = getScanner();
            System.out.println(LocalMessages.MAIN_MENU_DIARY);

            switch (scanner.nextInt()) {
                case 1 -> setCommandNextMenu(new ViewMyDiaryCommand(getUserInMemory()));
                case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
                case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
                case 0 -> setCommandNextMenu(new ExitFromAppCommand());
                default -> setCommandNextMenu(new MainDiaryMenuCommand(getUserInMemory()));
            }
        }
    }
}