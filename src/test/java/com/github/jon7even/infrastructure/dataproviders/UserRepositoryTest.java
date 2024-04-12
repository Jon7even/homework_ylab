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

    private Optional<UserEntity> actualResultFirst;

    private Optional<UserEntity> actualResultSecond;

    @BeforeEach
    public void setUp() {
        initUsers();
        actualResultFirst = userRepository.createUser(userEntityFirstForCreate);
        actualResultSecond = userRepository.createUser(userEntitySecondForCreate);
    }

    @Test
    @DisplayName("Новый пользователь должен создаться c релевантными полями")
    public void shouldCreateNewUser() {
        assertEquals(userEntityFirstExpected, actualResultFirst.get());
        assertEquals(userEntitySecondExpected, actualResultSecond.get());
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
}
