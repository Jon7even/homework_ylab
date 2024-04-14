package com.github.jon7even.application.services;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByUserDto;

import java.util.List;

/**
 * Интерфейс логики над историей действий пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public interface HistoryUserService {
    /**
     * Метод, который регистрирует новое действие пользователя
     *
     * @param historyUserCreateDto заполненный объект DTO
     */
    void createHistoryOfUser(HistoryUserCreateDto historyUserCreateDto);

    /**
     * Метод, который получает свою историю действий пользователя в коротком варианте представления
     *
     * @param userId      это ID существующего пользователя по которому требуется произвести проверку
     * @return Список всех действий отсортированных по дате
     */
    List<HistoryUserResponseByUserDto> findAllHistoryByOwnerIdSortByDeskDate(Long userId);

    /**
     * Метод, который получает историю действий пользователя в полном варианте представления. Сработает только
     * для тех пользователей, у кого выставлен флаг WRITE=true сервиса HistoryUser
     *
     * @param userId      это ID существующего пользователя по которому требуется произвести проверку
     * @param requesterId это ID существующего пользователя кто выполняет запрос к методу
     * @return Список всех действий отсортированных по дате
     */
    List<HistoryUserResponseByAdminDto> findAllHistoryByAdminIdSortByDeskDate(Long userId, Long requesterId);
}
