package com.github.jon7even.core.domain.v1.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long userId;
    private String event;
}
