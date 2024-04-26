package com.github.jon7even.core.domain.v1.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long userId;
    private Float weightUser;
    private Float growthUser;
}
