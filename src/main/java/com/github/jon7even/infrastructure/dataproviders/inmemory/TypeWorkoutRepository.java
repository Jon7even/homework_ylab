package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.TypeWorkoutDao;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация репозитория типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class TypeWorkoutRepository implements TypeWorkoutDao {
    private static TypeWorkoutRepository instance;
    private final Map<Long, TypeWorkoutEntity> mapOfTypeWorkouts = new HashMap<>();
    private Long idGenerator = 0L;

    public static TypeWorkoutRepository getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutRepository();
        }
        return instance;
    }

    private TypeWorkoutRepository() {
        TypeWorkoutEntity walking = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Простая ходьба")
                .caloriePerHour(200)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(walking.getId(), walking);
        TypeWorkoutEntity running = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Бег на улице")
                .caloriePerHour(500)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(running.getId(), running);
        TypeWorkoutEntity strengthTraining = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Силовая тренировка")
                .caloriePerHour(520)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(strengthTraining.getId(), strengthTraining);
        TypeWorkoutEntity treadmill = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Бег на беговой дорожке")
                .caloriePerHour(400)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(treadmill.getId(), treadmill);
        TypeWorkoutEntity bicycling = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Велоспорт")
                .caloriePerHour(450)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(bicycling.getId(), bicycling);
        TypeWorkoutEntity yoga = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Занятия йогой")
                .caloriePerHour(225)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(yoga.getId(), yoga);
        TypeWorkoutEntity swimmingPool = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Плавать в бассейне")
                .caloriePerHour(230)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(swimmingPool.getId(), swimmingPool);
        TypeWorkoutEntity tennis = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Игра в теннис")
                .caloriePerHour(400)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(tennis.getId(), tennis);
        TypeWorkoutEntity skiing = TypeWorkoutEntity.builder()
                .id(++idGenerator)
                .typeName("Ходьба на лыжах")
                .caloriePerHour(485)
                .idTypeService(4)
                .build();
        mapOfTypeWorkouts.put(skiing.getId(), skiing);
    }

    @Override
    public Optional<TypeWorkoutEntity> createTypeWorkout(TypeWorkoutEntity typeWorkoutEntity) {
        Long typeWorkoutId = ++idGenerator;
        typeWorkoutEntity.setId(typeWorkoutId);
        mapOfTypeWorkouts.put(typeWorkoutId, typeWorkoutEntity);

        System.out.println("В БД добавлен новый тип тренировки: " + typeWorkoutEntity);
        return findByTypeWorkoutId(typeWorkoutId);
    }

    @Override
    public Optional<TypeWorkoutEntity> updateTypeWorkout(TypeWorkoutEntity typeWorkoutEntity) {
        Long typeWorkoutId = typeWorkoutEntity.getId();
        TypeWorkoutEntity oldTypeWorkout;

        if (containsTypeWorkoutById(typeWorkoutId)) {
            oldTypeWorkout = mapOfTypeWorkouts.get(typeWorkoutId);
        } else {
            return Optional.empty();
        }

        mapOfTypeWorkouts.put(typeWorkoutId, typeWorkoutEntity);
        System.out.println("В БД произошло обновление. Старые данные: " + oldTypeWorkout
                + "\n Новые данные: " + typeWorkoutEntity);
        return findByTypeWorkoutId(typeWorkoutId);
    }

    @Override
    public Optional<TypeWorkoutEntity> findByTypeWorkoutId(Long typeWorkoutId) {
        System.out.println("Ищу тип тренировки с typeWorkoutId=" + typeWorkoutId);

        if (containsTypeWorkoutById(typeWorkoutId)) {
            Optional<TypeWorkoutEntity> foundTypeWorkoutEntity = Optional.of(mapOfTypeWorkouts.get(typeWorkoutId));
            System.out.println("Найден тип тренировки: " + foundTypeWorkoutEntity.get());
            return foundTypeWorkoutEntity;
        } else {
            System.out.println("Тип тренировки с таким typeWorkoutId не найден");
            return Optional.empty();
        }
    }

    @Override
    public List<TypeWorkoutEntity> findAllTypeWorkoutsNoSort() {
        System.out.println("Получаем полный список всех типов тренировки без параметров сортировки");
        return mapOfTypeWorkouts.values().stream().toList();
    }

    private Boolean containsTypeWorkoutById(Long typeWorkoutId) {
        System.out.println("Проверяем есть ли ти тренировки с typeWorkoutId=" + typeWorkoutId);
        return mapOfTypeWorkouts.containsKey(typeWorkoutId);
    }
}
