package com.github.jon7even.view.menu.admin;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.utils.DateTimeFormat;
import com.github.jon7even.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.view.menu.main.MainMenuCommand;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.user.SignOutCommand;
import com.github.jon7even.view.ru.LocalMessages;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Меню просмотра истории любого пользователя по его ID
 *
 * @author Jon7even
 * @version 1.0
 */
public class ViewHistoryAnyUserCommand extends ServiceCommand {
    public ViewHistoryAnyUserCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр меню поиска истории действий любого пользователя")
                .build());
        System.out.println(LocalMessages.MENU_ADMINISTRATOR_VIEWING_HISTORY_START);

        Long userId = scanner.nextLong();
        System.out.println(LocalMessages.MENU_ADMINISTRATOR_VIEWING_HOLD);

        getHistoryUserService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр истории пользователя с userId=" + userId)
                .build());

        List<HistoryUserResponseByAdminDto> historyList = getHistoryUserService().findAllHistoryByAdminIdSortByDeskDate(
                userId, getUserInMemory().getId()
        );

        System.out.printf(LocalMessages.VIEWING_HISTORY_HEADER_ADMIN, userId);
        historyList.forEach(h -> System.out.printf(
                LocalMessages.VIEWING_HISTORY_FORM_ADMIN,
                h.getDateTimeOn().format(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_TIME_DEFAULT)),
                h.getId(),
                h.getEvent())
        );

        System.out.printf(LocalMessages.MENU_ADMINISTRATOR_VIEWING, "историю");
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new ViewHistoryAnyUserCommand(getUserInMemory()));
        }
    }
}
