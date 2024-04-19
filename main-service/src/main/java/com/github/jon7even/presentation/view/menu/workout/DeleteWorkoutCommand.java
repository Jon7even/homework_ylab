package com.github.jon7even.presentation.view.menu.workout;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.application.services.ServiceCalculationOfStats;
import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.application.services.impl.DiaryServiceImpl;
import com.github.jon7even.application.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.application.services.impl.WorkoutServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.List;
import java.util.Scanner;

import static com.github.jon7even.presentation.utils.DateTimeFormat.DATA_TIME_FORMAT;
import static com.github.jon7even.presentation.view.ru.LocalException.NOT_FOUND_ID_EXCEPTION;
import static com.github.jon7even.presentation.view.ru.LocalException.WORKOUT_IS_EMPTY_EXCEPTION;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню удаления существующей тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class DeleteWorkoutCommand extends ServiceCommand {
    private final DiaryService diaryService;
    private final WorkoutService workoutService;
    private final ServiceCalculationOfStats serviceCalculationOfStats;

    public DeleteWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        workoutService = WorkoutServiceImpl.getInstance();
        diaryService = DiaryServiceImpl.getInstance();
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
    }

    @Override
    public void handle() {
        Long userId = getUserInMemory().getId();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр меню удаления тренировки")
                .build());
        Long diaryId = diaryService.getIdDiaryByUserId(userId);
        Scanner scanner = getScanner();
        List<WorkoutShortResponseDto> listExistsWorkoutsByUser =
                workoutService.findAllWorkoutByOwnerDiaryBySortByDeskDate(diaryId, userId);
        System.out.println(WORKOUT_DELETE_MENU);
        System.out.printf(WORKOUT_VIEWING_LIST_HEADER, "своих");

        if (!listExistsWorkoutsByUser.isEmpty()) {
            for (int i = 0; i < listExistsWorkoutsByUser.size(); i++) {
                int iterator = i + 1;
                System.out.printf(
                        WORKOUT_VIEWING_LIST_BODY,
                        iterator,
                        listExistsWorkoutsByUser.get(i).getTimeStartOn().format(DATA_TIME_FORMAT),
                        listExistsWorkoutsByUser.get(i).getTimeEndOn().format(DATA_TIME_FORMAT),
                        listExistsWorkoutsByUser.get(i).getTimeOfRest().toMinutes(),
                        listExistsWorkoutsByUser.get(i).getCurrentWeightUser(),
                        listExistsWorkoutsByUser.get(i).getPersonalNote()
                );
            }

            System.out.println(WORKOUT_GO_ID);
            int localIdWorkout = scanner.nextInt() - 1;
            scanner.nextLine();
            long workoutId = listExistsWorkoutsByUser.get(localIdWorkout).getId();

            if (workoutService.isExistWorkoutByWorkoutId(workoutId)) {
                WorkoutFullResponseDto workoutForDelete = workoutService.getWorkoutById(workoutId);

                int minutesOfWorkoutUpdate = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                        workoutForDelete
                );
                int totalCalorieUpdate = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutForDelete);
                System.out.printf(WORKOUT_FULL_VIEWING_FORM,
                        workoutForDelete.getTimeStartOn().format(DATA_TIME_FORMAT),
                        workoutForDelete.getTypeWorkoutResponseDto().getTypeName(),
                        minutesOfWorkoutUpdate,
                        workoutForDelete.getTimeOfRest().toMinutes(),
                        workoutForDelete.getCurrentWeightUser(),
                        totalCalorieUpdate,
                        workoutForDelete.getTypeWorkoutResponseDto()
                                .getDetailOfTypeWorkoutResponseDto()
                                .getName(),
                        workoutForDelete.getDetailOfWorkout(),
                        workoutForDelete.getPersonalNote()
                );
                System.out.println(WORKOUT_DELETE_PREPARE);
                workoutService.deleteWorkoutByWorkoutIdAndOwnerId(workoutId, userId);
                getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Успешное удаление тренировки workoutId=" + workoutId)
                        .build());
                System.out.println(WORKOUT_DELETE_COMPLETE);
            } else {
                getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Попытка удалить несуществующую тренировку workoutId=" + workoutId)
                        .build());
                System.out.println(NOT_FOUND_ID_EXCEPTION);
                System.out.println();
            }
        } else {
            System.out.println(WORKOUT_IS_EMPTY_EXCEPTION);
        }

        System.out.println(WORKOUT_MAIN_MENU_WHAT_NEXT);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new DeleteWorkoutCommand(getUserInMemory()));
        }
    }
}