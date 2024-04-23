package com.github.jon7even.core.domain.v1.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс DTO для краткого предоставления данных о дневнике пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryResponseDto {
    private Float weightUser;
    private Float growthUser;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
