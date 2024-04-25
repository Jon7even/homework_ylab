package dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import dataproviders.jdbc.setup.ContainersSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class HistoryUserJdbcRepositoryTest extends ContainersSetup {
    private HistoryUserEntity actualResultHistoryFirst;
    private HistoryUserEntity actualResultHistorySecond;
    private HistoryUserEntity expectedResultHistoryFirst;
    private HistoryUserEntity expectedResultHistorySecond;

    @BeforeEach
    public void setUp() {
        initLocalDateTime();
        expectedResultHistoryFirst = HistoryUserEntity.builder()
                .id(firstIdLong)
                .userId(firstIdLong)
                .event("Test event First")
                .dateTimeOn(timeStartFirst)
                .build();
        expectedResultHistorySecond = HistoryUserEntity.builder()
                .id(secondIdLong)
                .userId(secondIdLong)
                .event("Test event Second")
                .dateTimeOn(timeStartSecond)
                .build();

        actualResultHistoryFirst = historyUserJdbcRepository.createHistoryOfUser(expectedResultHistoryFirst).get();
        actualResultHistorySecond = historyUserJdbcRepository.createHistoryOfUser(expectedResultHistorySecond).get();
    }

    @Test
    @DisplayName("Новое событие должно создаться c релевантными полями")
    public void shouldCreateNewHistoryUser() {
        assertThat(actualResultHistoryFirst)
                .isNotNull()
                .isEqualTo(expectedResultHistoryFirst);
        assertThat(actualResultHistorySecond)
                .isNotNull()
                .isEqualTo(expectedResultHistorySecond);
    }

    @Test
    @DisplayName("Получить все события по ID пользователя")
    public void shouldFindAllHistoryUserByIdUser_ReturnAllHistoryUsers() {
        expectedResultHistoryFirst.setEvent("Event 2");
        expectedResultHistoryFirst.setDateTimeOn(LocalDateTime.now());
        historyUserJdbcRepository.createHistoryOfUser(expectedResultHistoryFirst);
        expectedResultHistoryFirst.setEvent("Event 3");
        expectedResultHistoryFirst.setDateTimeOn(LocalDateTime.now().minusDays(1));
        historyUserJdbcRepository.createHistoryOfUser(expectedResultHistoryFirst);
        assertThat(historyUserJdbcRepository.findAllHistoryByUserId(expectedResultHistoryFirst.getUserId()))
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(3);
    }
}