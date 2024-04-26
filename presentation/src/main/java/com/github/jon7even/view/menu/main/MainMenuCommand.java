package com.github.jon7even.view.menu.main;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.dataproviders.inmemory.constants.InitialCommonDataInDb;
import com.github.jon7even.view.menu.admin.MainAdminMenuCommand;
import com.github.jon7even.view.menu.diary.MainDiaryMenuCommand;
import com.github.jon7even.view.menu.user.MainUserMenuCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.menu.workout.MainMenuWorkoutCommand;

import java.util.Scanner;

import static com.github.jon7even.view.ru.LocalMessages.MAIN_ADMIN;
import static com.github.jon7even.view.ru.LocalMessages.MAIN_MENU;

/**
 * Главное меню приложение, здесь отображаются основные функции
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainMenuCommand extends ServiceCommand {

    public MainMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        boolean secret = getGroupPermissionsService().getPermissionsForService(
                getUserInMemory().getIdGroupPermissions(),
                InitialCommonDataInDb.SERVICE_USER.getId(),
                FlagPermissions.READ
        );

        if (secret) {
            getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(getUserInMemory().getId())
                    .event("Просмотр главного меню приложения роль - администратор")
                    .build());
            System.out.println(MAIN_ADMIN);

            switch (scanner.nextInt()) {
                case 1 -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
                case 2 -> setCommandNextMenu(new MainDiaryMenuCommand(getUserInMemory()));
                case 3 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
                case 4 -> setCommandNextMenu(new MainUserMenuCommand(getUserInMemory()));
                case 5 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
                case 0 -> setCommandNextMenu(new ExitFromAppCommand());
                default -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            }
        } else {
            getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                    .userId(getUserInMemory().getId())
                    .event("Просмотр главного меню приложения роль - пользователь")
                    .build());
            System.out.println(MAIN_MENU);

            switch (scanner.nextInt()) {
                case 1 -> setCommandNextMenu(new MainDiaryMenuCommand(getUserInMemory()));
                case 2 -> setCommandNextMenu(new MainMenuWorkoutCommand(getUserInMemory()));
                case 3 -> setCommandNextMenu(new MainUserMenuCommand(getUserInMemory()));
                case 4 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
                case 5 -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
                case 0 -> setCommandNextMenu(new ExitFromAppCommand());
                default -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            }
        }
    }
}