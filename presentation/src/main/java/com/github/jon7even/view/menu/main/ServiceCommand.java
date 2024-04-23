package com.github.jon7even.view.menu.main;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.services.HistoryUserService;
import com.github.jon7even.services.impl.HistoryUserServiceImpl;
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
