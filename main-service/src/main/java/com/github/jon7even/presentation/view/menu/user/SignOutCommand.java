package com.github.jon7even.presentation.view.menu.user;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.main.StartCommand;

import static com.github.jon7even.presentation.view.ru.LocalMessages.SIGN_OUT;

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
        System.out.println(SIGN_OUT);
        getHistoryService().createHistoryOfUser(HistoryUserCreateDto.builder()
                .userId(getUserInMemory().getId())
                .event("Выход из своего аккаунта")
                .build());
        setCommandNextMenu(new StartCommand());
    }
}