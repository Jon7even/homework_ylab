package com.github.jon7even.application.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Класс DTO для обновления данных дневника пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryUpdateDto {
    @NonNull
    private Long userId;
    private Float weightUser;
    private Float growthUser;
    private LocalDateTime updatedOn;
}
