package com.github.jon7even.services.config;

import com.github.jon7even.core.domain.v1.dao.*;
import com.github.jon7even.dataproviders.configuration.ConfigLoader;
import com.github.jon7even.dataproviders.configuration.MainConfig;
import com.github.jon7even.dataproviders.jdbc.*;

/**
 * Утилитарный класс-конфигуратор для внедрения репозиториев
 *
 * @author Jon7even
 * @version 1.0
 */
public class BeanConfig {
    private static BeanConfig instance;

    public static BeanConfig getInstance() {
        if (instance == null) {
            instance = new BeanConfig();
        }
        return instance;
    }

    private final MainConfig mainConfig;
    private final GroupPermissionsDao groupPermissionsDao;
    private final UserDao userDao;
    private final DiaryDao diaryDao;
    private final TypeWorkoutDao typeWorkoutDao;
    private final WorkoutDao workoutDao;
    private final HistoryUserDao historyUserDao;

    private BeanConfig() {
        mainConfig = ConfigLoader.getInstance().getConfig();
        groupPermissionsDao = new GroupPermissionsJdbcRepository(mainConfig);
        userDao = new UserJdbcRepository(mainConfig);
        diaryDao = new DiaryJdbcRepository(mainConfig);
        typeWorkoutDao = new TypeWorkoutJdbcRepository(mainConfig);
        workoutDao = new WorkoutJdbcRepository(mainConfig);
        historyUserDao = new HistoryUserJdbcRepository(mainConfig);
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public GroupPermissionsDao getGroupPermissionsDao() {
        return groupPermissionsDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public DiaryDao getDiaryDao() {
        return diaryDao;
    }

    public TypeWorkoutDao getTypeWorkoutDao() {
        return typeWorkoutDao;
    }

    public WorkoutDao getWorkoutDao() {
        return workoutDao;
    }

    public HistoryUserDao getHistoryUserDao() {
        return historyUserDao;
    }
}
