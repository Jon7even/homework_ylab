package com.github.jon7even.presentation.in;

import com.github.jon7even.infrastructure.dataproviders.core.LiquibaseManager;
import com.github.jon7even.infrastructure.dataproviders.core.impl.LiquibaseManagerImpl;
import com.github.jon7even.presentation.view.menu.main.ServiceCommand;
import com.github.jon7even.presentation.view.menu.main.StartCommand;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.github.jon7even.presentation.view.config.Settings.RUN_APP;
import static com.github.jon7even.presentation.view.ru.LocalException.INCORRECT_INPUT;

/**
 * Запуск приложения
 *
 * @author Jon7even
 * @version 1.0
 */
public class TrainingDiaryApp {
    public static void main(String[] args) {
        ServiceCommand serviceCommand = new StartCommand();
        LiquibaseManager liquibaseManager = LiquibaseManagerImpl.getInstance();
        liquibaseManager.initMigrate();

        while (RUN_APP) {
            try {
                serviceCommand.handle();
            } catch (InputMismatchException | IndexOutOfBoundsException exception) {
                System.out.println(INCORRECT_INPUT);
                serviceCommand.setScanner(new Scanner(System.in));
                serviceCommand.setCommandNextMenu(serviceCommand);
            }
            serviceCommand = serviceCommand.getCommandNextMenu();
        }
    }
}