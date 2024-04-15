package com.github.jon7even.presentation.view.menu.admin;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutCreateDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
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

import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню добавления нового типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class AddTypeWorkoutAdminCommand extends ServiceCommand {
    private final TypeWorkoutService typeWorkoutService;
    private final GroupPermissionsService groupPermissionsService;

    public AddTypeWorkoutAdminCommand(UserInMemoryDto userService) {
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
                .event("Просмотр меню добавления нового типа тренировки")
                .build());
        System.out.println(ADD_NEW_TYPE_WORKOUT_MENU);

        boolean isPermissionWrite = groupPermissionsService.getPermissionsForService(
                getUserInMemory().getIdGroupPermissions(), 4, FlagPermissions.WRITE
        );

        if (isPermissionWrite) {
            List<TypeWorkoutShortDto> listExistsTypeWorkout = typeWorkoutService.findAllTypeWorkoutsNoSort();
            System.out.println(ADD_NEW_TYPE_WORKOUT_WAIT);
            System.out.print(TYPE_WORKOUT_VIEWING_LIST_HEADER);
            listExistsTypeWorkout.forEach(t -> System.out.printf(
                    TYPE_WORKOUT_VIEWING_LIST_BODY,
                    t.getId(),
                    t.getTypeName())
            );

            System.out.println(ADD_NEW_TYPE_WORKOUT_GO_NAME);
            String nameTypeWorkout = scanner.nextLine();
            System.out.println(ADD_NEW_TYPE_WORKOUT_GO_CALORIE);
            Integer caloriePerHour = scanner.nextInt();

            TypeWorkoutCreateDto typeWorkoutForSaveInDB = TypeWorkoutCreateDto.builder()
                    .requesterId(userId)
                    .typeName(nameTypeWorkout)
                    .caloriePerHour(caloriePerHour)
                    .build();

            TypeWorkoutResponseDto createdNewTypeWorkout = typeWorkoutService.createTypeWorkout(typeWorkoutForSaveInDB);
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Создание нового типа тренировки с Id=" + createdNewTypeWorkout.getTypeWorkoutId())
                    .build());

            System.out.println(ADD_NEW_TYPE_WORKOUT_COMPLETE_CREATE);
            System.out.print(TYPE_WORKOUT_VIEWING_TYPE_HEADER);
            System.out.printf(TYPE_WORKOUT_VIEWING_TYPE_BODY,
                    createdNewTypeWorkout.getTypeWorkoutId(),
                    createdNewTypeWorkout.getCaloriePerHour(),
                    createdNewTypeWorkout.getCaloriePerHour());
        } else {
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(userId)
                    .event("Попытка добавить новый тип тренировки - нет доступа")
                    .build());
            System.out.println(ADD_NEW_TYPE_WORKOUT_ACCESS_DENIED);
        }
        System.out.println(MENU_WHAT_NEXT_HOLD);

        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new AddTypeWorkoutAdminCommand(getUserInMemory()));
        }
    }
}