package com.github.jon7even.presentation.view.app;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.jon7even.presentation.view.config.Settings.RUN_APP;

/**
 * Утилитарный класс для выхода из программы
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StarterApp {
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
