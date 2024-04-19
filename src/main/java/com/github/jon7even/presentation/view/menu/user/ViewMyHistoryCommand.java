package com.github.jon7even.presentation.view.menu.user;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.HistoryUserService;
import com.github.jon7even.application.services.impl.HistoryUserServiceImpl;
import com.github.jon7even.presentation.view.menu.main.ExitFromAppCommand;
import com.github.jon7even.presentation.view.menu.main.MainMenuCommand;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static com.github.jon7even.presentation.utils.DateTimeFormat.DATE_TIME_DEFAULT;
import static com.github.jon7even.presentation.view.ru.LocalMessages.*;

/**
 * Меню просмотра своей истории действий
 *
 * @author Jon7even
 * @version 1.0
 */
public class ViewMyHistoryCommand extends ServiceCommand {
    private final HistoryUserService historyUserService;

    public ViewMyHistoryCommand(UserInMemoryDto userService) {
        historyUserService = HistoryUserServiceImpl.getInstance();
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        Scanner scanner = getScanner();
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Просмотр своей истории действий")
                .build());

        List<HistoryUserResponseByUserDto> historyList = historyUserService.findAllHistoryByOwnerIdSortByDeskDate(
                getUserInMemory().getId()
        );

        System.out.printf(VIEWING_HISTORY_HEADER_USER);
        historyList.forEach(h -> System.out.printf(
                VIEWING_HISTORY_FORM_USER,
                h.getDateTimeOn().format(DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT)),
                h.getEvent())
        );

        System.out.println(VIEWING_MY_HISTORY);
        switch (scanner.nextInt()) {
            case 1 -> setCommandNextMenu(new MainUserMenuCommand(getUserInMemory()));
            case 2 -> setCommandNextMenu(new MainMenuCommand(getUserInMemory()));
            case 3 -> setCommandNextMenu(new SignOutCommand(getUserInMemory()));
            case 0 -> setCommandNextMenu(new ExitFromAppCommand());
            default -> setCommandNextMenu(new ViewMyHistoryCommand(getUserInMemory()));
        }
    }
}