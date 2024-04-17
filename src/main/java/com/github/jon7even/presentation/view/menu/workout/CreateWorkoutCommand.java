package com.github.jon7even.presentation.view.menu.workout;

import com.github.jon7even.application.dto.diary.DiaryUpdateDto;
import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.dto.workout.WorkoutCreateDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
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
 * Меню добавления новой тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class CreateWorkoutCommand extends ServiceCommand {
    private final DiaryService diaryService;
    private final TypeWorkoutService typeWorkoutService;
    private final WorkoutService workoutService;
    private final ServiceCalculationOfStats serviceCalculationOfStats;

    public CreateWorkoutCommand(UserInMemoryDto userService) {
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
                .event("Просмотр меню сохранения новой тренировки")
                .build());
        Long diaryId = diaryService.getIdDiaryByUserId(userId);

        List<TypeWorkoutShortDto> listExistsTypeWorkout = typeWorkoutService.findAllTypeWorkoutsNoSort();
        System.out.print(TYPE_WORKOUT_VIEWING_LIST_HEADER);
        listExistsTypeWorkout.forEach(t -> System.out.printf(
                TYPE_WORKOUT_VIEWING_LIST_BODY,
                t.getId(),
                t.getTypeName())
        );
        System.out.println(WORKOUT_ADD_NEW_MENU);
        System.out.println(WORKOUT_ADD_NEW_GO_ID_TYPE_WORKOUT);
        Scanner scanner = getScanner();
        Long typeWorkoutId = scanner.nextLong();
        scanner.nextLine();

        if (typeWorkoutService.isExistTypeWorkoutByTypeWorkoutId(typeWorkoutId)) {
            try {
                LocalDateTime currentTime = LocalDateTime.now();
                System.out.println(WORKOUT_ADD_NEW_GO_TIME_START);
                System.out.println(WORKOUT_WARN_TIME);
                LocalDateTime timeStart = DataTimeValidator.getLocalDateTimeStartAndValidate(
                        currentTime, scanner.nextLine()
                );

                System.out.println(WORKOUT_ADD_NEW_GO_TIME_END);
                LocalDateTime timeEnd = DataTimeValidator.getLocalDateTimeEndStartAndValidate(
                        timeStart, scanner.nextInt()
                );

                System.out.println(WORKOUT_ADD_NEW_GO_TIME_REST);
                System.out.println(WORKOUT_WARN_TIME_REST_DURATION);
                Duration durationOfRest = DataTimeValidator.getDurationAndValidate(
                        timeStart, timeEnd, scanner.nextInt()
                );
                scanner.nextLine();

                System.out.println(WORKOUT_ADD_NEW_GO_WEIGHT);
                Float weightUser = Float.parseFloat(scanner.nextLine());

                System.out.println(WORKOUT_ADD_NEW_GO_NOTE);
                String personalNote = scanner.nextLine();

                String detailOfWorkout = NAME_WITHOUT_DETAILS;
                DetailOfTypeWorkoutResponseDto detailOfTypeWorkout =
                        typeWorkoutService.findTypeWorkoutByTypeWorkoutId(typeWorkoutId)
                                .getDetailOfTypeWorkoutResponseDto();

                if (detailOfTypeWorkout.getIsFillingRequired()) {
                    System.out.printf(WORKOUT_ADD_NEW_GO_DETAIL, detailOfTypeWorkout.getName());
                    detailOfWorkout = scanner.nextLine();
                }

                WorkoutCreateDto workoutForSaveInDB = WorkoutCreateDto.builder()
                        .idDiary(diaryId)
                        .idTypeWorkout(typeWorkoutId)
                        .timeStartOn(timeStart)
                        .timeEndOn(timeEnd)
                        .timeOfRest(durationOfRest)
                        .currentWeightUser(weightUser)
                        .personalNote(personalNote)
                        .detailOfWorkout(detailOfWorkout)
                        .build();

                WorkoutFullResponseDto workoutFullResponseDto = workoutService.saveWorkout(workoutForSaveInDB);

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
                        .event("Успешное сохранение тренировки с id=" + workoutFullResponseDto.getId())
                        .build());

                System.out.println(WORKOUT_ADD_NEW_COMPLETE_CREATE);
                int minutesOfWorkout = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                        workoutFullResponseDto
                );
                int totalCalorie = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutFullResponseDto);

                System.out.printf(WORKOUT_FULL_VIEWING_FORM,
                        workoutFullResponseDto.getTimeStartOn().format(DATA_TIME_FORMAT),
                        workoutFullResponseDto.getTypeWorkoutResponseDto().getTypeName(),
                        minutesOfWorkout,
                        workoutFullResponseDto.getTimeOfRest().toMinutes(),
                        workoutFullResponseDto.getCurrentWeightUser(),
                        totalCalorie,
                        workoutFullResponseDto.getTypeWorkoutResponseDto()
                                .getDetailOfTypeWorkoutResponseDto()
                                .getName(),
                        workoutFullResponseDto.getDetailOfWorkout(),
                        workoutFullResponseDto.getPersonalNote()
                );
            } catch (IncorrectTimeException e) {
                System.out.println(BAD_INPUT_TIME_EXCEPTION);
            } catch (Exception e) {
                System.out.println(BAD_INPUT_EXCEPTION);
            }
        } else {
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Попытка сохранить тренировку по несуществующему typeWorkoutId="
                            + typeWorkoutId)
                    .build());
            System.out.println(NOT_FOUND_ID_EXCEPTION);
            System.out.println();
        }
        System.out.println(WORKOUT_MAIN_MENU_WHAT_NEXT);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new CreateWorkoutCommand(getUserInMemory()));
        }
    }
}