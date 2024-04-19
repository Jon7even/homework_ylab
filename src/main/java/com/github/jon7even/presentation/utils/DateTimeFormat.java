package com.github.jon7even.presentation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для формата времени
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeFormat {
    public static final String DATE_TIME_DEFAULT = "dd-MM-yyyy HH:mm";
    public static final DateTimeFormatter DATA_TIME_FORMAT = DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT);
}

