package com.github.jon7even.presentation.view.menu.main;

import static com.github.jon7even.presentation.view.app.StarterApp.shutdown;
import static com.github.jon7even.presentation.view.config.Settings.RUN_APP;
import static com.github.jon7even.presentation.view.ru.LocalMessages.EXIT_MESSAGE;

/**
 * Команда меню - выход из программы
 *
 * @author Jon7even
 * @version 1.0
 */
public class ExitFromAppCommand extends ServiceCommand {
    @Override
    public void handle() {
        System.out.println(EXIT_MESSAGE);
        shutdown(RUN_APP);
    }
}