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
}
