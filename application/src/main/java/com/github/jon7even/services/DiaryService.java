package com.github.jon7even.services;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryCreateDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.dto.diary.DiaryUpdateDto;

/**
 * Интерфейс для взаимодействия с дневником пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public interface DiaryService {
    /**
     * Метод, который добавляет новый дневник пользователя
     *
     * @param diaryCreateDto заполненный объект DTO
     * @return короткая форма ответа DiaryResponseDto
     */
    DiaryResponseDto createDiary(DiaryCreateDto diaryCreateDto);

    /**
     * Метод, который получает DTO дневника пользователя
     *
     * @param diaryId существующий ID дневника
     * @return объект DTO DiaryResponseDto
     */
    DiaryResponseDto findDiaryByDiaryId(Long diaryId);

    /**
     * Метод, который обновляет дневник пользователя
     *
     * @param diaryUpdateDto заполненный объект DTO
     */
    DiaryResponseDto updateDiary(DiaryUpdateDto diaryUpdateDto);

    /**
     * Метод, который даёт однозначный ответ есть ли дневник у пользователя
     *
     * @param userId существующий ID пользователя
     * @return boolean с ответом - есть ли дневник
     */
    Boolean isExistByUserId(Long userId);

    /**
     * Метод, который получает дневник по ID пользователя
     *
     * @param userId существующий ID пользователя
     * @return объект DTO DiaryResponseDto
     */
    DiaryResponseDto getDiaryDtoByUserId(Long userId);

    /**
     * Метод, который получает ID дневника по ID пользователя
     *
     * @param userId существующий ID пользователя
     * @return ID дневника
     */
    Long getIdDiaryByUserId(Long userId);

    /**
     * Метод, который получает ID пользователя по ID дневника
     *
     * @param diaryId существующий ID дневника
     * @return ID пользователя, если он есть в системе
     */
    Long getIdUserByDiaryId(Long diaryId);
}
