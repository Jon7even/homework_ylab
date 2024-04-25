package dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import dataproviders.jdbc.setup.ContainersSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DiaryJdbcRepositoryTest extends ContainersSetup {
    private DiaryEntity actualResultDiaryFirst;
    private DiaryEntity actualResultDiarySecond;

    @BeforeEach
    public void setUp() {
        initDiaryEntity();
        actualResultDiaryFirst = diaryJdbcRepository.createDiary(diaryEntityFirst).get();
        actualResultDiarySecond = diaryJdbcRepository.createDiary(diaryEntitySecond).get();
    }

    @Test
    @DisplayName("Новый дневник должен создаться c релевантными полями")
    public void shouldCreateNewDiary_ReturnNewDiary() {
        assertThat(actualResultDiaryFirst)
                .isNotNull()
                .isEqualTo(diaryEntityFirst);
        assertThat(actualResultDiarySecond)
                .isNotNull()
                .isEqualTo(diaryEntitySecond);
    }

    @Test
    @DisplayName("Должен обновить дневник пользователя")
    public void shouldUpdateDiary_ReturnUpdatedDiary() {
        Optional<DiaryEntity> updatedDiaryFirst = diaryJdbcRepository.updateDiary(diaryEntityForUpdateFirst);
        Optional<DiaryEntity> updatedDiarySecond = diaryJdbcRepository.updateDiary(diaryEntityForUpdateSecond);

        assertThat(updatedDiaryFirst.isPresent())
                .isTrue();
        assertThat(updatedDiarySecond.isPresent())
                .isTrue();

        assertThat(diaryJdbcRepository.findByUserId(diaryEntityForUpdateFirst.getUserId()).get())
                .isEqualTo(diaryEntityForUpdateFirst);
        assertThat(diaryJdbcRepository.findByUserId(diaryEntityForUpdateSecond.getUserId()).get())
                .isEqualTo(diaryEntityForUpdateSecond);

        assertThat(diaryJdbcRepository.findByDiaryId(diaryEntityFirst.getId()).get())
                .isNotEqualTo(diaryEntityFirst);
        assertThat(diaryJdbcRepository.findByDiaryId(diaryEntitySecond.getId()).get())
                .isNotEqualTo(diaryEntitySecond);
    }

    @Test
    @DisplayName("Должен найти дневник по diaryId")
    public void shouldFindDiaryByIdDiary_ReturnDiary() {
        assertThat(diaryJdbcRepository.findByDiaryId(firstIdLong).get().getId())
                .isNotNull()
                .isEqualTo(firstIdLong);

        assertThat(diaryJdbcRepository.findByDiaryId(secondIdLong).get().getId())
                .isNotNull()
                .isEqualTo(secondIdLong);
    }

    @Test
    @DisplayName("Должен найти дневник по userId")
    public void shouldFindDiaryByIdUser_ReturnDiary() {
        assertThat(diaryJdbcRepository.findByDiaryId(firstIdLong).get().getUserId())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getUserId());

        assertThat(diaryJdbcRepository.findByDiaryId(secondIdLong).get().getUserId())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getUserId());
    }
}