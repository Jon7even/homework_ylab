package com.github.jon7even.config;

import com.github.jon7even.core.domain.v1.dao.*;
import com.github.jon7even.dataproviders.configuration.ConfigLoader;
import com.github.jon7even.dataproviders.jdbc.*;
import lombok.Getter;

/**
 * Утилитарный класс-конфигуратор для внедрения репозиториев
 *
 * @author Jon7even
 * @version 1.0
 */
@Getter
public class BeanConfig {
    private static BeanConfig instance;

    public static BeanConfig getInstance() {
        if (instance == null) {
            instance = new BeanConfig();
        }
        return instance;
    }

    private final GroupPermissionsDao groupPermissionsDao;
    private final UserDao userDao;
    private final DiaryDao diaryDao;
    private final TypeWorkoutDao typeWorkoutDao;
    private final WorkoutDao workoutDao;
    private final HistoryUserDao historyUserDao;

    private BeanConfig() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        groupPermissionsDao = new GroupPermissionsJdbcRepository(configLoader.getConfig());
        userDao = new UserJdbcRepository(configLoader.getConfig());
        diaryDao = new DiaryJdbcRepository(configLoader.getConfig());
        typeWorkoutDao = new TypeWorkoutJdbcRepository(configLoader.getConfig());
        workoutDao = new WorkoutJdbcRepository(configLoader.getConfig());
        historyUserDao = new HistoryUserJdbcRepository(configLoader.getConfig());
    }
}
