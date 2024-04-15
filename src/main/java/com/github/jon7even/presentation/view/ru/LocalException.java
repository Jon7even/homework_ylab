package com.github.jon7even.presentation.view.ru;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для вывода ошибок
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocalException {
    public static final String INCORRECT_INPUT = "Некорректный ввод команды, пожалуйста введите команду из списка";
}
