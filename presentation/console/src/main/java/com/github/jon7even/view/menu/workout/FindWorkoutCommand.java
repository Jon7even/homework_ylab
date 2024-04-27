package com.github.jon7even.view.menu.workout;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.services.ServiceCalculationOfStats;
import com.github.jon7even.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.utils.DateTimeFormat;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalException;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.List;
import java.util.Scanner;

/**
 * Меню поиска и просмотра тренировок
 *
 * @author Jon7even
 * @version 1.0
 */
public class FindWorkoutCommand extends ServiceCommand {
    private final ServiceCalculationOfStats serviceCalculationOfStats;

    public FindWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
    }

    @Override
    public void handle() {
        Long userId = getUserInMemory().getId();
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр меню поиска тренировок")
                .build());
        Long diaryId = getDiaryService().getIdDiaryByUserId(userId);
        Scanner scanner = getScanner();
        List<WorkoutShortResponseDto> listExistsWorkoutsByUser =
                getWorkoutService().findAllWorkoutByOwnerDiaryBySortByDeskDate(diaryId, userId);
        System.out.println(LocalMessages.WORKOUT_FIND_MENU);
        System.out.printf(LocalMessages.WORKOUT_VIEWING_LIST_HEADER, "своих");

        if (!listExistsWorkoutsByUser.isEmpty()) {
            for (int i = 0; i < listExistsWorkoutsByUser.size(); i++) {
                int iterator = i + 1;
                System.out.printf(
                        LocalMessages.WORKOUT_VIEWING_LIST_BODY,
                        iterator,
                        listExistsWorkoutsByUser.get(i).getTimeStartOn().format(DateTimeFormat.DATA_TIME_FORMAT),
                        listExistsWorkoutsByUser.get(i).getTimeEndOn().format(DateTimeFormat.DATA_TIME_FORMAT),
                        listExistsWorkoutsByUser.get(i).getTimeOfRest().toMinutes(),
                        listExistsWorkoutsByUser.get(i).getCurrentWeightUser(),
                        listExistsWorkoutsByUser.get(i).getPersonalNote()
                );
            }

            System.out.println(LocalMessages.WORKOUT_GO_ID);
            int localIdWorkout = scanner.nextInt() - 1;
            scanner.nextLine();
            long workoutId = listExistsWorkoutsByUser.get(localIdWorkout).getId();

            if (getWorkoutService().isExistWorkoutByWorkoutId(workoutId)) {
                WorkoutFullResponseDto workoutForDelete = getWorkoutService().getWorkoutById(workoutId);
                getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Просмотр тренировки workoutId=" + workoutId)
                        .build());

                int minutesOfWorkoutUpdate = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                        workoutForDelete
                );
                int totalCalorieUpdate = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutForDelete);
                System.out.printf(LocalMessages.WORKOUT_FULL_VIEWING_FORM,
                        workoutForDelete.getTimeStartOn().format(DateTimeFormat.DATA_TIME_FORMAT),
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
            } else {
                getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Попытка поиска несуществующей тренировки workoutId=" + workoutId)
                        .build());
                System.out.println(LocalException.NOT_FOUND_ID_EXCEPTION);
                System.out.println();
            }
        } else {
            System.out.println(LocalException.WORKOUT_IS_EMPTY_EXCEPTION);
        }

        System.out.println(LocalMessages.WORKOUT_MAIN_MENU_WHAT_NEXT);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new FindWorkoutCommand(getUserInMemory()));
        }
    }
}
