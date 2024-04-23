package com.github.jon7even.view.menu.admin;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.services.DiaryService;
import com.github.jon7even.services.ServiceCalculationOfStats;
import com.github.jon7even.services.WorkoutService;
import com.github.jon7even.services.impl.DiaryServiceImpl;
import com.github.jon7even.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.services.impl.WorkoutServiceImpl;
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
 * Меню просмотра тренировок любого пользователя по его ID
 *
 * @author Jon7even
 * @version 1.0
 */
public class ViewWorkoutsAnyUserCommand extends ServiceCommand {
    private final DiaryService diaryService;
    private final WorkoutService workoutService;
    private final ServiceCalculationOfStats serviceCalculationOfStats;

    public ViewWorkoutsAnyUserCommand(UserInMemoryDto userService) {
        workoutService = WorkoutServiceImpl.getInstance();
        setUserInMemory(userService);
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
        diaryService = DiaryServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр меню поиска тренировок любого пользователя")
                .build());
        System.out.println(LocalMessages.MENU_ADMINISTRATOR_WORKOUT_START);

        Long userId = scanner.nextLong();
        System.out.println(LocalMessages.MENU_ADMINISTRATOR_VIEWING_HOLD);

        if (diaryService.isExistByUserId(userId)) {
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(getUserInMemory().getId())
                    .event("Просмотр тренировок пользователя с userId=" + userId)
                    .build());

            List<WorkoutShortResponseDto> listExistsWorkoutsByUser =
                    workoutService.findAllWorkoutByAdminDiaryBySortByDeskDate(userId, getUserInMemory().getId());
            System.out.println(LocalMessages.WORKOUT_FIND_MENU);
            System.out.printf(LocalMessages.WORKOUT_VIEWING_LIST_HEADER, "чужих");

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

                System.out.printf(LocalMessages.WORKOUT_ADMIN_GO_ID, userId);
                int localIdWorkout = scanner.nextInt() - 1;
                scanner.nextLine();
                long workoutId = listExistsWorkoutsByUser.get(localIdWorkout).getId();

                if (workoutService.isExistWorkoutByWorkoutId(workoutId)) {
                    WorkoutFullResponseDto workoutForDelete = workoutService.getWorkoutById(workoutId);
                    getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
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
                    getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                            .userId(userId)
                            .event("Попытка поиска несуществующей тренировки workoutId=" + workoutId)
                            .build());
                    System.out.println(LocalException.NOT_FOUND_ID_EXCEPTION);
                    System.out.println();
                }

            }
        } else {
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Попытка просмотреть несуществующий дневник пользователя userId=" + userId)
                    .build());
            System.out.println(LocalException.NOT_FOUND_ID_EXCEPTION);
        }

        System.out.printf(LocalMessages.MENU_ADMINISTRATOR_VIEWING, "тренировки");
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new ViewWorkoutsAnyUserCommand(getUserInMemory()));
        }
    }
}
