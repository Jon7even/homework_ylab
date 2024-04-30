package com.github.jon7even.in;

import com.github.jon7even.dataproviders.configuration.ConfigLoader;
import com.github.jon7even.dataproviders.core.LiquibaseManager;
import com.github.jon7even.dataproviders.core.impl.LiquibaseManagerImpl;
import com.github.jon7even.view.menu.main.ServiceCommand;
import com.github.jon7even.view.menu.main.StartCommand;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.github.jon7even.view.config.Settings.RUN_APP;
import static com.github.jon7even.view.ru.LocalException.INCORRECT_INPUT;

/**
 * @author Jon7even
 * @version 1.0
 * @deprecated Запуск приложения в консоле
 * Теперь приложение переведено на сервлеты. Чтобы заработал данный модуль, требуется сборку выполнять с этого
 * пакета. Далее, отключить Loggable в сервисах или наоборот подключить её зависимость. Настроить ссылку на настройки.
 */
public class TrainingDiaryApp {
    public static void main(String[] args) {
        ServiceCommand serviceCommand = new StartCommand();
        LiquibaseManager liquibaseManager = new LiquibaseManagerImpl(ConfigLoader.getInstance().getConfig());
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