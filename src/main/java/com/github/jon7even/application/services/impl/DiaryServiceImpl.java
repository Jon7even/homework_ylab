package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.infrastructure.dataproviders.inmemory.DiaryRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;

/**
 * Реализация сервиса дневника
 *
 * @author Jon7even
 * @version 1.0
 */
public class DiaryServiceImpl implements WorkoutService {
    private static DiaryServiceImpl instance;
    private final UserDao userRepository;
    private final DiaryDao diaryRepository;

    public static DiaryServiceImpl getInstance() {
        if (instance == null) {
            instance = new DiaryServiceImpl();
        }
        return instance;
    }

    private DiaryServiceImpl() {
        userRepository = UserRepository.getInstance();
        diaryRepository = DiaryRepository.getInstance();
    }
}
