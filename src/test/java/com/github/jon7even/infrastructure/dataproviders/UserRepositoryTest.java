package com.github.jon7even.infrastructure.dataproviders;

import com.github.jon7even.core.domain.v1.dao.UserDao;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import com.github.jon7even.infrastructure.dataproviders.inmemory.UserRepository;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest extends PreparationForTests {
    @InjectMocks
    private UserRepository userRepository;

    private Optional<UserEntity> actualResultUserFirst;

    private Optional<UserEntity> actualResultUserSecond;

    private Long sizeListRepositoryUser = 3L;

    @BeforeEach
    public void setUp() {
        initUsers();
        actualResultUserFirst = userRepository.createUser(userEntityFirstForCreate);
        actualResultUserSecond = userRepository.createUser(userEntitySecondForCreate);
    }

    @Test
    @DisplayName("Новый пользователь должен создаться c релевантными полями")
    public void shouldCreateNewUser() {
        assertEquals(userEntityFirstExpected, actualResultUserFirst.get());
        assertEquals(userEntitySecondExpected, actualResultUserSecond.get());
    }

    @Test
    @DisplayName("Пользователь админ должен быть уже в базе при инициализации программы")
    public void shouldFindUserAdminInBD_ReturnUserAdmin() {
        assertEquals(userAdmin.getId(), userRepository.findByUserId(firstIdLong).get().getId());
    }

    @Test
    @DisplayName("Должен найти пользователя по ID")
    public void shouldFindUserById_ReturnUser() {
        assertEquals(secondIdLong, userRepository.findByUserId(secondIdLong).get().getId());
        assertEquals(thirdIdLong, userRepository.findByUserId(thirdIdLong).get().getId());
    }

    @Test
    @DisplayName("Не должен найти пользователя по ID")
    public void shouldNotFindUserById_ReturnUser() {
        Optional<UserEntity> actualResultFirst = userRepository.findByUserId(9999L);
        Optional<UserEntity> actualResultSecond = userRepository.findByUserId(-9999L);
        assertEquals(actualResultFirst, Optional.empty());
        assertEquals(actualResultSecond, Optional.empty());
    }

    @Test
    @DisplayName("Должен найти пользователя по логину")
    public void shouldFindUserByLogin_ReturnUser() {
        assertEquals(userEntityFirstExpected, userRepository.findByUserLogin(userLoginFirst).get());
        assertEquals(userEntitySecondExpected, userRepository.findByUserLogin(userLoginSecond).get());
    }

    @Test
    @DisplayName("Не должен зарегистрировать пользователя с запрещенным логином")
    public void shouldNotCreateUser_ReturnExceptionBadLogin() {
        userEntityFirstForCreate.setLogin("admin");
        assertThrows(BadLoginException.class, () -> userRepository.createUser(
                userEntityFirstForCreate
        ));
        userEntitySecondForCreate.setLogin("administrator");
        assertThrows(BadLoginException.class, () -> userRepository.createUser(
                userEntityFirstForCreate
        ));

        userEntityFirstForCreate.setLogin(userLoginFirst);
        userEntitySecondForCreate.setLogin(userLoginSecond);
    }

    @Test
    @DisplayName("Получить всех пользователей")
    public void shouldFindAllUsers_ReturnAllUsers() {
        assertEquals(userRepository.getAllUsers().size(), sizeListRepositoryUser);
    }
}
