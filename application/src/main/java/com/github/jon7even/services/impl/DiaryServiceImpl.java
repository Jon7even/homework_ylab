package com.github.jon7even.services.impl;

import com.github.jon7even.annotations.Loggable;
import com.github.jon7even.core.domain.v1.dao.DiaryDao;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.core.domain.v1.exception.AlreadyExistException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.exception.NotUpdatedException;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapper;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapperImpl;
import com.github.jon7even.services.DiaryService;

import java.time.LocalDateTime;

/**
 * Реализация сервиса для взаимодействия с дневником пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Loggable
public class DiaryServiceImpl implements DiaryService {
    public static final Integer SERVICE_DIARY_ID = 2;
    private final DiaryDao diaryRepository;
    private final DiaryMapper diaryMapper;

    public DiaryServiceImpl(DiaryDao diaryRepository) {
        this.diaryRepository = diaryRepository;
        this.diaryMapper = new DiaryMapperImpl();
    }

    @Override
    public DiaryResponseDto createDiary(DiaryCreateDto diaryCreateDto) {
        System.out.println("К нам пришел на создание новый дневник: " + diaryCreateDto);

        if (diaryRepository.findByUserId(diaryCreateDto.getUserId()).isPresent()) {
            throw new AlreadyExistException(String.format("Diary witch [userId=%d]", diaryCreateDto.getUserId()));
        }

        DiaryEntity diaryForSaveInRepository = diaryMapper.toDiaryEntityFromDtoCreate(
                diaryCreateDto, LocalDateTime.now()
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
        DiaryEntity foundDiaryEntity = getDiaryEntityByDiaryId(diaryId);
        System.out.println("Дневник существует с diaryId=" + diaryId);
        return diaryMapper.toDiaryResponseDtoFromEntity(foundDiaryEntity);
    }

    @Override
    public DiaryResponseDto updateDiary(DiaryUpdateDto diaryUpdateDto) {
        diaryUpdateDto.setUpdatedOn(LocalDateTime.now());
        System.out.println("Начинаем обновлять дневник, вот что нужно обновить: " + diaryUpdateDto);
        DiaryEntity diaryEntityForUpdate = getDiaryEntityByUserId(diaryUpdateDto.getUserId());

        System.out.println("Объединяем данные в сущности");
        diaryMapper.updateDiaryEntityFromDtoUpdate(diaryEntityForUpdate, diaryUpdateDto);

        System.out.println("Сохраняю получившийся дневник: " + diaryEntityForUpdate);
        DiaryEntity updatedDiary = diaryRepository.updateDiary(diaryEntityForUpdate)
                .orElseThrow(() -> new NotUpdatedException(diaryUpdateDto.toString()));
        System.out.println("Дневник обновлен: " + updatedDiary);
        return diaryMapper.toDiaryResponseDtoFromEntity(updatedDiary);
    }

    @Override
    public Boolean isExistByUserId(Long userId) {
        System.out.println("Проверяю существует ли дневник у пользователя userId=" + userId);
        return diaryRepository.findByUserId(userId).isPresent();
    }

    @Override
    public DiaryResponseDto getDiaryDtoByUserId(Long userId) {
        DiaryEntity diaryEntityFromRepository = getDiaryEntityByUserId(userId);
        System.out.println("Дневник получен, начинаю маппить и отправлять");
        return diaryMapper.toDiaryResponseDtoFromEntity(diaryEntityFromRepository);
    }

    @Override
    public Long getIdDiaryByUserId(Long userId) {
        System.out.println("Начинаю получать ID дневника по userId=" + userId);
        return getDiaryEntityByUserId(userId).getId();
    }

    @Override
    public Long getIdUserByDiaryId(Long diaryId) {
        System.out.println("Начинаю получать ID пользователя по diaryId=" + diaryId);
        return getDiaryEntityByDiaryId(diaryId).getId();
    }

    private DiaryEntity getDiaryEntityByUserId(Long userId) {
        System.out.println("Начинаю получать дневник пользователя с userId=" + userId);
        return diaryRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Diary by [userId=%s]", userId)));
    }

    private DiaryEntity getDiaryEntityByDiaryId(Long diaryId) {
        System.out.println("Начинаю получать дневник пользователя с diaryId=" + diaryId);
        return diaryRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new NotFoundException(String.format("Diary by [diaryId=%s]", diaryId)));
    }
}
