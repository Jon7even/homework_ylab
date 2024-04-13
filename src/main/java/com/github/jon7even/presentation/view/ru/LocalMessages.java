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
    public static final String UNKNOWN_ERROR_EXCEPTION = "Неизвестная ошибка, перевожу на форму авторизации.";

    public static final String AUTH_LOGIN_MESSAGE = "Пожалуйста введите существующий логин";
    public static final String AUTH_PASSWORD_MESSAGE = "Пожалуйста введите пароль";
    public static final String AUTH_SUCCESS = "Добро пожаловать! Вы успешно авторизовались";
    public static final String AUTH_LOGIN_DENIED_EXCEPTION = "Логин был введен неправильно, перевожу на главную.";
    public static final String AUTH_PASSWORD_DENIED_EXCEPTION = "Пароль был введен неправильно, перевожу на главную.";


    public static final String REGISTER_NEW_USER_MESSAGE = "Отлично! Давайте начнем регистрацию нового пользователя";
    public static final String REGISTER_NEW_LOGIN_MESSAGE = "Пожалуйста введите новый логин";
    public static final String REGISTER_NEW_PASSWORD_MESSAGE = "Пожалуйста введите новый пароль";
    public static final String REGISTER_NEW_USER_SUCCESS = "Поздравляем! Создан новый пользователь с логином: ";
    public static final String REGISTER_NEXT_ITERATOR = "Переводим на форму авторизации...";
    public static final String REGISTER_BAD_LOGIN_EXCEPTION = "Запрещено использовать зарезервированный системой логин";
}
