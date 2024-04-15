package com.github.jon7even.application.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Класс DTO для создания дневника пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryCreateDto {
    @NonNull
    private Long userId;
    @NonNull
    private Float weightUser;
    @NonNull
    private Float growthUser;
}
