package com.github.jon7even.presentation.view.menu.admin;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.HistoryUserService;
import com.github.jon7even.application.services.impl.HistoryUserServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.user.SignOutCommand;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static com.github.jon7even.presentation.utils.DateTimeFormat.DATE_TIME_DEFAULT;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню просмотра истории любого пользователя по его ID
 *
 * @author Jon7even
 * @version 1.0
 */
public class ViewHistoryAnyUserCommand extends ServiceCommand {
    private final HistoryUserService historyUserService;

    public ViewHistoryAnyUserCommand(UserInMemoryDto userService) {
        historyUserService = HistoryUserServiceImpl.getInstance();
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр истории действия другого пользователя")
                .build());

        System.out.println(MENU_ADMINISTRATOR_VIEWING_HISTORY_START);

        Long userId = scanner.nextLong();
        System.out.println(MENU_ADMINISTRATOR_VIEWING_HISTORY_HOLD);

        List<HistoryUserResponseByAdminDto> historyList = historyUserService.findAllHistoryByAdminIdSortByDeskDate(
                userId, getUserInMemory().getId()
        );

        System.out.printf(VIEWING_HISTORY_HEADER_ADMIN, userId);
        historyList.forEach(h -> System.out.printf(
                VIEWING_HISTORY_FORM_ADMIN,
                h.getDateTimeOn().format(DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT)),
                h.getId(),
                h.getEvent())
        );

        System.out.println(MENU_ADMINISTRATOR_VIEWING_HISTORY);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainAdminMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new ViewHistoryAnyUserCommand(getUserInMemory()));
        }
    }
}
