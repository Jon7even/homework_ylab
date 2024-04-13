package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.TypeWorkoutDao;
import com.github.jon7even.core.domain.v1.entities.TypeWorkoutEntity;

import java.util.HashMap;
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

    private Boolean containsTypeWorkoutById(Long typeWorkoutId) {
        System.out.println("Проверяем есть ли ти тренировки с typeWorkoutId=" + typeWorkoutId);
        return mapOfTypeWorkouts.containsKey(typeWorkoutId);
    }
}
