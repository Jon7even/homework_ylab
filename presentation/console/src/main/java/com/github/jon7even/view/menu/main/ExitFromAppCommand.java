package com.github.jon7even.view.menu.main;

import com.github.jon7even.view.app.StarterApp;

import static com.github.jon7even.view.config.Settings.RUN_APP;
import static com.github.jon7even.view.ru.LocalMessages.EXIT_MESSAGE;

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
        StarterApp.shutdown(RUN_APP);
    }
}