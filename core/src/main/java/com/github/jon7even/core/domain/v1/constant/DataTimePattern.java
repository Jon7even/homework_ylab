package com.github.jon7even.core.domain.v1.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для работы с парсингом времени
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataTimePattern {
    public static final String DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_IN = "dd-MM-yyyy HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_IN = DateTimeFormatter.ofPattern(DATE_TIME_IN);
}
