package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.TypeWorkoutDao;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitDbTypeWorkout.*;

/**
 * Реализация репозитория типа тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class TypeWorkoutRepository implements TypeWorkoutDao {
    private static TypeWorkoutRepository instance;
    private final Map<Long, TypeWorkoutEntity> mapOfTypeWorkouts = new HashMap<>();
    private final Map<Integer, DetailOfTypeWorkoutEntity> mapOfDetailOfTypeWorkouts = new HashMap<>();
    private Long idGenerator = 0L;

    public static TypeWorkoutRepository getInstance() {
        if (instance == null) {
            instance = new TypeWorkoutRepository();
        }
        return instance;
    }

    private TypeWorkoutRepository() {
        mapOfTypeWorkouts.put(++idGenerator, WALKING);
        mapOfTypeWorkouts.put(++idGenerator, RUNNING);
        mapOfTypeWorkouts.put(++idGenerator, STRENGTH_TRAINING);
        mapOfTypeWorkouts.put(++idGenerator, TREADMILL);
        mapOfTypeWorkouts.put(++idGenerator, BICYCLING);
        mapOfTypeWorkouts.put(++idGenerator, YOGA);
        mapOfTypeWorkouts.put(++idGenerator, SWIMMING_POOL);
        mapOfTypeWorkouts.put(++idGenerator, TENNIS);
        mapOfTypeWorkouts.put(++idGenerator, SKIING);
        mapOfDetailOfTypeWorkouts.put(DETAIL_OF_DISTANCE_TRAVELED.getId(), DETAIL_OF_DISTANCE_TRAVELED);
        mapOfDetailOfTypeWorkouts.put(DETAIL_OF_EXERCISES_PERFORMED.getId(), DETAIL_OF_EXERCISES_PERFORMED);
        mapOfDetailOfTypeWorkouts.put(DETAIL_OF_NOT_DETAILS.getId(), DETAIL_OF_NOT_DETAILS);
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
        return Optional.ofNullable(mapOfTypeWorkouts.get(typeWorkoutId));
    }

    @Override
    public List<TypeWorkoutEntity> findAllTypeWorkoutsNoSort() {
        System.out.println("Получаем весь список всех типов тренировки без параметров сортировки");
        return mapOfTypeWorkouts.values().stream().toList();
    }

    @Override
    public Optional<DetailOfTypeWorkoutEntity> findDetailOfTypeWorkout(Integer detailOfTypeId) {
        System.out.println("Получаем тип деталей по detailOfTypeId=" + detailOfTypeId);
        return Optional.ofNullable(mapOfDetailOfTypeWorkouts.get(detailOfTypeId));
    }

    @Override
    public List<DetailOfTypeWorkoutEntity> findAllDetailOfTypeWorkoutNoSort() {
        System.out.println("Получаем весь список деталей для типа тренировки без параметров сортировки");
        return mapOfDetailOfTypeWorkouts.values().stream().toList();
    }

    private Boolean containsTypeWorkoutById(Long typeWorkoutId) {
        System.out.println("Проверяем есть ли ти тренировки с typeWorkoutId=" + typeWorkoutId);
        return mapOfTypeWorkouts.containsKey(typeWorkoutId);
    }
}
