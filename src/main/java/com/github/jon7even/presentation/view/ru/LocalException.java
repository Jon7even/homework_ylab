package com.github.jon7even.presentation.view.ru;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.jon7even.presentation.view.config.Settings.ANSI_RESET;
import static com.github.jon7even.presentation.view.config.Settings.RED_BOLD;

/**
 * Утилитарный класс для вывода ошибок
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalException {
    public static final String INCORRECT_INPUT = RED_BOLD +
            "Некорректный ввод команды, пожалуйста введите команду из списка" + ANSI_RESET;
    public static final String NOT_FOUND_ID_EXCEPTION = RED_BOLD +
            "Такого ID в системе не существует! Чтобы начать операцию заново, нажмите отличную от меню цифру"
            + ANSI_RESET;
    public static final String ACCESS_DENIED = RED_BOLD + "У вас нет прав эту операцию, "
            + "пожалуйста свяжитесь с нашей командой, если вам нужно предоставить такой доступ" + ANSI_RESET;
    public static final String WORKOUT_WARN_TIME = RED_BOLD + "Обратите внимание, время не должно быть в будущем и "
            + "соответствовать формату ЧИСЛО-МЕСЯЦ-ГОД ЧАС:МИНУТА" + ANSI_RESET;
    public static final String BAD_INPUT_TIME_EXCEPTION = RED_BOLD +
            "Вы совершили ошибку при вводе даты. Попробуйте снова" + ANSI_RESET;
    public static final String BAD_INPUT_EXCEPTION = RED_BOLD +
            "Вы совершили ошибку при вводе. Попробуйте снова" + ANSI_RESET;
    public static final String WORKOUT_WARN_TIME_REST_DURATION = RED_BOLD +
            "Только не увлекитесь! Отдых не должен превышать время самой тренировки:)" + ANSI_RESET;
    public static final String WORKOUT_IS_EMPTY_EXCEPTION = RED_BOLD +
            "У вас еще нет тренировок, внесите хотя бы одну" + ANSI_RESET;
}
