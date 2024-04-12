package com.github.jon7even.presentation.view.menu.user;

import com.github.jon7even.presentation.view.menu.main.ServiceCommand;

import static com.github.jon7even.presentation.view.app.StarterApp.shutdown;
import static com.github.jon7even.presentation.view.config.Settings.RUN_APP;

/**
 * Меню с авторизацией пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class AuthorizationCommand extends ServiceCommand {
    @Override
    public void handle() {
        //TODO
        shutdown(RUN_APP);
    }
}