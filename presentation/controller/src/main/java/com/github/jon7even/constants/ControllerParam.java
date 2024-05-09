package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения постоянных параметров запроса
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerParam {
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_DIARY_ID = "diaryId";
    public static final String PARAM_TYPE_WORKOUT_ID = "typeWorkoutId";
    public static final String PARAM_REQUESTER_ID = "requesterId";
    public static final String PARAM_WORKOUT_ID = "workoutId";
}
