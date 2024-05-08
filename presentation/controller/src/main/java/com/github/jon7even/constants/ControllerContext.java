package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения общих констант имеющихся бинов сервисов
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerContext {
    public static final String USER_SERVICE = "userService";
    public static final String AUTH_SERVICE = "authService";
    public static final String DIARY_SERVICE = "diaryService";
    public static final String PERMISSIONS_SERVICE = "accessService";
    public static final String TYPE_WORKOUT_SERVICE = "typeWorkoutService";
    public static final String AUDIT_SERVICE = "auditService";
}
