package com.github.jon7even.infrastructure.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация репозитория дневника
 *
 * @author Jon7even
 * @version 1.0
 */
public class DiaryRepository implements DiaryDao {
    private static DiaryRepository instance;
    private final Map<Long, DiaryEntity> mapOfDiaries = new HashMap<>();
    private Long idGenerator = 0L;

    public static DiaryRepository getInstance() {
        if (instance == null) {
            instance = new DiaryRepository();
        }
        return instance;
    }

    private DiaryRepository() {
    }

    @Override
    public Optional<DiaryEntity> createDiary(DiaryEntity diaryEntity) {
        Long diaryId = ++idGenerator;
        diaryEntity.setId(diaryId);
        mapOfDiaries.put(diaryId, diaryEntity);

        System.out.println("В БД добавлен новый дневник: " + diaryEntity);
        return findByDiaryId(diaryId);
    }

    @Override
    public Optional<DiaryEntity> updateDiary(DiaryEntity diaryEntity) {
        Long diaryId = diaryEntity.getId();
        DiaryEntity oldDiary;

        if (containDiaryById(diaryId)) {
            oldDiary = mapOfDiaries.get(diaryId);
        } else {
            return Optional.empty();
        }

        mapOfDiaries.put(diaryId, diaryEntity);
        System.out.println("В БД произошло обновление. Старые данные: " + oldDiary + "\n Новые данные: " + diaryEntity);
        return findByDiaryId(diaryId);
    }

    @Override
    public Optional<DiaryEntity> findByDiaryId(Long diaryId) {
        System.out.println("Ищу дневник с diaryId=" + diaryId);

        if (containDiaryById(diaryId)) {
            Optional<DiaryEntity> foundDiaryEntity = Optional.of(mapOfDiaries.get(diaryId));
            System.out.println("Найден дневник: " + foundDiaryEntity.get());
            return foundDiaryEntity;
        } else {
            System.out.println("Дневник с таким diaryId не найден");
            return Optional.empty();
        }
    }

    @Override
    public Optional<DiaryEntity> findByUserId(Long userId) {
        System.out.println("Ищу дневник с userId=" + userId);
        return mapOfDiaries.values().stream()
                .filter(diaryEntity -> diaryEntity.getUserId().equals(userId))
                .findFirst();
    }

    private Boolean containDiaryById(Long diaryId) {
        System.out.println("Проверяем есть ли дневник с diaryId=" + diaryId);
        return mapOfDiaries.containsKey(diaryId);
    }
}
