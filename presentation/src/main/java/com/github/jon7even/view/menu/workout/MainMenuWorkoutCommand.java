package com.github.jon7even.view.menu.workout;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.services.impl.DiaryServiceImpl;
import com.github.jon7even.view.menu.diary.CreateMyDiaryCommand;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.Scanner;

/**
 * Главное меню взаимодействия с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainMenuWorkoutCommand extends ServiceCommand {
    private final DiaryService diaryService;

    public MainMenuWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        diaryService = DiaryServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр главного меню тренировок")
                .build());

        if (!diaryService.isExistByUserId(getUserInMemory().getId())) {
            System.out.println(LocalMessages.MENU_DIARY_NOT_FOUND);
            setCommandNextMenu(new CreateMyDiaryCommand(getUserInMemory()));
        } else {
            Scanner scanner = getScanner();
            System.out.println(LocalMessages.MAIN_MENU_WORKOUT);

            switch (scanner.nextInt()) {
                case 1 -> setCommandNextMenu(new CreateWorkoutCommand(getUserInMemory()));
                case 2 -> setCommandNextMenu(new UpdateWorkoutCommand(getUserInMemory()));
                case 3 -> setCommandNextMenu(new DeleteWorkoutCommand(getUserInMemory()));
                case 4 -> setCommandNextMenu(new FindWorkoutCommand(getUserInMemory()));
                case 5 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
                case 6 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
                case 0 -> setCommandNextMenu(new ExitFromAppCommand());
                default -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            }
        }
    }
}
