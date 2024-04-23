package com.github.jon7even.application.services.impl;

import com.github.jon7even.application.dto.history.HistoryUserCreateDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.application.services.GroupPermissionsService;
import com.github.jon7even.application.services.HistoryUserService;
import com.github.jon7even.configuration.database.ConfigLoader;
import com.github.jon7even.configuration.database.impl.ConfigLoaderImpl;
import com.github.jon7even.core.domain.v1.dao.HistoryUserDao;
import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.core.domain.v1.exception.AccessDeniedException;
import com.github.jon7even.core.domain.v1.exception.NotCreatedException;
import com.github.jon7even.core.domain.v1.exception.NotFoundException;
import com.github.jon7even.core.domain.v1.mappers.HistoryUserMapper;
import com.github.jon7even.core.domain.v1.mappers.HistoryUserMapperImpl;
import com.github.jon7even.infrastructure.dataproviders.inmemory.HistoryUserRepository;
import com.github.jon7even.infrastructure.dataproviders.jdbc.UserJdbcRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        ConfigLoader configLoader = ConfigLoaderImpl.getInstance();
        this.userRepository = new UserJdbcRepository(configLoader.getConfig());
        historyUserMapper = new HistoryUserMapperImpl();
        groupPermissionsService = GroupPermissionsServiceImpl.getInstance();
    }

    @Override
    public void createHistoryOfUser(HistoryUserCreateDto historyUserCreateDto) {
        System.out.println("К нам пришло новое событие от пользователя: " + historyUserCreateDto);
        historyUserRepository.createHistoryOfUser(
                        historyUserMapper.toEntityFromHistoryUserCreateDto(
                                historyUserCreateDto, LocalDateTime.now(), SERVICE_HISTORY.getId()))
                .orElseThrow(() -> new NotCreatedException("History User"));
    }

    @Override
    public List<HistoryUserResponseByUserDto> findAllHistoryByOwnerIdSortByDeskDate(Long userId) {
        System.out.println("Пользователь requesterId=" + userId + " начинает получать свою историю действий");
        isExistUserOrThrowException(userId);

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
        System.out.println("requesterId=" + requesterId
                + "хочет получить историю действий пользователя с userId=" + userId);
        isExistUserOrThrowException(requesterId);
        validationOfPermissions(requesterId, READ);

        List<HistoryUserEntity> listHistoryByUserId = historyUserRepository.findAllHistoryByUserId(userId);
        System.out.println("Получен список из событий в количестве=" + listHistoryByUserId.size());

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

    private boolean isExistUserById(Long userId) {
        System.out.println("Проверяю существование запрашиваемого пользователя");
        return userRepository.findByUserId(userId).isPresent();
    }

    private void isExistUserOrThrowException(Long userId) {
        if (!isExistUserById(userId)) {
            throw new NotFoundException(String.format("User with [userId=%s]", userId));
        }
    }

    private void isOwnerHistory(List<HistoryUserEntity> listHistoryByUserId, Long userId) {
        System.out.println("Проверяем является ли пользователь владельцем действий");
        if (!Objects.equals(userId, listHistoryByUserId.listIterator().next().getUserId())) {
            throw new AccessDeniedException(String.format("For [requesterId=%d]", userId));
        }
    }

    private void validationOfPermissions(Long requesterId, FlagPermissions flagPermissions) {
        System.out.println("Пользователь с requesterId="
                + requesterId + "запрашивает разрешение на операцию: " + flagPermissions);
        if (groupPermissionsService.getPermissionsForService(getGroupPermissionsId(requesterId),
                SERVICE_HISTORY.getId(), flagPermissions)) {
            System.out.println("Разрешение на эту операцию получено.");
        } else {
            System.out.println("У пользователя нет доступа на эту операцию");
            throw new AccessDeniedException(String.format("For [requesterId=%d]", requesterId));
        }
    }

    private Integer getGroupPermissionsId(Long requesterId) {
        System.out.println("Проверяю существование запрашиваемого пользователя requesterId=" + requesterId);
        return userRepository.findByUserId(requesterId)
                .orElseThrow(() -> new NotFoundException(String.format("User with [requesterId=%s]", requesterId)))
                .getIdGroupPermissions();
    }
}
