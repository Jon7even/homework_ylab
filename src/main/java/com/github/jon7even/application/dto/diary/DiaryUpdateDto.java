package com.github.jon7even.application.dto.diary;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс DTO для обновления данных
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
