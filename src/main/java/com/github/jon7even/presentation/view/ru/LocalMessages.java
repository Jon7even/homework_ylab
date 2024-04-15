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
    public static final String EXIT_MESSAGE = "Запускаю команду выключения приложения";
    public static final String SIGN_OUT = "Выходим из вашего аккаунта...";
    public static final String UNKNOWN_ERROR_EXCEPTION = "Неизвестная ошибка, перевожу на форму авторизации.";
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
    public static final String MAIN_ADMIN = "Вы в главном меню приложения имеете больше прав. Выберите пункт меню: \n"
            + "1.Меню администратора\n"
            + "2.Мой дневник\n"
            + "3.Мои тренировки\n"
            + "4.Мой аккаунт\n"
            + "5.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String MAIN_MENU_USER = "Вы в главном меню своего аккаунта. Выберите пункт меню: \n"
            + "1.Посмотреть журнал моих действий\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String VIEWING_MY_HISTORY = "Вы просмотрели свои действия, что дальше? Выберите пункт меню: \n"
            + "1.Назад в главное меню пользователя\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String MAIN_MENU_DIARY = "Вы в главном меню своего дневника. Выберите пункт меню: \n"
            + "1.Создать дневник\n"
            + "2.Просмотреть информацию о дневнике\n"
            + "3.Вернуться на главное меню\n"
            + "4.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String MAIN_MENU_WORKOUT = "Вы в главном меню своих тренировок. Выберите пункт меню: \n"
            + "1.Записать тренировку\n"
            + "2.Изменить тренировку\n"
            + "3.Удалить тренировку\n"
            + "4.Просмотреть тренировки\n"
            + "5.Просмотреть статистику по тренировкам\n"
            + "6.Вернуться на главное меню\n"
            + "7.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String MAIN_MENU_ADMINISTRATOR = "Вы в главном меню администратора. Выберите пункт меню: \n"
            + "1.Просмотреть историю действий любого пользователя\n"
            + "2.Добавить тип тренировки\n"
            + "3.Вернуться на главное меню\n"
            + "4.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String MENU_ADMINISTRATOR_COMPLETE_ADD_WORKOUT = "Что дальше? Выберите пункт меню: \n"
            + "1.Вернуться на главное меню\n"
            + "2.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";
    public static final String MENU_ADMINISTRATOR_VIEWING_HISTORY = "Вы просмотрели действия другого пользователя, "
            + "что дальше? Выберите пункт меню: \n"
            + "1.Назад в главное меню администратора\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n";

    public static final String MENU_ADMINISTRATOR_VIEWING_HISTORY_START = "Вы попали в меню просмотра истории действий "
            + "пользователя, пожалуйста введите ID пользователя, по которому нужно посмотреть события: ";
    public static final String MENU_ADMINISTRATOR_VIEWING_HISTORY_HOLD = "Идет поиск... ";

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

    public static final String VIEWING_HISTORY_HEADER_USER =
            """
                    -----------------------------------------------------------------
                    |--Дата и время события--|--------------Событие-----------------|
                    -----------------------------------------------------------------
                    """;
    public static final String VIEWING_HISTORY_FORM_USER = "|--%s --|--%s--\n";
    public static final String VIEWING_HISTORY_HEADER_ADMIN =
            """     
                    |------------------------------------------------------------------------------------------|
                    |--------------------------Просмотр логов пользователя с ID=%d------------------------------|
                    |------------------------------------------------------------------------------------------|
                    |--Дата и время события--|--ID события--|-------------Событие------------------------------|
                    |------------------------------------------------------------------------------------------|
                    """;
    public static final String VIEWING_HISTORY_FORM_ADMIN = "|-- %s --|--    %d    --|--%s--\n";

    public static final String ADD_NEW_TYPE_WORKOUT_MENU = "Вы попали в меню добавления нового типа тренировки.";

}
