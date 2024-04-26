package com.github.jon7even.dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.dao.HistoryUserDao;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация аудита действий пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public class HistoryUserRepository implements HistoryUserDao {
    private static HistoryUserRepository instance;
    private final Map<Long, HistoryUserEntity> mapOfHistoryByUser = new HashMap<>();
    private Long idGenerator = 0L;

    public static HistoryUserRepository getInstance() {
        if (instance == null) {
            instance = new HistoryUserRepository();
        }
        return instance;
    }

    private HistoryUserRepository() {
    }

    @Override
    public Optional<HistoryUserEntity> createHistoryOfUser(HistoryUserEntity historyUserEntity) {
        Long historyUserId = ++idGenerator;
        historyUserEntity.setId(historyUserId);
        mapOfHistoryByUser.put(historyUserId, historyUserEntity);

        System.out.println("В БД добавлено новое действие пользователя: " + historyUserEntity);
        return findByHistoryUserId(historyUserId);
    }

    @Override
    public List<HistoryUserEntity> findAllHistoryByUserId(Long userId) {
        System.out.println("Получаю несортированный список всей истории пользователя с userId=" + userId);
        return mapOfHistoryByUser.values().stream()
                .filter(historyUserEntity -> historyUserEntity.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private Optional<HistoryUserEntity> findByHistoryUserId(Long historyUserId) {
        System.out.println("Ищу историю действий с historyUserId=" + historyUserId);
        return Optional.ofNullable(mapOfHistoryByUser.get(historyUserId));
    }
}
