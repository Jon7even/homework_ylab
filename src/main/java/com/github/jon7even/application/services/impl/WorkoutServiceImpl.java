package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.workout.WorkoutCreateDto;
import com.github.jon7even.application.dto.workout.WorkoutFullResponseDto;
import com.github.jon7even.application.services.TypeWorkoutService;
import com.github.jon7even.application.services.WorkoutService;
import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.dao.WorkoutDao;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.mappers.WorkoutMapper;
import com.github.jon7even.core.domain.v1.mappers.WorkoutMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.DiaryRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.WorkoutRepository;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.SERVICE_WORKOUT;

/**
 * Реализация сервиса взаимодействия с тренировками
 *
 * @author Jon7even
 * @version 1.0
 */
public class WorkoutServiceImpl implements WorkoutService {
    private static WorkoutServiceImpl instance;
    private final UserDao userRepository;
    private final TypeWorkoutService typeWorkoutService;
    private final WorkoutDao workoutRepository;
    private final DiaryDao diaryRepository;
    private final WorkoutMapper workoutMapper;

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
        typeWorkoutService = TypeWorkoutServiceImpl.getInstance();
        workoutMapper = new WorkoutMapperImpl();
    }

    @Override
    public WorkoutFullResponseDto saveWorkout(WorkoutCreateDto workoutCreateDto) {
        System.out.println("К нам пришла на сохранение новая тренировка: " + workoutCreateDto);

        WorkoutEntity workoutEntityForSaveInRepository = workoutMapper.toWorkoutEntityFromDtoCreate(
                workoutCreateDto, SERVICE_WORKOUT.getId()
        );
        System.out.println("Тренировка для сохранения собрана: " + workoutEntityForSaveInRepository);

        WorkoutEntity createdWorkout = workoutRepository.createWorkout(workoutEntityForSaveInRepository)
                .orElseThrow(() -> new NotCreatedException("New Workout"));
        System.out.println("Новая тренировка сохранена: " + createdWorkout);

        TypeWorkoutResponseDto typeWorkoutResponseDto =
                typeWorkoutService.findTypeWorkoutByTypeWorkoutId(createdWorkout.getIdTypeWorkout());
        return workoutMapper.toDiaryResponseDtoFromEntity(createdWorkout, typeWorkoutResponseDto);
    }
}
