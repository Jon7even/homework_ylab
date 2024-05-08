package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения имеющихся адресов эндпоинтов
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerPath {
    public static final String PATH_URL_AUTH = "/auth";
    public static final String PATH_URL_USER = "/user";
    public static final String PATH_URL_ADMIN = "/admin";

    public static final String PATH_URL_SIGN_IN = "/sign-in";
    public static final String PATH_URL_SIGN_UP = "/sign-up";
    public static final String PATH_URL_SIGN_OUT = "/sign-out";
    public static final String PATH_URL_DIARY = "/diary";
    public static final String PATH_URL_TYPE_WORKOUT = "/type/workout";
    public static final String PATH_URL_AUDIT = "/audit";
}
