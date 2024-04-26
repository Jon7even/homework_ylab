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

import static org.assertj.core.api.Assertions.assertThat;


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

        assertThat(actualResult)
                .isNotNull();
        assertThat(actualResult.getId())
                .isNull();

        assertThat(actualResult.getLogin())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getLogin());
        assertThat(actualResult.getPassword())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getPassword());
        assertThat(actualResult.getIdGroupPermissions())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getIdGroupPermissions());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для обновления данных в БД")
    public void toEntityFromDtoUpdate_ReturnEntity() {
        UserEntity actualResult = userMapper.toEntityFromDtoUpdate(userUpdateDtoFirst, secondIdLong, userLoginFirst);

        assertThat(actualResult)
                .isNotNull();

        assertThat(actualResult.getLogin())
                .isNotNull()
                .isEqualTo(userLoginFirst);
        assertThat(actualResult.getPassword())
                .isNotNull()
                .isEqualTo(userUpdateDtoFirst.getPassword());
        assertThat(actualResult.getIdGroupPermissions())
                .isNotNull()
                .isEqualTo(userUpdateDtoFirst.getIdGroupPermissions());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в модель для короткого вывода о пользователе")
    public void toShortDtoFromEntity_UserShortResponseDto() {
        UserShortResponseDto actualResult = userMapper.toShortDtoFromEntity(userEntityFirst);

        assertThat(actualResult)
                .isNotNull();

        assertThat(actualResult.getLogin())
                .isNotNull()
                .isEqualTo(userEntityFirst.getLogin());
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в модель для пользователя в памяти")
    public void toInMemoryDtoFromEntity_UserInMemoryDto() {
        UserInMemoryDto actualResult = userMapper.toInMemoryDtoFromEntity(userEntityFirst);

        assertThat(actualResult)
                .isNotNull();

        assertThat(actualResult.getId())
                .isNotNull()
                .isEqualTo(userEntityFirst.getId());
        assertThat(actualResult.getLogin())
                .isNotNull()
                .isEqualTo(userEntityFirst.getLogin());
        assertThat(actualResult.getIdGroupPermissions())
                .isNotNull()
                .isEqualTo(userEntityFirst.getIdGroupPermissions());
    }
}
