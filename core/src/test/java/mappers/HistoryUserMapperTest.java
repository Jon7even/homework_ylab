package mappers;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.core.domain.v1.mappers.HistoryUserMapper;
import com.github.jon7even.core.domain.v1.mappers.HistoryUserMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoryUserMapperTest extends PreparationForTests {
    private HistoryUserMapper historyUserMapper;

    @BeforeEach
    public void setUp() {
        historyUserMapper = new HistoryUserMapperImpl();
        initHistoryEntity();
        initHistoryDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для создания новой истории")
    public void should_toEntityFromHistoryUserCreateDto_ReturnEntityNotId() {
        HistoryUserEntity actualResult = historyUserMapper.toEntityFromHistoryUserCreateDto(
                historyUserCreateDtoFirst, timeStartFirst, idTypeServiceHistory
        );

        assertThat(actualResult)
                .isNotNull();
        assertThat(actualResult.getId())
                .isNull();

        assertThat(actualResult.getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityFirst.getDateTimeOn());
        assertThat(actualResult.getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityFirst.getEvent());
    }

    @Test
    @DisplayName("Должен произойти правильный в DTO для возвращения списка с полной информацией")
    public void should_toHistoryUserResponseByAdminFromEntity_ReturnAdminListDto() {
        List<HistoryUserResponseByAdminDto> actualResult = historyUserMapper.toHistoryUserResponseByAdminFromEntity(
                historyUserEntityListAdminFirst
        );

        assertThat(actualResult)
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(historyUserEntityListAdminFirst.size());

        assertThat(actualResult.get(0))
                .isNotNull();
        assertThat(actualResult.get(0).getId())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(0).getId());
        assertThat(actualResult.get(0).getUserId())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(0).getUserId());
        assertThat(actualResult.get(0).getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(0).getDateTimeOn());
        assertThat(actualResult.get(0).getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(0).getEvent());

        assertThat(actualResult.get(1))
                .isNotNull();
        assertThat(actualResult.get(1).getId())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(1).getId());
        assertThat(actualResult.get(1).getUserId())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(1).getUserId());
        assertThat(actualResult.get(1).getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(1).getDateTimeOn());
        assertThat(actualResult.get(1).getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(1).getEvent());

        assertThat(actualResult.get(2))
                .isNotNull();
        assertThat(actualResult.get(2).getId())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(2).getId());
        assertThat(actualResult.get(2).getUserId())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(2).getUserId());
        assertThat(actualResult.get(2).getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(2).getDateTimeOn());
        assertThat(actualResult.get(2).getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(2).getEvent());
    }

    @Test
    @DisplayName("Должен произойти правильный в DTO для возвращения списка с краткой информацией")
    public void should_toHistoryUserResponseByUserFromEntity_ReturnUserListDto() {
        List<HistoryUserResponseByUserDto> actualResult = historyUserMapper.toHistoryUserResponseByUserFromEntity(
                historyUserEntityListUserFirst
        );
        assertThat(actualResult)
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(historyUserEntityListAdminFirst.size());

        assertThat(actualResult.get(0))
                .isNotNull();
        assertThat(actualResult.get(0).getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(0).getDateTimeOn());
        assertThat(actualResult.get(0).getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(0).getEvent());

        assertThat(actualResult.get(1))
                .isNotNull();
        assertThat(actualResult.get(1).getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(1).getDateTimeOn());
        assertThat(actualResult.get(1).getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(1).getEvent());

        assertThat(actualResult.get(2))
                .isNotNull();
        assertThat(actualResult.get(2).getDateTimeOn())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(2).getDateTimeOn());
        assertThat(actualResult.get(2).getEvent())
                .isNotNull()
                .isEqualTo(historyUserEntityListAdminFirst.get(2).getEvent());
    }
}