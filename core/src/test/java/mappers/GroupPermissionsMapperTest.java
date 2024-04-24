package mappers;

import com.github.jon7even.core.domain.v1.dto.permission.GroupPermissionsServiceDto;
import com.github.jon7even.core.domain.v1.mappers.GroupPermissionsMapper;
import com.github.jon7even.core.domain.v1.mappers.GroupPermissionsMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupPermissionsMapperTest extends PreparationForTests {
    private GroupPermissionsMapper permissionsMapper;

    @BeforeEach
    public void setUp() {
        permissionsMapper = new GroupPermissionsMapperImpl();
        initGroupPermissions();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в DTO для отображения разрешения группы для сервиса")
    public void toPermissionsServiceDtoFromEntity_ReturnDtoForService() {
        GroupPermissionsServiceDto actualResult = permissionsMapper.toPermissionsServiceDtoFromEntity(
                groupPermissionsForExpectedFirst, historyAdmin
        );
        assertThat(actualResult)
                .isNotNull();

        assertThat(actualResult.getId())
                .isNotNull()
                .isEqualTo(groupPermissionsForExpectedFirst.getId());
        assertThat(actualResult.getNameGroup())
                .isNotNull()
                .isEqualTo(groupPermissionsForExpectedFirst.getName());

        assertThat(actualResult.getNameService())
                .isNotNull()
                .isEqualTo(historyAdmin.getNameType().getName());
        assertThat(actualResult.getWrite())
                .isNotNull()
                .isEqualTo(historyAdmin.getWrite());
        assertThat(actualResult.getRead())
                .isNotNull()
                .isEqualTo(historyAdmin.getRead());
        assertThat(actualResult.getUpdate())
                .isNotNull()
                .isEqualTo(historyAdmin.getUpdate());
        assertThat(actualResult.getDelete())
                .isNotNull()
                .isEqualTo(historyAdmin.getDelete());
    }
}