package mappers;

import com.github.jon7even.core.domain.v1.dto.user.UserInMemoryDto;
import com.github.jon7even.core.domain.v1.dto.user.UserShortResponseDto;
import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.mappers.UserMapper;
import com.github.jon7even.core.domain.v1.mappers.UserMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import static org.junit.jupiter.api.Assertions.*;


public class UserMapperTest extends PreparationForTests {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        initUsersEntity();
        initUsersDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для создания новых данных в БД")
    public void toEntityFromDtoCreate_ReturnEntityNotId() {
        UserEntity actualResult = userMapper.toEntityFromDtoCreate(userCreateDtoFirst);

        assertNotNull(actualResult);
        assertNull(actualResult.getId());
        assertEquals(userCreateDtoFirst.getLogin(), actualResult.getLogin());
        assertEquals(userCreateDtoFirst.getPassword(), actualResult.getPassword());
        assertEquals(userCreateDtoFirst.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для обновления данных в БД")
    public void toEntityFromDtoUpdate_ReturnEntity() {
        UserEntity actualResult = userMapper.toEntityFromDtoUpdate(userUpdateDtoFirst, secondIdLong, userLoginFirst);

        assertNotNull(actualResult);
        assertEquals(userLoginFirst, actualResult.getLogin());
        assertEquals(userLoginFirst, actualResult.getLogin());
        assertEquals(userUpdateDtoFirst.getPassword(), actualResult.getPassword());
        assertEquals(userUpdateDtoFirst.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в модель для короткого вывода о пользователе")
    public void toShortDtoFromEntity_UserShortResponseDto() {
        UserShortResponseDto actualResult = userMapper.toShortDtoFromEntity(userEntityFirst);

        assertNotNull(actualResult);
        assertEquals(userEntityFirst.getLogin(), actualResult.getLogin());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в модель для пользователя в памяти")
    public void toInMemoryDtoFromEntity_UserInMemoryDto() {
        UserInMemoryDto actualResult = userMapper.toInMemoryDtoFromEntity(userEntityFirst);

        assertNotNull(actualResult);
        assertEquals(userEntityFirst.getId(), actualResult.getId());
        assertEquals(userEntityFirst.getLogin(), actualResult.getLogin());
        assertEquals(userEntityFirst.getIdGroupPermissions(), actualResult.getIdGroupPermissions());
    }
}
