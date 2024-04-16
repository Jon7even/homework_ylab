package com.github.jon7even.presentation.view.menu.workout;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.TypeWorkoutService;
import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.application.services.impl.TypeWorkoutServiceImpl;
import com.github.jon7even.application.services.impl.WorkoutServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;

import java.util.Scanner;

import static com.github.jon7even.presentation.view.ru.LocalMessages.WORKOUT_ADD_NEW_MENU;

/**
 * Меню добавления новой тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class CreateWorkoutCommand extends ServiceCommand {
    private final TypeWorkoutService typeWorkoutService;
    private final WorkoutService workoutService;

    public CreateWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        typeWorkoutService = TypeWorkoutServiceImpl.getInstance();
        workoutService = WorkoutServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        Long userId = getUserInMemory().getId();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр меню сохранения новой тренировки")
                .build());
        System.out.println(WORKOUT_ADD_NEW_MENU);

    }
}
