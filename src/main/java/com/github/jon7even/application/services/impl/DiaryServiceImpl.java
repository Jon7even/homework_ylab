package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.diary.DiaryCreateDto;
import com.github.jon7even.application.dto.diary.DiaryResponseDto;
import com.github.jon7even.application.dto.diary.DiaryUpdateDto;
import com.github.jon7even.application.services.DiaryService;
import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.NotUpdatedException;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapper;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.DiaryRepository;

import java.time.LocalDateTime;

import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.SERVICE_DIARY;

/**
 * Реализация сервиса для взаимодействия с дневником пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class DiaryServiceImpl implements DiaryService {
    private static DiaryServiceImpl instance;
    private final DiaryDao diaryRepository;
    private final DiaryMapper diaryMapper;

    public static DiaryServiceImpl getInstance() {
        if (instance == null) {
            instance = new DiaryServiceImpl();
        }
        return instance;
    }

    private DiaryServiceImpl() {
        diaryRepository = DiaryRepository.getInstance();
        diaryMapper = new DiaryMapperImpl();
    }

    @Override
    public DiaryResponseDto createDiary(DiaryCreateDto diaryCreateDto) {
        System.out.println("К нам пришел на создание новый дневник: " + diaryCreateDto);

        DiaryEntity diaryForSaveInRepository = diaryMapper.toDiaryEntityFromDtoCreate(
                diaryCreateDto, LocalDateTime.now(), SERVICE_DIARY.getId()
        );
        System.out.println("Дневник для сохранения собран: " + diaryForSaveInRepository);

        DiaryEntity createdDiary = diaryRepository.createDiary(diaryForSaveInRepository)
                .orElseThrow(() -> new NotCreatedException("New Diary"));
        System.out.println("Новый дневник сохранен: " + createdDiary);
        return diaryMapper.toDiaryResponseDtoFromEntity(createdDiary);
    }

    @Override
    public DiaryResponseDto findDiaryByDiaryId(Long diaryId) {
        System.out.println("Начинаю получать дневник по diaryId=" + diaryId);
        DiaryEntity foundDiaryEntity = diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new NotFoundException(String.format("Diary by [diaryId=%s]", diaryId)));
        System.out.println("Дневник существует с diaryId=" + diaryId);
        return diaryMapper.toDiaryResponseDtoFromEntity(foundDiaryEntity);
    }

    @Override
    public void updateDiary(DiaryUpdateDto diaryUpdateDto) {
        diaryUpdateDto.setUpdatedOn(LocalDateTime.now());
        System.out.println("Начинаем обновлять дневник, вот что нужно обновить: " + diaryUpdateDto);
        DiaryEntity diaryEntityForUpdate = getDiaryEntityByUserId(diaryUpdateDto.getUserId());

        System.out.println("Объединяем данные в сущности");
        diaryMapper.updateDiaryEntityFromDtoUpdate(diaryEntityForUpdate, diaryUpdateDto);

        System.out.println("Сохраняю получившийся дневник: " + diaryEntityForUpdate);
        diaryRepository.updateDiary(diaryEntityForUpdate)
                .orElseThrow(() -> new NotUpdatedException(diaryUpdateDto.toString()));
    }

    @Override
    public boolean isExistByUserId(Long userId) {
        System.out.println("Проверяю существует ли дневник у пользователя userId=" + userId);
        return diaryRepository.findByUserId(userId).isPresent();
    }

    @Override
    public DiaryResponseDto getDiaryDtoByUserId(Long userId) {
        DiaryEntity diaryEntityFromRepository = getDiaryEntityByUserId(userId);
        System.out.println("Дневник получен, начинаю маппить и отправлять");
        return diaryMapper.toDiaryResponseDtoFromEntity(diaryEntityFromRepository);
    }

    private DiaryEntity getDiaryEntityByUserId(Long userId) {
        System.out.println("Начинаю получать дневник пользователя с userId=" + userId);
        return diaryRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Diary by [userId=%s]", userId)));
    }
}
