package com.github.jon7even.presentation.view.menu.main;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.services.HistoryUserService;
import com.github.jon7even.application.services.impl.HistoryUserServiceImpl;
import lombok.Data;

import java.util.Scanner;

/**
 * Абстрактный класс для вывода меню в консоль
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
public abstract class ServiceCommand {
    private Scanner scanner = new Scanner(System.in);
    private ServiceCommand commandNextMenu;
    private UserInMemoryDto userInMemory;
    private HistoryUserCreateDto historyUserCreateDto;
    private final HistoryUserService historyUserService = HistoryUserServiceImpl.getInstance();

    /**
     * Абстрактный метод для обработки меню
     */
    public abstract void handle();

    public HistoryUserService getHistoryService() {
        return historyUserService;
    }

}
