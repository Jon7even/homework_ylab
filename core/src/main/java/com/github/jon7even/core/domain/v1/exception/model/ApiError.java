package com.github.jon7even.core.domain.v1.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.github.jon7even.core.domain.v1.constant.DataTimePattern.DATE_TIME_DEFAULT;

/**
 * Класс для возвращения ошибок в контроллере
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@RequiredArgsConstructor
public class ApiError {
    private final String reason;

    private final String message;

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private final LocalDateTime timestamp;
}
