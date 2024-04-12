package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.core.domain.v1.entities.UserEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest extends PreparationForTests {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        initUsers();
        initUsersDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг для создания нового пользователя")
    void toEntityFromDTOCreate() {
        UserEntity actualResult = userMapper.toEntityFromDtoCreate(userCreateDtoFirst);

        assertNotNull(actualResult);
        assertNull(actualResult.getId());
        assertEquals(userCreateDtoFirst.getLogin(), actualResult.getLogin());
        assertEquals(userCreateDtoFirst.getPassword(), actualResult.getPassword());
        assertEquals(userCreateDtoFirst.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }
}
