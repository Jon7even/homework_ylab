package com.github.jon7even.presentation.view.app;

import lombok.experimental.UtilityClass;

import static com.github.jon7even.presentation.view.config.Settings.RUN_APP;

/**
 * Утилитарный класс для выхода из программы
 *
 * @author Jon7even
 * @version 1.0
 */
@UtilityClass
public class StarterApp {
    public static void shutdown(Boolean flag) {
        System.out.println("Выключаю приложение...");
        System.out.println("Внимание! Данные будут потеряны, процесс необратим.");
        System.out.println("3...");
        System.out.println("2...");
        System.out.println("1...");
        System.out.println("1...");
        RUN_APP = false;
    }
}
