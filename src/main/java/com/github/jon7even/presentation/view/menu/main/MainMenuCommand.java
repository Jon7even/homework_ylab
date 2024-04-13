package com.github.jon7even.presentation.view.menu.main;

import com.github.jon7even.application.dto.user.UserInMemoryDto;

import static com.github.jon7even.presentation.view.app.StarterApp.shutdown;
import static com.github.jon7even.presentation.view.config.Settings.RUN_APP;

public class MainMenuCommand extends ServiceCommand {
    public MainMenuCommand(UserInMemoryDto userService) {
        setUserInMemory(userService);
    }

    @Override
    public void handle() {
        // TODO
        System.out.println("Пользователь загружен в память:" + getUserInMemory().getLogin());
        shutdown(RUN_APP);
    }
}