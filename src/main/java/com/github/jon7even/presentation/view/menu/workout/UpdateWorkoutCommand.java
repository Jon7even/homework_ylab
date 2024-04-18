package com.github.jon7even.presentation.view.menu.workout;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.application.services.ServiceCalculationOfStats;
import com.github.jon7even.application.services.TypeWorkoutService;
import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.application.services.impl.DiaryServiceImpl;
import com.github.jon7even.application.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.application.services.impl.TypeWorkoutServiceImpl;
import com.github.jon7even.application.services.impl.WorkoutServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.WORKOUT_MAIN_MENU_WHAT_NEXT;

/**
 * Меню редактирования существующей тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class UpdateWorkoutCommand extends ServiceCommand {
    private final DiaryService diaryService;
    private final TypeWorkoutService typeWorkoutService;
    private final WorkoutService workoutService;
    private final ServiceCalculationOfStats serviceCalculationOfStats;

    public UpdateWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        typeWorkoutService = TypeWorkoutServiceImpl.getInstance();
        workoutService = WorkoutServiceImpl.getInstance();
        diaryService = DiaryServiceImpl.getInstance();
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
    }

    @Override
    public void handle() {
        Long userId = getUserInMemory().getId();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр меню редактирования тренировки")
                .build());

        Scanner scanner = getScanner();
        System.out.println(WORKOUT_MAIN_MENU_WHAT_NEXT);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new UpdateWorkoutCommand(getUserInMemory()));
        }
    }
}
