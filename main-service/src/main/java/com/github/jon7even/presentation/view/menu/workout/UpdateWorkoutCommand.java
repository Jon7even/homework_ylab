package com.github.jon7even.presentation.view.menu.workout;

import com.github.jon7even.application.dto.diary.DiaryUpdateDto;
import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutShortResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutUpdateDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.application.services.ServiceCalculationOfStats;
import com.github.jon7even.application.services.TypeWorkoutService;
import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.application.services.impl.DiaryServiceImpl;
import com.github.jon7even.application.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.application.services.impl.TypeWorkoutServiceImpl;
import com.github.jon7even.application.services.impl.WorkoutServiceImpl;
import com.github.jon7even.core.domain.v1.exception.IncorrectTimeException;
import com.github.jon7even.presentation.utils.DataTimeValidator;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.github.jon7even.presentation.utils.DateTimeFormat.DATA_TIME_FORMAT;
import static com.github.jon7even.presentation.view.config.Settings.NAME_WITHOUT_DETAILS;
import static com.github.jon7even.presentation.view.ru.LocalException.*;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

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
        Long diaryId = diaryService.getIdDiaryByUserId(userId);

        List<WorkoutShortResponseDto> listExistsWorkoutsByUser =
                workoutService.findAllWorkoutByOwnerDiaryBySortByDeskDate(diaryId, userId);
        System.out.println(WORKOUT_UPDATE_MENU);
        System.out.printf(WORKOUT_VIEWING_LIST_HEADER, "своих");

        Scanner scanner = getScanner();
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

                WorkoutFullResponseDto workoutForUpdate = workoutService.getWorkoutById(workoutId);

                int minutesOfWorkoutUpdate = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                        workoutForUpdate
                );
                int totalCalorieUpdate = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutForUpdate);
                System.out.printf(WORKOUT_FULL_VIEWING_FORM,
                        workoutForUpdate.getTimeStartOn().format(DATA_TIME_FORMAT),
                        workoutForUpdate.getTypeWorkoutResponseDto().getTypeName(),
                        minutesOfWorkoutUpdate,
                        workoutForUpdate.getTimeOfRest().toMinutes(),
                        workoutForUpdate.getCurrentWeightUser(),
                        totalCalorieUpdate,
                        workoutForUpdate.getTypeWorkoutResponseDto()
                                .getDetailOfTypeWorkoutResponseDto()
                                .getName(),
                        workoutForUpdate.getDetailOfWorkout(),
                        workoutForUpdate.getPersonalNote()
                );

                System.out.println(WORKOUT_UPDATE_PREPARE);
                List<TypeWorkoutShortDto> listExistsTypeWorkout = typeWorkoutService.findAllTypeWorkoutsNoSort();
                System.out.print(TYPE_WORKOUT_VIEWING_LIST_HEADER);
                listExistsTypeWorkout.forEach(t -> System.out.printf(
                        TYPE_WORKOUT_VIEWING_LIST_BODY,
                        t.getId(),
                        t.getTypeName())
                );
                System.out.println(WORKOUT_GO_ID_TYPE_WORKOUT);
                Long typeWorkoutId = scanner.nextLong();
                scanner.nextLine();

                if (typeWorkoutService.isExistTypeWorkoutByTypeWorkoutId(typeWorkoutId)) {
                    try {
                        LocalDateTime currentTime = LocalDateTime.now();
                        System.out.println(WORKOUT_GO_TIME_START);
                        System.out.println(WORKOUT_WARN_TIME);
                        LocalDateTime timeStart = DataTimeValidator.getLocalDateTimeStartAndValidate(
                                currentTime, scanner.nextLine()
                        );

                        System.out.println(WORKOUT_GO_TIME_END);
                        LocalDateTime timeEnd = DataTimeValidator.getLocalDateTimeEndStartAndValidate(
                                timeStart, scanner.nextInt()
                        );

                        System.out.println(WORKOUT_GO_TIME_REST);
                        System.out.println(WORKOUT_WARN_TIME_REST_DURATION);
                        Duration durationOfRest = DataTimeValidator.getDurationAndValidate(
                                timeStart, timeEnd, scanner.nextInt()
                        );
                        scanner.nextLine();

                        System.out.println(WORKOUT_GO_WEIGHT);
                        Float weightUser = Float.parseFloat(scanner.nextLine());

                        System.out.println(WORKOUT_GO_NOTE);
                        String personalNote = scanner.nextLine();

                        String detailOfWorkout = NAME_WITHOUT_DETAILS;
                        DetailOfTypeWorkoutResponseDto detailOfTypeWorkout =
                                typeWorkoutService.findTypeWorkoutByTypeWorkoutId(typeWorkoutId)
                                        .getDetailOfTypeWorkoutResponseDto();

                        if (detailOfTypeWorkout.getIsFillingRequired()) {
                            System.out.printf(WORKOUT_GO_DETAIL, detailOfTypeWorkout.getName());
                            detailOfWorkout = scanner.nextLine();
                        }

                        WorkoutUpdateDto workoutForUpdateInDB = WorkoutUpdateDto.builder()
                                .id(workoutForUpdate.getId())
                                .idDiary(diaryId)
                                .idTypeWorkout(typeWorkoutId)
                                .timeStartOn(timeStart)
                                .timeEndOn(timeEnd)
                                .timeOfRest(durationOfRest)
                                .currentWeightUser(weightUser)
                                .personalNote(personalNote)
                                .detailOfWorkout(detailOfWorkout)
                                .build();

                        WorkoutFullResponseDto workoutUpdatedDto = workoutService.updateWorkout(workoutForUpdateInDB);

                        DiaryUpdateDto diaryForUpdateInDb = DiaryUpdateDto.builder()
                                .userId(userId)
                                .weightUser(weightUser)
                                .build();
                        if (Objects.equals(timeEnd.getDayOfYear(), currentTime.getDayOfYear())) {
                            diaryForUpdateInDb.setUpdatedOn(timeEnd);
                        }
                        diaryService.updateDiary(diaryForUpdateInDb);

                        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                                .userId(userId)
                                .event("Успешное обновление тренировки с id=" + workoutUpdatedDto.getId())
                                .build());

                        int minutesOfWorkout = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                                workoutUpdatedDto
                        );
                        int totalCalorie = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutUpdatedDto);
                        System.out.println(WORKOUT_UPDATE_COMPLETE_UPDATE);
                        System.out.printf(WORKOUT_FULL_VIEWING_FORM,
                                workoutUpdatedDto.getTimeStartOn().format(DATA_TIME_FORMAT),
                                workoutUpdatedDto.getTypeWorkoutResponseDto().getTypeName(),
                                minutesOfWorkout,
                                workoutUpdatedDto.getTimeOfRest().toMinutes(),
                                workoutUpdatedDto.getCurrentWeightUser(),
                                totalCalorie,
                                workoutUpdatedDto.getTypeWorkoutResponseDto()
                                        .getDetailOfTypeWorkoutResponseDto()
                                        .getName(),
                                workoutUpdatedDto.getDetailOfWorkout(),
                                workoutUpdatedDto.getPersonalNote()
                        );
                    } catch (IncorrectTimeException e) {
                        System.out.println(BAD_INPUT_TIME_EXCEPTION);
                    } catch (Exception e) {
                        System.out.println(BAD_INPUT_EXCEPTION);
                    }

                } else {
                    getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                            .userId(userId)
                            .event("Попытка отредактировать тренировку по несуществующему typeWorkoutId="
                                    + typeWorkoutId)
                            .build());
                    System.out.println(NOT_FOUND_ID_EXCEPTION);
                }

            } else {
                getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Попытка отредактировать несуществующую тренировку workoutId=" + workoutId)
                        .build());
                System.out.println(NOT_FOUND_ID_EXCEPTION);
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
            default -> setCommandNextMenu(new UpdateWorkoutCommand(getUserInMemory()));
        }
    }
}
