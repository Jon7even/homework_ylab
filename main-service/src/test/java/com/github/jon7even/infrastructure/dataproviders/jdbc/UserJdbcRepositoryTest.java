package com.github.jon7even.infrastructure.dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserJdbcRepositoryTest extends ContainersSetup {
    private Optional<UserEntity> actualResultUserFirst;

    private Optional<UserEntity> actualResultUserSecond;

    private Long sizeListRepositoryUser = 3L;

    @BeforeEach
    public void setUp() {
        initUsersEntity();
        actualResultUserFirst = userJdbcRepository.createUser(userEntityForCreateFirst);
        actualResultUserSecond = userJdbcRepository.createUser(userEntityForCreateSecond);
    }

    @Test
    @DisplayName("Новый пользователь должен создаться c релевантными полями")
    public void shouldCreateNewUser() {
        assertEquals(userEntityFirst, actualResultUserFirst.get());
        assertEquals(userEntitySecond, actualResultUserSecond.get());
    }

    @Test
    @DisplayName("Пользователь админ должен быть уже в базе при инициализации программы")
    public void shouldFindUserAdminInBD_ReturnUserAdmin() {
        assertEquals(userAdmin.getId(), userJdbcRepository.findByUserId(firstIdLong).get().getId());
    }

    @Test
    @DisplayName("Должен найти пользователя по ID")
    public void shouldFindUserById_ReturnUser() {
        assertEquals(secondIdLong, userJdbcRepository.findByUserId(secondIdLong).get().getId());
        assertEquals(thirdIdLong, userJdbcRepository.findByUserId(thirdIdLong).get().getId());
    }

    @Test
    @DisplayName("Не должен найти пользователя по ID")
    public void shouldNotFindUserById_ReturnUser() {
        Optional<UserEntity> actualResultFirst = userJdbcRepository.findByUserId(9999L);
        Optional<UserEntity> actualResultSecond = userJdbcRepository.findByUserId(-9999L);
        assertEquals(actualResultFirst, Optional.empty());
        assertEquals(actualResultSecond, Optional.empty());
    }

    @Test
    @DisplayName("Должен найти пользователя по логину")
    public void shouldFindUserByLogin_ReturnUser() {
        assertEquals(userEntityFirst, userJdbcRepository.findByUserLogin(userLoginFirst).get());
        assertEquals(userEntitySecond, userJdbcRepository.findByUserLogin(userLoginSecond).get());
    }

    @Test
    @DisplayName("Не должен зарегистрировать пользователя с запрещенным логином")
    public void shouldNotCreateUser_ReturnExceptionBadLogin() {
        userEntityForCreateFirst.setLogin("admin");
        assertThrows(BadLoginException.class, () -> userJdbcRepository.createUser(
                userEntityForCreateFirst
        ));
        userEntityForCreateSecond.setLogin("administrator");
        assertThrows(BadLoginException.class, () -> userJdbcRepository.createUser(
                userEntityForCreateFirst
        ));

        userEntityForCreateFirst.setLogin(userLoginFirst);
        userEntityForCreateSecond.setLogin(userLoginSecond);
    }

    @Test
    @DisplayName("Получить всех пользователей")
    public void shouldFindAllUsers_ReturnAllUsers() {
        assertEquals(userJdbcRepository.getAllUsers().size(), sizeListRepositoryUser);
    }

    @Test
    @DisplayName("Должен обновить пользователя")
    public void shouldUpdateUser_ReturnUpdatedUses() {
        Optional<UserEntity> updatedUserFirst = userJdbcRepository.updateUser(userEntityForUpdateFirst);
        Optional<UserEntity> updatedUserSecond = userJdbcRepository.updateUser(userEntityForUpdateSecond);

        assertFalse(updatedUserFirst.isPresent());
        assertFalse(updatedUserSecond.isPresent());
        assertEquals(
                userEntityForUpdateFirst, userJdbcRepository.findByUserId(userEntityForUpdateFirst.getId()).get()
        );
        assertEquals(
                userEntityForUpdateSecond, userJdbcRepository.findByUserId(userEntityForUpdateSecond.getId()).get()
        );

        assertNotEquals(actualResultUserFirst, userJdbcRepository.findByUserId(userEntityForUpdateFirst.getId()));
        assertNotEquals(actualResultUserSecond, userJdbcRepository.findByUserId(userEntityForUpdateSecond.getId()));
    }
}
