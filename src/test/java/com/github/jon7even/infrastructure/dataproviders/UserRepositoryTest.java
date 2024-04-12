package com.github.jon7even.infrastructure.dataproviders;

import com.github.jon7even.core.domain.v1.entities.UserEntity;
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

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest extends PreparationForTests {
    @InjectMocks
    private UserRepository userRepository;

    private Optional<UserEntity> actualResultUserFirst;

    private Optional<UserEntity> actualResultUserSecond;

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
    @DisplayName("Пользователь админ должен быть уже в базе")
    public void shouldFindUserAdminInBD() {
        assertEquals(userAdmin.getId(), userRepository.findByUserId(firstIdLong).get().getId());
    }

    @Test
    @DisplayName("Поиск пользователя по ID")
    public void shouldFindUserById() {
        assertEquals(secondIdLong, userRepository.findByUserId(secondIdLong).get().getId());
        assertEquals(thirdIdLong, userRepository.findByUserId(thirdIdLong).get().getId());
    }

    @Test
    @DisplayName("Поиск пользователя по логину")
    public void shouldFindUserByLogin() {
        assertEquals(userEntityFirstExpected, userRepository.findByUserLogin(userLoginFirst).get());
        assertEquals(userEntitySecondExpected, userRepository.findByUserLogin(userLoginSecond).get());
    }

    @Test
    @DisplayName("Не должен зарегистрировать пользователя с запрещенным логином")
    public void shouldNotCreateUser() {
        userEntityFirstForCreate.setLogin("admin");
        Optional<UserEntity> actualResultFirst = userRepository.createUser(userEntityFirstForCreate);
        userEntitySecondForCreate.setLogin("administrator");
        Optional<UserEntity> actualResultSecond = userRepository.createUser(userEntitySecondForCreate);
        assertEquals(actualResultFirst, Optional.empty());
        assertEquals(actualResultSecond, Optional.empty());
    }

    @Test
    @DisplayName("Получить всех пользователей")
    public void shouldFindAllUsers() {
        assertEquals(userRepository.getAllUsers().size(), 3);
    }
}
