package com.github.jon7even.view.menu.workout;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.core.domain.v1.exception.IncorrectTimeException;
import com.github.jon7even.services.ServiceCalculationOfStats;
import com.github.jon7even.services.impl.ServiceCalculationOfStatsImpl;
import com.github.jon7even.utils.DataTimeValidator;
import com.github.jon7even.utils.DateTimeFormat;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalException;
import com.github.jon7even.view.ru.LocalMessages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.github.jon7even.view.config.Settings.NAME_WITHOUT_DETAILS;

/**
 * Меню добавления новой тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class CreateWorkoutCommand extends ServiceCommand {
    private final ServiceCalculationOfStats serviceCalculationOfStats;

    public CreateWorkoutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        serviceCalculationOfStats = ServiceCalculationOfStatsImpl.getInstance();
    }

    @Override
    public void handle() {
        Long userId = getUserInMemory().getId();
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр меню сохранения новой тренировки")
                .build());
        Long diaryId = getDiaryService().getIdDiaryByUserId(userId);

        List<TypeWorkoutShortDto> listExistsTypeWorkout = getTypeWorkoutService().findAllTypeWorkoutsNoSort();
        System.out.print(LocalMessages.TYPE_WORKOUT_VIEWING_LIST_HEADER);
        listExistsTypeWorkout.forEach(t -> System.out.printf(
                LocalMessages.TYPE_WORKOUT_VIEWING_LIST_BODY,
                t.getId(),
                t.getTypeName())
        );
        System.out.println(LocalMessages.WORKOUT_ADD_NEW_MENU);
        System.out.println(LocalMessages.WORKOUT_GO_ID_TYPE_WORKOUT);
        Scanner scanner = getScanner();
        Long typeWorkoutId = scanner.nextLong();
        scanner.nextLine();

        if (getTypeWorkoutService().isExistTypeWorkoutByTypeWorkoutId(typeWorkoutId)) {
            try {
                LocalDateTime currentTime = LocalDateTime.now();
                System.out.println(LocalMessages.WORKOUT_GO_TIME_START);
                System.out.println(LocalException.WORKOUT_WARN_TIME);
                LocalDateTime timeStart = DataTimeValidator.getLocalDateTimeStartAndValidate(
                        currentTime, scanner.nextLine()
                );

                System.out.println(LocalMessages.WORKOUT_GO_TIME_END);
                LocalDateTime timeEnd = DataTimeValidator.getLocalDateTimeEndStartAndValidate(
                        timeStart, scanner.nextInt()
                );

                System.out.println(LocalMessages.WORKOUT_GO_TIME_REST);
                System.out.println(LocalException.WORKOUT_WARN_TIME_REST_DURATION);
                Duration durationOfRest = DataTimeValidator.getDurationAndValidate(
                        timeStart, timeEnd, scanner.nextInt()
                );
                scanner.nextLine();

                System.out.println(LocalMessages.WORKOUT_GO_WEIGHT);
                Float weightUser = Float.parseFloat(scanner.nextLine());

                System.out.println(LocalMessages.WORKOUT_GO_NOTE);
                String personalNote = scanner.nextLine();

                String detailOfWorkout = NAME_WITHOUT_DETAILS;
                DetailOfTypeWorkoutResponseDto detailOfTypeWorkout =
                        getTypeWorkoutService().findTypeWorkoutByTypeWorkoutId(typeWorkoutId)
                                .getDetailOfTypeWorkoutResponseDto();

                if (detailOfTypeWorkout.getIsFillingRequired()) {
                    System.out.printf(LocalMessages.WORKOUT_GO_DETAIL, detailOfTypeWorkout.getName());
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

                WorkoutFullResponseDto workoutSavedDto = getWorkoutService().saveWorkout(workoutForSaveInDB);

                DiaryUpdateDto diaryForUpdateInDb = DiaryUpdateDto.builder()
                        .userId(userId)
                        .weightUser(weightUser)
                        .build();
                if (Objects.equals(timeEnd.getDayOfYear(), currentTime.getDayOfYear())) {
                    diaryForUpdateInDb.setUpdatedOn(timeEnd);
                }
                getDiaryService().updateDiary(diaryForUpdateInDb);

                getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Успешное сохранение тренировки с id=" + workoutSavedDto.getId())
                        .build());

                int minutesOfWorkout = serviceCalculationOfStats.getRealMinutesOfWorkoutFromWorkoutDto(
                        workoutSavedDto
                );
                int totalCalorie = serviceCalculationOfStats.getTotalCalorieFromWorkoutDto(workoutSavedDto);

                System.out.println(LocalMessages.WORKOUT_ADD_NEW_COMPLETE_CREATE);
                System.out.printf(LocalMessages.WORKOUT_FULL_VIEWING_FORM,
                        workoutSavedDto.getTimeStartOn().format(DateTimeFormat.DATA_TIME_FORMAT),
                        workoutSavedDto.getTypeWorkoutResponseDto().getTypeName(),
                        minutesOfWorkout,
                        workoutSavedDto.getTimeOfRest().toMinutes(),
                        workoutSavedDto.getCurrentWeightUser(),
                        totalCalorie,
                        workoutSavedDto.getTypeWorkoutResponseDto()
                                .getDetailOfTypeWorkoutResponseDto()
                                .getName(),
                        workoutSavedDto.getDetailOfWorkout(),
                        workoutSavedDto.getPersonalNote()
                );
            } catch (IncorrectTimeException e) {
                System.out.println(LocalException.BAD_INPUT_TIME_EXCEPTION);
            } catch (Exception e) {
                System.out.println(LocalException.BAD_INPUT_EXCEPTION);
            }
        } else {
            getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Попытка сохранить тренировку по несуществующему typeWorkoutId="
                            + typeWorkoutId)
                    .build());
            System.out.println(LocalException.NOT_FOUND_ID_EXCEPTION);
            System.out.println();
        }
        System.out.println(LocalMessages.WORKOUT_MAIN_MENU_WHAT_NEXT);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new CreateWorkoutCommand(getUserInMemory()));
        }
    }
}