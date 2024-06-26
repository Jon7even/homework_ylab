package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения общих констант типов контента для сервлетов
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerContent {
    public static final String DEFAULT_CONTENT_JSON = "application/json";
    public static final String DEFAULT_ENCODING = "UTF-8";
}
