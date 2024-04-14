package com.github.jon7even.presentation.view.config;

import lombok.experimental.UtilityClass;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialDataInDb.DEFAULT_GROUP_PERMISSIONS_USER;

/**
 * Утилитарный класс для глобальных переменных, использующихся в View
 *
 * @author Jon7even
 * @version 1.0
 */
@UtilityClass
public class Settings {
    public static boolean RUN_APP = true;

    public static final Integer DEFAULT_ID_FOR_GROUP = DEFAULT_GROUP_PERMISSIONS_USER;
}
