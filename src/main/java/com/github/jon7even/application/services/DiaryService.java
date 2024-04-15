package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.diary.DiaryCreateDto;
import com.github.jon7even.application.dto.diary.DiaryResponseDto;
import com.github.jon7even.application.dto.diary.DiaryUpdateDto;

/**
 * Интерфейс дневника для пользователей
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
    void updateDiary(DiaryUpdateDto diaryUpdateDto);

    /**
     * Метод, который даёт однозначный ответ есть ли дневник у пользователя
     *
     * @param userId существующий ID пользователя
     * @return boolean с ответом - есть ли дневник
     */
    boolean isExistByUserId(Long userId);

    /**
     * Метод, который получает дневник по ID пользователя
     *
     * @param userId существующий ID пользователя
     * @return объект DTO DiaryResponseDto
     */
    DiaryResponseDto getDiaryDtoByUserId(Long userId);
}
