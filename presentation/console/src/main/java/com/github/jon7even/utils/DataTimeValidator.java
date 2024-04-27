package com.github.jon7even.utils;

import com.github.jon7even.core.domain.v1.exception.IncorrectTimeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.github.jon7even.utils.DateTimeFormat.DATA_TIME_FORMAT;

/**
 * Утилитарный класс валидации входящего времени
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class DataTimeValidator {
    public static LocalDateTime getLocalDateTimeStartAndValidate(LocalDateTime currentTime, String targetTimeInput) {
        LocalDateTime targetTime;
        log.debug("Начинаю валидацию времени Старта тренировки targetTimeInput={}", targetTimeInput);
        try {
            targetTime = LocalDateTime.parse(targetTimeInput, DATA_TIME_FORMAT);
            if (targetTime.isAfter(currentTime)) {
                log.warn("Указали время в будущем");
                throw new IncorrectTimeException(targetTimeInput);
            } else {
                log.debug("Валидация времени прошла targetTime={}", targetTime.format(DATA_TIME_FORMAT));
                return targetTime;
            }
        } catch (Exception e) {
            throw new IncorrectTimeException(targetTimeInput);
        }
    }

    public static LocalDateTime getLocalDateTimeEndStartAndValidate(LocalDateTime startTime, Integer durationInput) {
        log.debug("Начинаю валидацию времени Окончания тренировки, durationInput={}", durationInput);
        LocalDateTime endTime;
        try {
            Duration periodWorkout = Duration.ofMinutes(durationInput);
            log.debug("Продолжительность тренировки получилась periodWorkout={}", periodWorkout);
            endTime = startTime.plus(periodWorkout);
            if (!Objects.equals(startTime.getDayOfYear(), endTime.getDayOfYear()) || periodWorkout.isNegative()) {
                log.warn("Дни тренировки не совпадают или период оказался отрицательным числом!");
                throw new IncorrectTimeException("Duration of workout");
            } else {
                log.debug("Валидация времени прошла endTime=" + endTime);
                return endTime;
            }
        } catch (Exception e) {
            throw new IncorrectTimeException("Duration of workout Value");
        }
    }

    public static Duration getDurationAndValidate(LocalDateTime startTime,
                                                  LocalDateTime endTime,
                                                  Integer durationInput) {
        log.debug("Начинаю валидацию времени отдыха, durationInput={}", durationInput);
        Duration durationOfRest;
        try {
            durationOfRest = Duration.ofMinutes(durationInput);
            long timeOfWorkout = Duration.between(startTime, endTime).toMinutes();
            if (durationOfRest.isNegative() || durationInput > timeOfWorkout) {
                log.warn("Время отдыха больше, чем сама тренировка или число отрицательное");
                throw new IncorrectTimeException("Duration of Rest");
            } else {
                log.debug("Валидация времени отдыха прошла durationOfRest={}", durationOfRest);
                return durationOfRest;
            }
        } catch (Exception e) {
            throw new IncorrectTimeException("Duration of Rest Value");
        }
    }
}
