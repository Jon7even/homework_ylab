package com.github.jon7even.core.domain.v1.dto.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.github.jon7even.core.domain.v1.constant.DataTimePattern.DATE_TIME_IN;

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
    private Long userId;

    private Float weightUser;

    private Float growthUser;

    @JsonFormat(pattern = DATE_TIME_IN)
    private LocalDateTime updatedOn;
}
