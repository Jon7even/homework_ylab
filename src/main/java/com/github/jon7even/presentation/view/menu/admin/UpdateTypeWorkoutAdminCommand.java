package com.github.jon7even.presentation.view.menu.admin;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutUpdateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.GroupPermissionsService;
import com.github.jon7even.application.services.TypeWorkoutService;
import com.github.jon7even.application.services.impl.GroupPermissionsServiceImpl;
import com.github.jon7even.application.services.impl.TypeWorkoutServiceImpl;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.util.List;
import java.util.Scanner;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.SERVICE_TYPE_WORKOUT;
import static com.github.jon7even.presentation.view.ru.LocalException.ACCESS_DENIED;
import static com.github.jon7even.presentation.view.ru.LocalException.NOT_FOUND_ID_EXCEPTION;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню обновления типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class UpdateTypeWorkoutAdminCommand extends ServiceCommand {
    private final TypeWorkoutService typeWorkoutService;
    private final GroupPermissionsService groupPermissionsService;

    public UpdateTypeWorkoutAdminCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        typeWorkoutService = TypeWorkoutServiceImpl.getInstance();
        groupPermissionsService = GroupPermissionsServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        Long userId = getUserInMemory().getId();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(userId)
                .event("Просмотр редактирования типа тренировки")
                .build());
        System.out.println(UPDATE_TYPE_WORKOUT_MENU);

        boolean isPermissionWrite = groupPermissionsService.getPermissionsForService(
                getUserInMemory().getIdGroupPermissions(), SERVICE_TYPE_WORKOUT.getId(), FlagPermissions.UPDATE
        );

        if (isPermissionWrite) {
            List<TypeWorkoutShortDto> listExistsTypeWorkout = typeWorkoutService.findAllTypeWorkoutsNoSort();
            System.out.println(UPDATE_TYPE_WORKOUT_WAIT);
            System.out.print(TYPE_WORKOUT_VIEWING_LIST_HEADER);
            listExistsTypeWorkout.forEach(t -> System.out.printf(
                    TYPE_WORKOUT_VIEWING_LIST_BODY,
                    t.getId(),
                    t.getTypeName())
            );

            System.out.println(UPDATE_TYPE_WORKOUT_GO_ID_TYPE);
            Long typeWorkoutId = scanner.nextLong();
            scanner.nextLine();

            if (typeWorkoutService.isExistTypeWorkoutByTypeWorkoutId(typeWorkoutId)) {

                System.out.println(UPDATE_TYPE_WORKOUT_GO_NAME);
                String nameTypeWorkout = scanner.nextLine();
                System.out.println(ADD_NEW_TYPE_WORKOUT_GO_CALORIE);
                Integer caloriePerHour = scanner.nextInt();

                List<DetailOfTypeWorkoutResponseDto> listDetailsOfTypeWorkout =
                        typeWorkoutService.findAllDetailOfTypeWorkoutNoSort();
                System.out.print(TYPE_WORKOUT_VIEWING_LIST_DETAILS_HEADER);
                listDetailsOfTypeWorkout.forEach(t -> System.out.printf(
                        TYPE_WORKOUT_VIEWING_LIST_DETAILS_BODY,
                        t.getId(),
                        t.getName())
                );
                System.out.println(ADD_NEW_TYPE_WORKOUT_GO_ID_DETAIL_TYPE);
                Integer detailOfTypeId = scanner.nextInt();

                if (typeWorkoutService.isExistDetailOfTypeByDetailOfTypeId(detailOfTypeId)) {
                    TypeWorkoutUpdateDto typeWorkoutForUpdateInDB = TypeWorkoutUpdateDto.builder()
                            .requesterId(userId)
                            .typeWorkoutId(typeWorkoutId)
                            .typeName(nameTypeWorkout)
                            .caloriePerHour(caloriePerHour)
                            .detailOfTypeId(detailOfTypeId)
                            .build();

                    TypeWorkoutResponseDto updatedExistTypeWorkout =
                            typeWorkoutService.updateTypeWorkout(typeWorkoutForUpdateInDB);
                    getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                            .userId(userId)
                            .event("Обновление существующего типа тренировки с typeWorkoutId=" + typeWorkoutId)
                            .build());

                    System.out.println(UPDATE_TYPE_WORKOUT_COMPLETE_UPDATE);
                    System.out.print(TYPE_WORKOUT_VIEWING_TYPE_HEADER);
                    System.out.printf(TYPE_WORKOUT_VIEWING_TYPE_BODY,
                            updatedExistTypeWorkout.getTypeWorkoutId(),
                            updatedExistTypeWorkout.getCaloriePerHour(),
                            updatedExistTypeWorkout.getCaloriePerHour());
                } else {
                    getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                            .userId(userId)
                            .event("Попытка обновить тип тренировки с несуществующей детализацией detailOfTypeId="
                                    + detailOfTypeId)
                            .build());
                    System.out.println(NOT_FOUND_ID_EXCEPTION);
                }
            } else {
                getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                        .userId(userId)
                        .event("Попытка отредактировать тип тренировки по несуществующему typeWorkoutId="
                                + typeWorkoutId)
                        .build());
                System.out.println(NOT_FOUND_ID_EXCEPTION);
            }
        } else {
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Попытка отредактировать тип тренировки - нет доступа")
                    .build());
            System.out.println(ACCESS_DENIED);
        }
        System.out.println(MENU_WHAT_NEXT_HOLD);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new UpdateTypeWorkoutAdminCommand(getUserInMemory()));
        }
    }
}