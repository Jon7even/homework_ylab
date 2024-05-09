package com.github.jon7even.validator;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.github.jon7even.validator.constants.NameOfFieldsForValidation.*;

/**
 * Утилитарный класс валидации входящего времени
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataTimeValidator {
    public static void validationTimeOfStart(LocalDateTime currentTime, LocalDateTime startTime) {
        if (startTime.isAfter(currentTime)) {
            throw new MethodArgumentNotValidException(TIME_START, "не может быть в будущем");
        }
    }

    public static void validationTimeOfEnd(LocalDateTime startTime, LocalDateTime endTime) {
        Duration endTimeForValidation = Duration.between(startTime, endTime);
        if (endTimeForValidation.isNegative()) {
            throw new MethodArgumentNotValidException(TIME_END, "не может быть раньше начала тренировки");
        }

        if (!Objects.equals(startTime.getDayOfYear(), endTime.getDayOfYear())) {
            throw new MethodArgumentNotValidException(TIME_END, "не может быть указано следующим днем");
        }
    }

    public static void validationTimeOfRest(LocalDateTime startTime, LocalDateTime endTime, Long durationOfRest) {
        long timeOfWorkout = Duration.between(startTime, endTime).toMinutes();
        if (durationOfRest > timeOfWorkout) {
            throw new MethodArgumentNotValidException(TIME_OF_REST, "не может быть больше чем сама тренировка");
        }
    }
}
