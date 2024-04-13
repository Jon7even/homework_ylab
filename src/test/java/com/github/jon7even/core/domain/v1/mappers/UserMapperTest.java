package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.user.UserInMemoryDto;
import com.github.jon7even.application.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UserMapperTest extends PreparationForTests {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        initUsers();
        initUsersDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для создания новых данных в БД")
    void toEntityFromDtoCreate_ReturnEntity() {
        UserEntity actualResult = userMapper.toEntityFromDtoCreate(userCreateDtoFirst);

        assertNotNull(actualResult);
        assertNull(actualResult.getId());
        assertEquals(userCreateDtoFirst.getLogin(), actualResult.getLogin());
        assertEquals(userCreateDtoFirst.getPassword(), actualResult.getPassword());
        assertEquals(userCreateDtoFirst.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для обновления данных в БД")
    void toEntityFromDtoUpdate_ReturnEntity() {
        UserEntity actualResult = userMapper.toEntityFromDtoUpdate(userUpdateDtoFirst, secondIdLong, userLoginFirst);

        assertNotNull(actualResult);
        assertEquals(userLoginFirst, actualResult.getLogin());
        assertEquals(userLoginFirst, actualResult.getLogin());
        assertEquals(userUpdateDtoFirst.getPassword(), actualResult.getPassword());
        assertEquals(userUpdateDtoFirst.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в модель для короткого вывода о пользователе")
    void toShortDtoFromEntity_UserShortResponseDto() {
        UserShortResponseDto actualResult = userMapper.toShortDtoFromEntity(userEntityFirstExpected);

        assertNotNull(actualResult);
        assertEquals(userEntityFirstExpected.getLogin(), actualResult.getLogin());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в модель для пользователя в памяти")
    void toInMemoryDtoFromEntity_UserInMemoryDto() {
        UserInMemoryDto actualResult = userMapper.toInMemoryDtoFromEntity(userEntityFirstExpected);

        assertNotNull(actualResult);
        assertEquals(userEntityFirstExpected.getId(), actualResult.getId());
        assertEquals(userEntityFirstExpected.getLogin(), actualResult.getLogin());
        assertEquals(userEntityFirstExpected.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }
}
