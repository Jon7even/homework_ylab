package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.WorkoutDao;
import com.github.jon7even.core.domain.v1.entities.workout.WorkoutEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация репозитория тренировки
 *
 * @author Jon7even
 * @version 1.0
 */
public class WorkoutRepository implements WorkoutDao {
    private static WorkoutRepository instance;

    private final Map<Long, WorkoutEntity> mapOfWorkouts = new HashMap<>();

    private Long idGenerator = 0L;

    public static WorkoutRepository getInstance() {
        if (instance == null) {
            instance = new WorkoutRepository();
        }
        return instance;
    }

    private WorkoutRepository() {
    }

    @Override
    public Optional<WorkoutEntity> createWorkout(WorkoutEntity workoutEntity) {
        Long workoutId = ++idGenerator;
        workoutEntity.setId(workoutId);
        mapOfWorkouts.put(workoutId, workoutEntity);

        System.out.println("В БД добавлен новая тренировка: " + workoutEntity);
        return findByWorkoutByTypeIdAndDate(workoutId);
    }

    @Override
    public Optional<WorkoutEntity> updateWorkout(WorkoutEntity workoutEntity) {
        Long workoutId = workoutEntity.getId();
        WorkoutEntity oldWorkout;

        if (containsWorkoutById(workoutId)) {
            oldWorkout = mapOfWorkouts.get(workoutId);
        } else {
            return Optional.empty();
        }

        mapOfWorkouts.put(workoutId, workoutEntity);
        System.out.println("В БД произошло обновление. Старые данные: " + oldWorkout
                + "\n Новые данные: " + workoutEntity);
        return findByWorkoutByTypeIdAndDate(workoutId);
    }

    @Override
    public Optional<WorkoutEntity> findByWorkoutByTypeIdAndDate(Long workoutId) {
        System.out.println("Ищу тренировка с workoutId=" + workoutId);

        if (containsWorkoutById(workoutId)) {
            Optional<WorkoutEntity> foundWorkoutEntity = Optional.of(mapOfWorkouts.get(workoutId));
            System.out.println("Найдена тренировка: " + foundWorkoutEntity.get());
            return foundWorkoutEntity;
        } else {
            System.out.println("Тренировка с таким workoutId не найдена");
            return Optional.empty();
        }
    }

    @Override
    public Optional<WorkoutEntity> findByWorkoutByTypeIdAndDate(Long idTypeWorkout, LocalDate dayOfWorkout) {
        System.out.println("Ищу тренировку с idWorkoutType=" + idTypeWorkout + " и датой=" + dayOfWorkout.toString());

        Optional<WorkoutEntity> foundWorkoutEntity = mapOfWorkouts.values().stream()
                .filter(workoutEntity -> workoutEntity.getIdWorkoutType().equals(idTypeWorkout))
                .filter(workoutEntity -> workoutEntity.getTimeStartOn().toLocalDate().equals(dayOfWorkout))
                .findFirst();

        if (foundWorkoutEntity.isPresent()) {
            System.out.println("Найдена тренировка с заданными параметрами: " + foundWorkoutEntity.get());
            return foundWorkoutEntity;
        } else {
            System.out.println("Тренировка с такими параметрами не найдена");
            return Optional.empty();
        }
    }

    @Override
    public List<WorkoutEntity> findAllWorkoutByDiaryId(Long diaryId) {
        System.out.println("Получаю несортированный список всех тренировок дневника с diaryId=" + diaryId);
        return mapOfWorkouts.values().stream()
                .filter(workoutEntity -> workoutEntity.getIdDiary().equals(diaryId))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutEntity> findAllWorkout() {
        System.out.println("Получаю список всех тренировок без параметров");
        return mapOfWorkouts.values().stream().toList();
    }

    private Boolean containsWorkoutById(Long workoutId) {
        System.out.println("Проверяем есть ли тренировка с workoutId=" + workoutId);
        return mapOfWorkouts.containsKey(workoutId);
    }
}
