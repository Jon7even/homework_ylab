package com.github.jon7even.core.domain.v1.dao;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс DAO для пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserDao {
    /**
     * Метод для создания нового пользователя
     *
     * @param userEntity новый пользователь без ID
     * @return новый Entity пользователь со сгенерированным ID
     */
    Optional<UserEntity> createUser(UserEntity userEntity);

    /**
     * Метод для обновления существующего пользователя
     *
     * @param userEntity существующий пользователь с ID
     * @return обновленный пользователь, если он есть в системе
     */
    Optional<UserEntity> updateUser(UserEntity userEntity);

    /**
     * Метод для поиска пользователя по ID
     *
     * @param userId существующий ID пользователя
     * @return пользователь, если он есть в системе
     */
    Optional<UserEntity> findByUserId(Long userId);

    /**
     * Метод для поиска пользователя по его логину
     *
     * @param userLogin существующий login пользователя
     * @return пользователь, если он есть в системе
     */
    Optional<UserEntity> findByUserLogin(String userLogin);

    /**
     * Метод для поиска всех пользователей без параметров
     *
     * @return весь найденный список Entity всех существующих пользователей без параметров сортировки
     */
    List<UserEntity> getAllUsers();
}
