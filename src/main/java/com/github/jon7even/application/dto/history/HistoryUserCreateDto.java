package com.github.jon7even.application.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Класс DTO для сохранения действий пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryUserCreateDto {
    @NonNull
    private Long userId;
    @NonNull
    private String event;
}
