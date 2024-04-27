package com.github.jon7even.view.menu.admin;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.dataproviders.inmemory.constants.InitialCommonDataInDb;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalException;
import com.github.jon7even.view.ru.LocalMessages;

import java.util.List;
import java.util.Scanner;

/**
 * Меню добавления нового типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class AddTypeWorkoutAdminCommand extends ServiceCommand {
    public AddTypeWorkoutAdminCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        Long userId = getUserInMemory().getId();
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр меню добавления нового типа тренировки")
                .build());
        System.out.println(LocalMessages.ADD_NEW_TYPE_WORKOUT_MENU);

        boolean isPermissionWrite = getGroupPermissionsService().getPermissionsForService(
                getUserInMemory().getIdGroupPermissions(),
                InitialCommonDataInDb.SERVICE_TYPE_WORKOUT.getId(), FlagPermissions.WRITE
        );

        if (isPermissionWrite) {
            List<TypeWorkoutShortDto> listExistsTypeWorkout = getTypeWorkoutService().findAllTypeWorkoutsNoSort();
            System.out.println(LocalMessages.ADD_NEW_TYPE_WORKOUT_WAIT);
            System.out.print(LocalMessages.TYPE_WORKOUT_VIEWING_LIST_HEADER);
            listExistsTypeWorkout.forEach(t -> System.out.printf(
                    LocalMessages.TYPE_WORKOUT_VIEWING_LIST_BODY,
                    t.getId(),
                    t.getTypeName())
            );

            System.out.println(LocalMessages.ADD_NEW_TYPE_WORKOUT_GO_NAME);
            String nameTypeWorkout = scanner.nextLine();
            System.out.println(LocalMessages.ADD_NEW_TYPE_WORKOUT_GO_CALORIE);
            Integer caloriePerHour = scanner.nextInt();

            List<DetailOfTypeWorkoutResponseDto> listDetailsOfTypeWorkout =
                    getTypeWorkoutService().findAllDetailOfTypeWorkoutNoSort();
            System.out.print(LocalMessages.TYPE_WORKOUT_VIEWING_LIST_DETAILS_HEADER);
            listDetailsOfTypeWorkout.forEach(t -> System.out.printf(
                    LocalMessages.TYPE_WORKOUT_VIEWING_LIST_DETAILS_BODY,
                    t.getId(),
                    t.getName())
            );
            System.out.println(LocalMessages.ADD_NEW_TYPE_WORKOUT_GO_ID_DETAIL_TYPE);
            Integer detailOfTypeId = scanner.nextInt();

            if (getTypeWorkoutService().isExistDetailOfTypeByDetailOfTypeId(detailOfTypeId)) {
                TypeWorkoutCreateDto typeWorkoutForSaveInDB = TypeWorkoutCreateDto.builder()
                        .requesterId(userId)
                        .typeName(nameTypeWorkout)
                        .caloriePerHour(caloriePerHour)
                        .detailOfTypeId(detailOfTypeId)
                        .build();

                TypeWorkoutResponseDto createdNewTypeWorkout =
                        getTypeWorkoutService().createTypeWorkout(typeWorkoutForSaveInDB);
                getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Создание нового типа тренировки с Id=" + createdNewTypeWorkout.getTypeWorkoutId())
                        .build());

                System.out.println(LocalMessages.ADD_NEW_TYPE_WORKOUT_COMPLETE_CREATE);
                System.out.print(LocalMessages.TYPE_WORKOUT_VIEWING_TYPE_HEADER);
                System.out.printf(LocalMessages.TYPE_WORKOUT_VIEWING_TYPE_BODY,
                        createdNewTypeWorkout.getTypeWorkoutId(),
                        createdNewTypeWorkout.getCaloriePerHour(),
                        createdNewTypeWorkout.getCaloriePerHour());
            } else {
                getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Попытка добавить новый тип тренировки с несуществующей детализацией detailOfTypeId="
                                + detailOfTypeId)
                        .build());
                System.out.println(LocalException.NOT_FOUND_ID_EXCEPTION);
            }

        } else {
            getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Попытка добавить новый тип тренировки - нет доступа")
                    .build());
            System.out.println(LocalException.ACCESS_DENIED);
        }
        System.out.println(LocalMessages.MENU_WHAT_NEXT_HOLD);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new AddTypeWorkoutAdminCommand(getUserInMemory()));
        }
    }
}