package com.github.jon7even.presentation.view.menu.main;

import lombok.Data;

import java.util.Scanner;

/**
 * Абстрактный класс для вывода меню в консоль
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
public abstract class ServiceCommand {
    private Scanner scanner = new Scanner(System.in);
    private ServiceCommand commandNextMenu;

    /**
     * Абстрактный метод для обработки меню
     */
    public abstract void handle();

}
