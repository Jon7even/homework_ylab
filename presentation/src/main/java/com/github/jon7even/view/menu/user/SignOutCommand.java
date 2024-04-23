package com.github.jon7even.view.menu.user;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.main.StartCommand;
import com.github.jon7even.view.ru.LocalMessages;

/**
 * Меню автоматически выходит из аккаунта пользователя и перевод на стартовое меню
 *
 * @author Jon7even
 * @version 1.0
 */
public class SignOutCommand extends ServiceCommand {
    public SignOutCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        System.out.println(LocalMessages.SIGN_OUT);
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Выход из своего аккаунта")
                .build());
        setCommandNextMenu(new StartCommand());
    }
}