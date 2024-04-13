package com.github.jon7even.presentation.view.ru;

import lombok.experimental.UtilityClass;

/**
 * Утилитарный класс для сообщений в меню
 *
 * @author Jon7even
 * @version 1.0
 */
@UtilityClass
public class LocalMessages {
    public static final String GREET_MESSAGE = "Добро пожаловать в приложение *Дневник тренировок*";
    public static final String START_MENU = "Выберите пункт меню: \n"
            + "1.Авторизация\n"
            + "2.Регистрация\n"
            + "0.Завершить работу с приложением\n";
    public static final String EXIT_MESSAGE = "Запускаю команду выключения приложения";

    public static final String AUTH_LOGIN_MESSAGE = "Пожалуйста введите существующий логин";
    public static final String AUTH_PASSWORD_MESSAGE = "Пожалуйста введите пароль";
    public static final String AUTH_SUCCESS = "Добро пожаловать! Вы успешно авторизовались";
    public static final String AUTH_LOGIN_DENIED = "Логин был введен неправильно, перевожу на главную.";
    public static final String AUTH_PASSWORD_DENIED = "Пароль был введен неправильно, перевожу на главную.";

    public static final String UNKNOWN_ERROR = "Неизвестная ошибка, перевожу на авторизацию.";
}
