package com.github.jon7even.application.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс DTO для полного предоставления данных о действиях пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryUserResponseByAdminDto {
    private Long id;
    private Long userId;
    private LocalDateTime dateTimeOn;
    private String event;
}
