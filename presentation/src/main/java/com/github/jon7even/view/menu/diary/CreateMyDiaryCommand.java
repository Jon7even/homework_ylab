package com.github.jon7even.view.menu.diary;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.utils.DateTimeFormat;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.menu.workout.MainMenuWorkoutCommand;
import com.github.jon7even.view.ru.LocalMessages;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Меню создания нового дневника
 *
 * @author Jon7even
 * @version 1.0
 */
public class CreateMyDiaryCommand extends ServiceCommand {

    public CreateMyDiaryCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Попытка создания нового дневника")
                .build());

        if (getDiaryService().isExistByUserId(getUserInMemory().getId())) {
            System.out.println(LocalMessages.MENU_DIARY_ALREADY_EXIST_FAILURE);
            setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
        } else {
            Scanner scanner = getScanner();
            DiaryCreateDto diaryCreateDto = DiaryCreateDto.builder().userId(getUserInMemory().getId()).build();
            System.out.println(LocalMessages.MENU_DIARY_CREATE_STEP1);
            Float weightUser = Float.parseFloat(scanner.nextLine());
            diaryCreateDto.setWeightUser(weightUser);

            System.out.println(LocalMessages.MENU_DIARY_CREATE_STEP2);
            Float growthUser = Float.parseFloat(scanner.nextLine());
            diaryCreateDto.setGrowthUser(growthUser);

            System.out.println(LocalMessages.MENU_DIARY_CREATE_HOLD);
            DiaryResponseDto createdDiary = getDiaryService().createDiary(diaryCreateDto);

            getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(getUserInMemory().getId())
                    .event("Впервые создали свой дневник")
                    .build());

            System.out.println(LocalMessages.MENU_DIARY_CREATE_DONE);
            System.out.printf(LocalMessages.MENU_DIARY_VIEW,
                    createdDiary.getWeightUser(),
                    createdDiary.getGrowthUser(),
                    createdDiary.getCreatedOn().format(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_TIME_DEFAULT)),
                    createdDiary.getUpdatedOn().format(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_TIME_DEFAULT)));


            System.out.println(LocalMessages.MENU_DIARY_VIEW_END);
            switch (scanner.nextInt()) {
                case 1 -> setCommandNextMenu(new MainDiaryMenuCommand(getUserInMemory()));
                case 2 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
                case 3 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
                case 4 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
                case 0 -> setCommandNextMenu(new ExitFromAppCommand());
                default -> setCommandNextMenu(new ViewMyDiaryCommand(getUserInMemory()));
            }
        }
    }
}
