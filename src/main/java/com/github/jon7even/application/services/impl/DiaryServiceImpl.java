package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.diary.DiaryCreateDto;
import com.github.jon7even.application.dto.diary.DiaryResponseDto;
import com.github.jon7even.application.dto.diary.DiaryUpdateDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.NotUpdatedException;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapper;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.DiaryRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Реализация сервиса дневника
 *
 * @author Jon7even
 * @version 1.0
 */
public class DiaryServiceImpl implements DiaryService {
    private static DiaryServiceImpl instance;
    private final UserDao userRepository;
    private final DiaryDao diaryRepository;
    private final DiaryMapper diaryMapper;

    public static DiaryServiceImpl getInstance() {
        if (instance == null) {
            instance = new DiaryServiceImpl();
        }
        return instance;
    }

    private DiaryServiceImpl() {
        userRepository = UserRepository.getInstance();
        diaryRepository = DiaryRepository.getInstance();
        diaryMapper = new DiaryMapperImpl();
    }

    @Override
    public DiaryResponseDto createDiary(DiaryCreateDto diaryCreateDto) {
        System.out.println("К нам пришел на создание новый дневник: " + diaryCreateDto);

        DiaryEntity diaryForSaveInRepository = diaryMapper.toDiaryEntityFromDtoCreate(
                diaryCreateDto, LocalDateTime.now(), 2
        );
        System.out.println("Дневник для сохранения собран: " + diaryForSaveInRepository);

        Optional<DiaryEntity> createdDiary = diaryRepository.createDiary(diaryForSaveInRepository);
        System.out.println("Новый дневник попытались сохранить в репозитории: " + createdDiary);

        if (createdDiary.isPresent()) {
            System.out.println("Новый дневник успешно сохранен");
            return diaryMapper.toDiaryResponseDtoFromEntity(createdDiary.get());
        } else {
            throw new NotCreatedException("New User");
        }
    }

    @Override
    public DiaryResponseDto findDiaryByDiaryId(Long diaryId) {
        System.out.println("Начинаю получать дневник по diaryId=" + diaryId);
        Optional<DiaryEntity> foundDiaryEntity = diaryRepository.findByDiaryId(diaryId);

        if (foundDiaryEntity.isPresent()) {
            System.out.println("Дневник существует");
            return diaryMapper.toDiaryResponseDtoFromEntity(foundDiaryEntity.get());
        } else {
            System.out.println("Дневник с таким diaryId не найден");
            throw new NotFoundException(String.format("Diary by [diaryId=%s]", diaryId));
        }
    }

    @Override
    public void updateDiary(DiaryUpdateDto diaryUpdateDto) {
        diaryUpdateDto.setUpdatedOn(LocalDateTime.now());
        System.out.println("Начинаем обновлять дневник, вот что нужно обновить: " + diaryUpdateDto);
        DiaryEntity diaryEntityForUpdate = getDiaryEntityByUserId(diaryUpdateDto.getUserId());

        System.out.println("Объединяем данные в сущности");
        diaryMapper.updateDiaryEntityFromDtoUpdate(diaryEntityForUpdate, diaryUpdateDto);

        System.out.println("Сохраняю получившийся дневник: " + diaryEntityForUpdate);
        Optional<DiaryEntity> updatedDiary = diaryRepository.updateDiary(diaryEntityForUpdate);

        if (updatedDiary.isPresent()) {
            System.out.println("Дневник обновлен в БД: " + updatedDiary);
        } else {
            throw new NotUpdatedException(diaryUpdateDto.toString());
        }
    }

    @Override
    public boolean isExistByUserId(Long userId) {
        System.out.println("Проверяю существует ли дневник у пользователя userId=" + userId);
        Optional<DiaryEntity> foundDiaryEntity = diaryRepository.findByUserId(userId);

        if (foundDiaryEntity.isPresent()) {
            System.out.println("Дневник существует");
            return true;
        } else {
            System.out.println("Дневник с таким userId не найден");
            return false;
        }
    }

    @Override
    public DiaryResponseDto getDiaryDtoByUserId(Long userId) {
        DiaryEntity diaryEntityFromRepository = getDiaryEntityByUserId(userId);
        System.out.println("Дневник получен, начинаю маппить и отправлять");
        return diaryMapper.toDiaryResponseDtoFromEntity(diaryEntityFromRepository);
    }

    private DiaryEntity getDiaryEntityByUserId(Long userId) {
        System.out.println("Начинаю получать дневник пользователя с userId=" + userId);
        Optional<DiaryEntity> foundDiaryEntity = diaryRepository.findByUserId(userId);

        if (foundDiaryEntity.isPresent()) {
            System.out.println("Дневник существует");
            return foundDiaryEntity.get();
        } else {
            System.out.println("Дневник пользователя с таким userId не найден");
            throw new NotFoundException(String.format("Diary by [userId=%s]", userId));
        }
    }

}
