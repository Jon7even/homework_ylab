package com.github.jon7even.infrastructure.dataproviders.inmemory.constants;

import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный файл констант инициализации необходимых данных в БД типов тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitDbTypeWorkout {
    public final static TypeWorkoutEntity WALKING = TypeWorkoutEntity.builder()
            .id(1L)
            .typeName("Простая ходьба")
            .caloriePerHour(200)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity RUNNING = TypeWorkoutEntity.builder()
            .id(2L)
            .typeName("Бег на улице")
            .caloriePerHour(500)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity STRENGTH_TRAINING = TypeWorkoutEntity.builder()
            .id(3L)
            .typeName("Силовая тренировка")
            .caloriePerHour(520)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity TREADMILL = TypeWorkoutEntity.builder()
            .id(4L)
            .typeName("Бег на беговой дорожке")
            .caloriePerHour(400)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity BICYCLING = TypeWorkoutEntity.builder()
            .id(5L)
            .typeName("Велоспорт")
            .caloriePerHour(450)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity YOGA = TypeWorkoutEntity.builder()
            .id(6L)
            .typeName("Занятия йогой")
            .caloriePerHour(225)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity SWIMMING_POOL = TypeWorkoutEntity.builder()
            .id(7L)
            .typeName("Плавать в бассейне")
            .caloriePerHour(230)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity TENNIS = TypeWorkoutEntity.builder()
            .id(8L)
            .typeName("Игра в теннис")
            .caloriePerHour(400)
            .idTypeService(4)
            .build();

    public final static TypeWorkoutEntity SKIING = TypeWorkoutEntity.builder()
            .id(9L)
            .typeName("Ходьба на лыжах")
            .caloriePerHour(485)
            .idTypeService(4)
            .build();
}
