package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.application.services.GroupPermissionsService;
import com.github.jon7even.application.services.HistoryUserService;
import com.github.jon7even.core.domain.v1.dao.HistoryUserDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.mappers.HistoryUserMapper;
import com.github.jon7even.core.domain.v1.mappers.HistoryUserMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.HistoryUserRepository;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Objects;

import static com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions.READ;
import static com.github.jon7even.infrastructure.dataproviders.inmemory.constants.InitialCommonDataInDb.SERVICE_HISTORY;

/**
 * Реализация сервиса для взаимодействия с историей действий пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class HistoryUserServiceImpl implements HistoryUserService {
    private static HistoryUserServiceImpl instance;
    private final HistoryUserDao historyUserRepository;
    private final HistoryUserMapper historyUserMapper;
    private final UserDao userRepository;
    private final GroupPermissionsService groupPermissionsService;

    public static HistoryUserServiceImpl getInstance() {
        if (instance == null) {
            instance = new HistoryUserServiceImpl();
        }
        return instance;
    }

    private HistoryUserServiceImpl() {
        historyUserRepository = HistoryUserRepository.getInstance();
        userRepository = UserRepository.getInstance();
        historyUserMapper = new HistoryUserMapperImpl();
        groupPermissionsService = GroupPermissionsServiceImpl.getInstance();
    }

    @Override
    public void createHistoryOfUser(HistoryUserCreateDto historyUserCreateDto) {
        System.out.println("К нам пришло новое событие от пользователя: " + historyUserCreateDto);
        Optional<HistoryUserEntity> savedHistory = historyUserRepository.createHistoryOfUser(
                historyUserMapper.toEntityFromHistoryUserCreateDto(historyUserCreateDto, LocalDateTime.now(), 1)
        );

        if (savedHistory.isPresent()) {
            System.out.println("Событие успешно записано");
        } else {
            System.out.println("Событие не записалось, проведите отладку");
        }
    }

    @Override
    public List<HistoryUserResponseByUserDto> findAllHistoryByOwnerIdSortByDeskDate(Long userId) {
        System.out.println("Пользователь requesterId=" + userId + " начинает получать свою историю действий");
        isExistUserById(userId);

        List<HistoryUserEntity> listHistoryByUserId = historyUserRepository.findAllHistoryByUserId(userId);
        System.out.println("Получен список из событий в количестве=" + listHistoryByUserId.size());

        if (listHistoryByUserId.isEmpty()) {
            System.out.println("Возвращаю пустой список");
            return Collections.emptyList();
        } else {
            isOwnerHistory(listHistoryByUserId, userId);
        }

        List<HistoryUserEntity> sortedList = sortListHistoryUser(listHistoryByUserId);
        System.out.println("Начинаю маппить и возвращать");
        return historyUserMapper.toHistoryUserResponseByUserFromEntity(sortedList);
    }

    @Override
    public List<HistoryUserResponseByAdminDto> findAllHistoryByAdminIdSortByDeskDate(Long userId, Long requesterId) {
        System.out.println("Начинаем получать историю действий пользователя с userId=" + userId);
        isExistUserById(requesterId);

        List<HistoryUserEntity> listHistoryByUserId;

        System.out.println("Запрашиваю разрешение на просмотр");
        if (groupPermissionsService.getPermissionsForService(getGroupPermissionsId(requesterId),
                SERVICE_HISTORY.getId(), READ)) {
            listHistoryByUserId = historyUserRepository.findAllHistoryByUserId(userId);
            System.out.println("Получен список из событий в количестве=" + listHistoryByUserId.size());
        } else {
            System.out.println("У вас нет доступа для просмотра истории этого пользователя");
            return Collections.emptyList();
        }

        List<HistoryUserEntity> sortedList = sortListHistoryUser(listHistoryByUserId);
        System.out.println("Начинаю маппить и возвращать");
        return historyUserMapper.toHistoryUserResponseByAdminFromEntity(sortedList);
    }

    private List<HistoryUserEntity> sortListHistoryUser(List<HistoryUserEntity> listNotSorted) {
        System.out.println("Сортируем список");
        List<HistoryUserEntity> sortedList = listNotSorted.stream()
                .sorted(Comparator.comparing(HistoryUserEntity::getDateTimeOn)
                        .reversed())
                .collect(Collectors.toList());
        System.out.println("Список отсортирован: " + sortedList);
        return sortedList;
    }

    private void isExistUserById(Long userId) {
        System.out.println("Проверяю существование запрашиваемого пользователя");
        Optional<UserEntity> requesterFromBd = userRepository.findByUserId(userId);

        if (requesterFromBd.isPresent()) {
            System.out.println("Запрашиваемый пользователь есть");
        } else {
            throw new NotFoundException(String.format("User with [userId=%s]", userId));
        }
    }

    private void isOwnerHistory(List<HistoryUserEntity> listHistoryByUserId, Long userId) {
        System.out.println("Проверяем является ли пользователь владельцем действий");
        if (!Objects.equals(userId, listHistoryByUserId.listIterator().next().getUserId())) {
            throw new AccessDeniedException(String.format("For [requesterId=%d]", userId));
        }
    }

    private Integer getGroupPermissionsId(Long requesterId) {
        System.out.println("Проверяю существование запрашиваемого пользователя requesterId=" + requesterId);
        Optional<UserEntity> requesterFromBd = userRepository.findByUserId(requesterId);

        if (requesterFromBd.isPresent()) {
            System.out.println("Запрашиваемый пользователь есть");
            return requesterFromBd.get().getIdGroupPermissions();
        } else {
            throw new NotFoundException(String.format("User with [requesterId=%s]", requesterId));
        }
    }
}
