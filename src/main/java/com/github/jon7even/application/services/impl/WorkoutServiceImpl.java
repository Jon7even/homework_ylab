package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dao.WorkoutDao;
import com.github.jon7even.infrastructure.dataproviders.inmemory.DiaryRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.WorkoutRepository;

/**
 * Реализация сервиса взаимодействия с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
public class WorkoutServiceImpl implements WorkoutService {
    private static WorkoutServiceImpl instance;
    private final UserDao userRepository;
    private final WorkoutDao workoutRepository;
    private final DiaryDao diaryRepository;

    public static WorkoutServiceImpl getInstance() {
        if (instance == null) {
            instance = new WorkoutServiceImpl();
        }
        return instance;
    }

    private WorkoutServiceImpl() {
        userRepository = UserRepository.getInstance();
        workoutRepository = WorkoutRepository.getInstance();
        diaryRepository = DiaryRepository.getInstance();
    }

    // TODO
}
