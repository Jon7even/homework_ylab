package com.github.jon7even.view.config;

import com.github.jon7even.dataproviders.inmemory.constants.InitialCommonDataInDb;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для глобальных переменных, использующихся в View
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Settings {
    public static boolean RUN_APP = true;
    public static final String NAME_WITHOUT_DETAILS = "Без деталей";
    public static final Integer DEFAULT_ID_FOR_GROUP = InitialCommonDataInDb.DEFAULT_ID_GROUP_PERMISSIONS_USER;
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String ANSI_RESET = "\u001B[0m";
}
