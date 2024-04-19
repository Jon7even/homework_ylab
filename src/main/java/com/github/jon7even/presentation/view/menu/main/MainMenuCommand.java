package com.github.jon7even.presentation.view.menu.main;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.GroupPermissionsService;
import com.github.jon7even.application.services.impl.GroupPermissionsServiceImpl;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.presentation.view.menu.admin.MainAdminMenuCommand;
import com.github.jon7even.presentation.view.menu.diary.MainDiaryMenuCommand;
import com.github.jon7even.presentation.view.menu.user.MainUserMenuCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;
import com.github.jon7even.presentation.view.menu.workout.MainMenuWorkoutCommand;

import java.util.Scanner;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.SERVICE_USER;
import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_ADMIN;
import static com.github.jon7even.presentation.view.ru.LocalMessages.MAIN_MENU;

/**
 * Главное меню приложение, здесь отображаются основные функции
 *
 * @author Jon7even
 * @version 1.0
 */
public class MainMenuCommand extends ServiceCommand {
    private final GroupPermissionsService groupPermissionsService;

    public MainMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
        groupPermissionsService = GroupPermissionsServiceImpl.getInstance();
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        boolean secret = groupPermissionsService.getPermissionsForService(
                getUserInMemory().getIdGroupPermissions(), SERVICE_USER.getId(), FlagPermissions.READ
        );

        if (secret) {
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
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
            getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
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