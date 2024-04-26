package com.github.jon7even.core.domain.v1.entities.history;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс описывающий сущность аудита действий пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
public class HistoryUserEntity {
    private Long id;
    private Long userId;
    private LocalDateTime dateTimeOn;
    private String event;
}
