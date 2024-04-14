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
    public static final String START_MENU = "Начните работу с приложением. Выберите пункт меню: \n"
            + "1.Авторизация\n"
            + "2.Регистрация\n"
            + "0.Завершить работу с приложением\n";

    public static final String MAIN_MENU = "Вы в главном меню приложения. Выберите пункт меню: \n"
            + "1.Мой дневник\n"
            + "2.Мои тренировки\n"
            + "3.Мой аккаунт\n"
            + "4.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";

    public static final String MAIN_USER = "Вы в главном меню своего аккаунта. Выберите пункт меню: \n"
            + "1.Посмотреть журнал моих действий\n"
            + "2.На главную меню приложения\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";

    public static final String VIEWING_MY_HISTORY = "Вы просмотрели свои действия, что дальше? Выберите пункт меню: \n"
            + "1.Назад в главное меню пользователя\n"
            + "2.На главную меню приложения\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";

    public static final String MAIN_DIARY = "Вы в главном меню своего дневника. Выберите пункт меню: \n"
            + "1.Создать дневник\n"
            + "2.Просмотреть информацию о дневнике\n"
            + "3.На главную меню приложения\n"
            + "4.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";

    public static final String MAIN_WORKOUT = "Вы в главном меню своих тренировок. Выберите пункт меню: \n"
            + "1.Записать тренировку\n"
            + "2.Изменить тренировку\n"
            + "3.Удалить тренировку\n"
            + "4.Просмотреть тренировки\n"
            + "5.Просмотреть статистику по тренировкам\n"
            + "6.На главную меню приложения\n"
            + "7.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String EXIT_MESSAGE = "Запускаю команду выключения приложения";
    public static final String SIGN_OUT = "Выходим из вашего аккаунта...";
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
