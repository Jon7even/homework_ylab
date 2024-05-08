package com.github.jon7even.core.domain.v1.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.github.jon7even.core.domain.v1.constant.DataTimePattern.DATE_TIME_DEFAULT;

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

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private LocalDateTime dateTimeOn;

    private String event;
}
