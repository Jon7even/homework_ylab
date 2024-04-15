package com.github.jon7even.presentation.view.menu.diary;

import com.github.jon7even.application.dto.diary.DiaryCreateDto;
import com.github.jon7even.application.dto.diary.DiaryResponseDto;
import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.application.services.impl.DiaryServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.github.jon7even.presentation.utils.DateTimeFormat.DATE_TIME_DEFAULT;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню создания нового дневника
 *
 * @author Jon7even
 * @version 1.0
 */
public class CreateMyDiaryCommand extends ServiceCommand {
    private final DiaryService diaryService;

    public CreateMyDiaryCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        diaryService = DiaryServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Создание нового дневника")
                .build());

        if (diaryService.isExistByUserId(getUserInMemory().getId())) {
            System.out.println(MENU_DIARY_NOT_FAILURE);
            setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
        } else {
            Scanner scanner = getScanner();
            DiaryCreateDto diaryCreateDto = DiaryCreateDto.builder().userId(getUserInMemory().getId()).build();
            System.out.println(MENU_DIARY_CREATE_STEP1);
            Float weightUser = Float.parseFloat(scanner.nextLine());
            diaryCreateDto.setWeightUser(weightUser);

            System.out.println(MENU_DIARY_CREATE_STEP2);
            Float growthUser = Float.parseFloat(scanner.nextLine());
            diaryCreateDto.setGrowthUser(growthUser);

            System.out.println(MENU_DIARY_CREATE_HOLD);
            DiaryResponseDto createdDiary = diaryService.createDiary(diaryCreateDto);

            System.out.println(MENU_DIARY_CREATE_DONE);
            System.out.printf(MENU_DIARY_VIEW,
                    createdDiary.getWeightUser(),
                    createdDiary.getGrowthUser(),
                    createdDiary.getCreatedOn().format(DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT)),
                    createdDiary.getUpdatedOn().format(DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT)));

            System.out.println(MENU_DIARY_VIEW_END);
            switch (scanner.nextInt()) {
                case 1 -> setCommandNextMenu(new MainDiaryMenuCommand(getUserInMemory()));
                case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
                case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
                case 0 -> setCommandNextMenu(new ExitFromAppCommand());
                default -> setCommandNextMenu(new ViewMyDiaryCommand(getUserInMemory()));
            }
        }
    }
}
