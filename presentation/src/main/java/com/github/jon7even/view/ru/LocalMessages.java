package com.github.jon7even.view.ru;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.jon7even.view.config.Settings.*;

/**
 * Утилитарный класс для сообщений в меню
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalMessages {
    public static final String GREET_MESSAGE = GREEN_BOLD
            + """
            |---------------------------------------------------------------|
            |---Рады приветствовать тебя в нашем приложении, дорогой друг!--|
            |---------------Дневник моих тренировок v.1.0-------------------|
            |---------------------------------------------------------------|""" + ANSI_RESET;
    public static final String EXIT_MESSAGE = GREEN_BOLD + "Запускаю команду выключения приложения" + ANSI_RESET;
    public static final String SIGN_OUT = GREEN_BOLD + "Выходим из вашего аккаунта..." + ANSI_RESET;

    public static final String START_MENU = GREEN_BOLD + "Начните работу с приложением. Выберите пункт меню: \n"
            + "1.Авторизация\n"
            + "2.Регистрация\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MAIN_MENU = GREEN_BOLD + "Вы в главном меню приложения. Выберите пункт меню: \n"
            + "1.Мой дневник\n"
            + "2.Мои тренировки\n"
            + "3.Мой аккаунт\n"
            + "4.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MAIN_ADMIN = GREEN_BOLD
            + "Вы в главном меню приложения имеете больше прав. Выберите пункт меню: \n"
            + "1.Меню администратора\n"
            + "2.Мой дневник\n"
            + "3.Мои тренировки\n"
            + "4.Мой аккаунт\n"
            + "5.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MAIN_MENU_USER = GREEN_BOLD
            + "Вы в главном меню своего аккаунта. Выберите пункт меню: \n"
            + "1.Посмотреть журнал моих действий\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String VIEWING_MY_HISTORY = GREEN_BOLD + "Вы просмотрели свои действия, что дальше? Выберите пункт меню: \n"
            + "1.Назад в главное меню пользователя\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MAIN_MENU_DIARY = GREEN_BOLD
            + "Вы в главном меню своего дневника. Выберите пункт меню: \n"
            + "1.Просмотреть информацию о дневнике\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MENU_DIARY_CREATE_STEP1 = GREEN_BOLD + "Самое время создать новый дневник! "
            + "Напишите ваш вес в формате [00.0]кг :" + ANSI_RESET;
    public static final String MENU_DIARY_CREATE_STEP2 = GREEN_BOLD + "Поехали дальше!"
            + "Напишите ваш рост в формате [00.0]см :" + ANSI_RESET;
    public static final String MENU_DIARY_CREATE_HOLD = GREEN_BOLD + "Создаем новый дневник. Ожидайте..." + ANSI_RESET;
    public static final String MENU_DIARY_CREATE_DONE = GREEN_BOLD
            + "Дневник создан! Поздравляем, вы восхитительны." + ANSI_RESET;
    public static final String MENU_DIARY_NOT_FOUND = GREEN_BOLD
            + "Так у вас не создан дневник... Давайте создадим:)" + ANSI_RESET;
    public static final String MENU_DIARY_ALREADY_EXIST_FAILURE = GREEN_BOLD
            + "Опаньки, а как это вы сюда попали?" + ANSI_RESET;
    public static final String MENU_DIARY_VIEW = GREEN_BOLD + "Давайте посмотрим ваш дневник...\n"
            + "-----------------------------------------------------------------------\n"
            + "| Ваш вес составляет %.01fкг | Ваш рост составляет %.01fсм "
            + "| Вы начали пользоваться приложением %s | А занимались в прошлый раз %s |\n"
            + "-----------------------------------------------------------------------\n" + ANSI_RESET;
    public static final String MENU_DIARY_VIEW_END = GREEN_BOLD
            + "С дневником закончили, что дальше? Выберите пункт меню: \n"
            + "1.Вернуться в главное меню дневника\n"
            + "2.Мои тренировки главное меню\n"
            + "3.Вернуться на главное меню\n"
            + "4.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MAIN_MENU_WORKOUT =
            GREEN_BOLD + "Вы в главном меню своих тренировок. Выберите пункт меню: \n"
                    + "1.Записать тренировку\n"
                    + "2.Изменить тренировку\n"
                    + "3.Удалить тренировку\n"
                    + "4.Мои тренировки\n"
                    + "5.Вернуться на главное меню\n"
                    + "6.Выйти из аккаунта\n"
                    + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MAIN_MENU_ADMINISTRATOR = GREEN_BOLD
            + "Вы в главном меню администратора. Выберите пункт меню: \n"
            + "1.Просмотреть историю действий любого пользователя\n"
            + "2.Просмотреть тренировки любого пользователя\n"
            + "3.Добавить тип тренировки\n"
            + "4.Изменить тип тренировки\n"
            + "5.Вернуться на главное меню\n"
            + "6.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MENU_WHAT_NEXT_HOLD = GREEN_BOLD + "Что дальше? Выберите пункт меню: \n"
            + "1.Вернуться на главное меню\n"
            + "2.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MENU_ADMINISTRATOR_VIEWING =
            GREEN_BOLD + "Вы просмотрели %s другого пользователя, "
                    + "что дальше? Выберите пункт меню: \n"
                    + "1.Назад в главное меню администратора\n"
                    + "2.Вернуться на главное меню\n"
                    + "3.Выйти из аккаунта\n"
                    + "0.Завершить работу с приложением\n" + ANSI_RESET;
    public static final String MENU_ADMINISTRATOR_VIEWING_HISTORY_START =
            GREEN_BOLD + "Вы попали в меню просмотра истории действий "
                    + "пользователя, пожалуйста введите ID пользователя, по которому нужно посмотреть события: "
                    + ANSI_RESET;
    public static final String MENU_ADMINISTRATOR_WORKOUT_START =
            GREEN_BOLD + "Вы попали в меню просмотра тренировок "
                    + "пользователя, пожалуйста введите ID пользователя, по которому нужно посмотреть события: "
                    + ANSI_RESET;
    public static final String MENU_ADMINISTRATOR_VIEWING_HOLD = GREEN_BOLD + "Идет поиск... " + ANSI_RESET;
    public static final String AUTH_LOGIN_MESSAGE = GREEN_BOLD + "Пожалуйста введите существующий логин" + ANSI_RESET;
    public static final String AUTH_PASSWORD_MESSAGE = GREEN_BOLD + "Пожалуйста введите пароль" + ANSI_RESET;
    public static final String AUTH_SUCCESS = GREEN_BOLD + "Добро пожаловать! Вы успешно авторизовались" + ANSI_RESET;
    public static final String REGISTER_NEW_USER_MESSAGE = GREEN_BOLD
            + "Отлично! Давайте начнем регистрацию нового пользователя" + ANSI_RESET;
    public static final String REGISTER_NEW_LOGIN_MESSAGE = GREEN_BOLD + "Пожалуйста введите новый логин" + ANSI_RESET;
    public static final String REGISTER_NEW_PASSWORD_MESSAGE = GREEN_BOLD
            + "Пожалуйста введите новый пароль" + ANSI_RESET;
    public static final String REGISTER_NEW_USER_SUCCESS = GREEN_BOLD
            + "Поздравляем! Создан новый пользователь с логином: " + ANSI_RESET;
    public static final String REGISTER_NEXT_ITERATOR = GREEN_BOLD + "Переводим на форму авторизации..." + ANSI_RESET;
    public static final String VIEWING_HISTORY_HEADER_USER = GREEN_BOLD
            + """
            -----------------------------------------------------------------
            |--Дата и время события--|--------------Событие-----------------|
            -----------------------------------------------------------------
            """ + ANSI_RESET;
    public static final String VIEWING_HISTORY_FORM_USER = GREEN_BOLD + "|--%s --|--%s--\n" + ANSI_RESET;
    public static final String VIEWING_HISTORY_HEADER_ADMIN = GREEN_BOLD
            + """     
            |------------------------------------------------------------------------------------------|
            |--------------------------Просмотр логов пользователя с ID=%d------------------------------|
            |------------------------------------------------------------------------------------------|
            |--Дата и время события--|--ID события--|-------------Событие------------------------------|
            |------------------------------------------------------------------------------------------|
            """ + ANSI_RESET;
    public static final String VIEWING_HISTORY_FORM_ADMIN = GREEN_BOLD
            + "|-- %s --|--    %d    --|--%s--\n" + ANSI_RESET;
    public static final String ADD_NEW_TYPE_WORKOUT_MENU = GREEN_BOLD
            + "Вы попали в меню добавления нового типа тренировки." + ANSI_RESET;
    public static final String ADD_NEW_TYPE_WORKOUT_COMPLETE_CREATE = GREEN_BOLD
            + "Отлично! Новый тип тренировки добавлен."
            + " Смотрите, что получилось..." + ANSI_RESET;
    public static final String ADD_NEW_TYPE_WORKOUT_WAIT = GREEN_BOLD
            + "Приступим к созданию нового типа тренировки. "
            + "Для начала взгляните на уже имеющиеся:" + ANSI_RESET;
    public static final String ADD_NEW_TYPE_WORKOUT_GO_NAME = GREEN_BOLD
            + "Если в списке такой тренировки нет, тогда начнем. "
            + "Для начала напишите название типа тренировки:" + ANSI_RESET;
    public static final String ADD_NEW_TYPE_WORKOUT_GO_CALORIE = GREEN_BOLD
            + "Отлично! Теперь посмотрите методические пособия по "
            + "этому типу тренировки и напишите среднее значение количества Калорий, "
            + "которые можно сжечь занимаясь этим спортом 1 час." + ANSI_RESET;
    public static final String ADD_NEW_TYPE_WORKOUT_GO_ID_DETAIL_TYPE = GREEN_BOLD
            + "Выберите как будем детализировать тип тренировки. Выберите ID из списка и напишете его " + ANSI_RESET;
    public static final String UPDATE_TYPE_WORKOUT_MENU = GREEN_BOLD
            + "Вы попали в меню редактирования типа тренировки." + ANSI_RESET;
    public static final String UPDATE_TYPE_WORKOUT_WAIT = GREEN_BOLD
            + "Приступим к редактированию типа тренировки. "
            + "На текущий момент доступны:" + ANSI_RESET;
    public static final String UPDATE_TYPE_WORKOUT_GO_ID_TYPE = GREEN_BOLD
            + "Приступим к редактированию. Введите ID типа, который нужно отредактировать: " + ANSI_RESET;
    public static final String UPDATE_TYPE_WORKOUT_GO_NAME = GREEN_BOLD
            + "Напишите новое имя типа тренировки:" + ANSI_RESET;
    public static final String UPDATE_TYPE_WORKOUT_COMPLETE_UPDATE = GREEN_BOLD
            + "Отлично! Существующий тип тренировки успешно обновлен."
            + " Смотрите, что получилось..." + ANSI_RESET;
    public static final String TYPE_WORKOUT_VIEWING_LIST_DETAILS_HEADER = GREEN_BOLD
            + """     
            |------------------------------------------------------------------------|
            |--------------------Виды детализации типа тренировки--------------------|
            |------------------------------------------------------------------------|
            |--ID типа--|---------------Название детализации-------------------------|
            |------------------------------------------------------------------------|
            """ + ANSI_RESET;
    public static final String TYPE_WORKOUT_VIEWING_LIST_DETAILS_BODY = GREEN_BOLD
            + "|--   %d   --|--    %s    --\n" + ANSI_RESET;
    public static final String TYPE_WORKOUT_VIEWING_LIST_HEADER = GREEN_BOLD
            + """     
            |------------------------------------------------------------------------|
            |------------------------Просмотр типов тренировок-----------------------|
            |------------------------------------------------------------------------|
            |--ID типа--|-------------Название типа тренировки-----------------------|
            |------------------------------------------------------------------------|
            """ + ANSI_RESET;
    public static final String TYPE_WORKOUT_VIEWING_LIST_BODY = GREEN_BOLD
            + "|--   %d   --|--    %s    --\n" + ANSI_RESET;
    public static final String WORKOUT_VIEWING_LIST_HEADER = GREEN_BOLD
            + """     
            |---------------------------------------------------------------------------------------------------------|
            |-----------------------------------Просмотр списка %s тренировок-----------------------------------------|
            |---------------------------------------------------------------------------------------------------------|
            |--ID--|--Тренировка началась--|--Тренировка закончилась--|--Отдыхали--|--Вес на тот момент--|--Заметка---|
            |---------------------------------------------------------------------------------------------------------|
            """ + ANSI_RESET;
    public static final String WORKOUT_VIEWING_LIST_BODY = GREEN_BOLD
            + "|--%d--|  --%s--  |  --%s--  |  --%d минут--  |   --%.01fкг--  |--%s--   \n";
    public static final String TYPE_WORKOUT_VIEWING_TYPE_HEADER = GREEN_BOLD
            + """     
            |--------------------------------------------------------------------------|
            |-----------------------Просмотр типа тренировки---------------------------|
            |--------------------------------------------------------------------------|
            |---ID типа--|--Калории/час--|---------Название типа тренировки------------|
            |--------------------------------------------------------------------------|
            """ + ANSI_RESET;
    public static final String TYPE_WORKOUT_VIEWING_TYPE_BODY = GREEN_BOLD
            + "|--    %d   --|----- %d -----|--    %s    --\n" + ANSI_RESET;
    public static final String WORKOUT_UPDATE_MENU = GREEN_BOLD
            + "Вы попали в меню редактирования своей тренировки! " + ANSI_RESET;
    public static final String WORKOUT_DELETE_MENU = GREEN_BOLD
            + "Вы попали в меню удаления своей тренировки! " + ANSI_RESET;
    public static final String WORKOUT_FIND_MENU = GREEN_BOLD
            + "Вы попали в меню просмотра дополнительной информации о тренировках! " + ANSI_RESET;
    public static final String WORKOUT_UPDATE_PREPARE = GREEN_BOLD
            + "Вот такая была тренировка. Преступим к редактированию." + ANSI_RESET;
    public static final String WORKOUT_DELETE_PREPARE = GREEN_BOLD
            + "Вот такая была тренировка. Преступим к удалению." + ANSI_RESET;
    public static final String WORKOUT_DELETE_COMPLETE = RED_BOLD
            + "Тренировка была успешно удалена!" + ANSI_RESET;
    public static final String WORKOUT_GO_ID = GREEN_BOLD
            + "Выше находится ваш список тренировок, выберите его ID для операции: " + ANSI_RESET;
    public static final String WORKOUT_ADMIN_GO_ID = GREEN_BOLD
            + "Выше список тренировок пользователя с ID=%d, выберите ID для просмотра подробностей: " + ANSI_RESET;
    public static final String WORKOUT_ADD_NEW_MENU = GREEN_BOLD
            + "Вы попали в меню сохранения новой тренировки, так держать! " + ANSI_RESET;
    public static final String WORKOUT_GO_ID_TYPE_WORKOUT = GREEN_BOLD
            + "Давайте выберем ID типа тренировки из списка: " + ANSI_RESET;
    public static final String WORKOUT_GO_TIME_START = GREEN_BOLD
            + "Введите пожалуйста дату и время тренировки в формате 01-01-2024 00:00" + ANSI_RESET;
    public static final String WORKOUT_GO_TIME_END = GREEN_BOLD
            + "Введите пожалуйста количество минут, в течении которых длилась тренировка." + ANSI_RESET;
    public static final String WORKOUT_GO_TIME_REST = GREEN_BOLD
            + "Наверняка во время тренировки вы отдыхали, укажите количество минут отдыха: " + ANSI_RESET;
    public static final String WORKOUT_GO_WEIGHT = GREEN_BOLD
            + "Напишите свой текущий вес в формате в формате [00.0]кг. "
            + "Это обязательно, ведь вы пришли за результатом. А мы поможем отследить ваш прогресс." + ANSI_RESET;
    public static final String WORKOUT_GO_NOTE = GREEN_BOLD
            + "Почти закончили! Напишите свои дополнительные заметки на эту тренировку(например свое состояние)"
            + ANSI_RESET;
    public static final String WORKOUT_GO_DETAIL = GREEN_BOLD
            + "Укажите пожалуйста дополнительный параметр: %s\n" + ANSI_RESET;
    public static final String WORKOUT_ADD_NEW_COMPLETE_CREATE = GREEN_BOLD
            + "Поздравляем! Ваша тренировка сохранена! Давайте посмотрим, что получилось:" + ANSI_RESET;
    public static final String WORKOUT_UPDATE_COMPLETE_UPDATE = GREEN_BOLD
            + "Мы обновили вашу тренировку, посмотрим, что получилось:" + ANSI_RESET;
    public static final String WORKOUT_FULL_VIEWING_FORM = GREEN_BOLD
            + "|------------------------------------------------------------------------------------------|\n"
            + "|-----------------------Ваша тренировка состоялась %s------------------------|\n"
            + "|-----------------------Тип тренировки %s----------------------------------------|\n"
            + "|------------------------------------------------------------------------------------------|\n"
            + "|           ***Вы занимались целых %d минут***                 \n"
            + "|           ***За всю тренировку вы отдохнули %d минут***      \n"
            + "|           ***Ваш вес на тот момент составлял %.01fкг***      \n"
            + "|           ***Сожжено целых %d Калорий***                     \n"
            + "|           ***Детали тренировки: %s: %s***                    \n"
            + "|           ***Ваша заметка: %s***                            \n"
            + "|------------------------------------------------------------------------------------------|\n"
            + ANSI_RESET;
    public static final String WORKOUT_MAIN_MENU_WHAT_NEXT = GREEN_BOLD + "Что дальше? Выберите пункт меню: \n"
            + "1.Вернуться на главное меню тренировок\n"
            + "2.Вернуться на главное меню\n"
            + "3.Выйти из аккаунта\n"
            + "0.Завершить работу с приложением\n" + ANSI_RESET;
}