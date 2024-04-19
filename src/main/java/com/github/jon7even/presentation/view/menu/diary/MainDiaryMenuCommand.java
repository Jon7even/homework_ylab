package com.github.jon7even.presentation.view.menu.diary;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.application.services.impl.DiaryServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_MENU_DIARY;
import static com.github.jon7even.presentation.view.ru.LocalMessages.MENU_DIARY_NOT_FOUND;

/**
 * Главное меню взаимодействия с дневником
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainDiaryMenuCommand extends ServiceCommand {
    private final DiaryService diaryService;

    public MainDiaryMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        diaryService = DiaryServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню дневника")
                .build());

        if (!diaryService.isExistByUserId(getUserInMemory().getId())) {
            System.out.println(MENU_DIARY_NOT_FOUND);
            setCommandNextMenu(new CreateMyDiaryCommand(getUserInMemory()));
        } else {
            Scanner scanner = getScanner();
            System.out.println(MAIN_MENU_DIARY);

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