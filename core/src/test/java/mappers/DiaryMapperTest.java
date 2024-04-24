package mappers;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapper;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import static org.assertj.core.api.Assertions.assertThat;

public class DiaryMapperTest extends PreparationForTests {
    private DiaryMapper diaryMapper;

    @BeforeEach
    public void setUp() {
        diaryMapper = new DiaryMapperImpl();
        initDiaryEntity();
        initDiaryDto();
    }

    @Test
    @DisplayName("Маппинг из DTO в сущность для создания нового дневника")
    public void should_toDiaryEntityFromDtoCreate_ReturnEntityNotId() {
        DiaryEntity actualResultFirst = diaryMapper.toDiaryEntityFromDtoCreate(
                diaryCreateDtoFirst, diaryEntityFirst.getCreatedOn(), idTypeServiceDiary
        );

        assertThat(actualResultFirst)
                .isNotNull();
        assertThat(actualResultFirst.getId())
                .isNull();

        assertThat(actualResultFirst.getUserId())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getUserId());
        assertThat(actualResultFirst.getWeightUser())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getWeightUser());
        assertThat(actualResultFirst.getGrowthUser())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getGrowthUser());
        assertThat(actualResultFirst.getCreatedOn())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getCreatedOn());
        assertThat(actualResultFirst.getUpdatedOn())
                .isNotNull()
                .isNotEqualTo(diaryEntityFirst.getUpdatedOn());

        DiaryEntity actualResultSecond = diaryMapper.toDiaryEntityFromDtoCreate(
                diaryCreateDtoSecond, diaryEntitySecond.getCreatedOn(), idTypeServiceDiary
        );
        assertThat(actualResultSecond)
                .isNotNull();
        assertThat(actualResultSecond.getId())
                .isNull();

        assertThat(actualResultSecond.getUserId())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getUserId());
        assertThat(actualResultSecond.getWeightUser())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getWeightUser());
        assertThat(actualResultSecond.getGrowthUser())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getGrowthUser());
        assertThat(actualResultSecond.getCreatedOn())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getCreatedOn());
        assertThat(actualResultSecond.getUpdatedOn())
                .isNotNull()
                .isNotEqualTo(diaryEntitySecond.getUpdatedOn());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления данных о дневнике")
    public void should_toDiaryResponseDtoFromEntity_ReturnDtoResponse() {
        DiaryResponseDto actualResultFirst = diaryMapper.toDiaryResponseDtoFromEntity(diaryEntityFirst);

        assertThat(actualResultFirst)
                .isNotNull();

        assertThat(actualResultFirst.getWeightUser())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getWeightUser());
        assertThat(actualResultFirst.getGrowthUser())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getGrowthUser());
        assertThat(actualResultFirst.getCreatedOn())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getCreatedOn());
        assertThat(actualResultFirst.getUpdatedOn())
                .isNotNull()
                .isEqualTo(diaryEntityFirst.getUpdatedOn());

        DiaryResponseDto actualResultSecond = diaryMapper.toDiaryResponseDtoFromEntity(diaryEntitySecond);

        assertThat(actualResultSecond)
                .isNotNull();

        assertThat(actualResultSecond.getWeightUser())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getWeightUser());
        assertThat(actualResultSecond.getGrowthUser())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getGrowthUser());
        assertThat(actualResultSecond.getCreatedOn())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getCreatedOn());
        assertThat(actualResultSecond.getUpdatedOn())
                .isNotNull()
                .isEqualTo(diaryEntitySecond.getUpdatedOn());
    }

    @Test
    @DisplayName("Маппинг в сущность из DTO Update для обновления данных о дневнике")
    public void should_updateTypeWorkoutEntityFromDtoUpdate_VoidUpdateEntity() {
        DiaryEntity actualResult = DiaryEntity.builder()
                .id(diaryEntityFirst.getId())
                .userId(diaryEntityFirst.getUserId())
                .weightUser(diaryEntityFirst.getWeightUser())
                .growthUser(diaryEntityFirst.getGrowthUser())
                .createdOn(diaryEntityFirst.getCreatedOn())
                .updatedOn(diaryEntityFirst.getUpdatedOn())
                .idTypeService(diaryEntityFirst.getIdTypeService())
                .build();

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(diaryEntityFirst);

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoFirst);

        assertThat(actualResult)
                .isNotNull();

        assertThat(actualResult.getWeightUser())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoFirst.getWeightUser());
        assertThat(actualResult.getGrowthUser())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoFirst.getGrowthUser());
        assertThat(actualResult.getUpdatedOn())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoFirst.getUpdatedOn());


        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoSecond);

        assertThat(actualResult)
                .isNotNull()
                .isNotEqualTo(diaryEntityFirst);

        assertThat(actualResult.getWeightUser())
                .isNotNull()
                .isNotEqualTo(diaryUpdateDtoSecond.getWeightUser());
        assertThat(actualResult.getGrowthUser())
                .isNotNull()
                .isNotEqualTo(diaryUpdateDtoSecond.getGrowthUser());
        assertThat(actualResult.getUpdatedOn())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoSecond.getUpdatedOn());

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoThird);

        assertThat(actualResult)
                .isNotNull()
                .isNotEqualTo(diaryEntityFirst);

        assertThat(actualResult.getWeightUser())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoThird.getWeightUser());
        assertThat(actualResult.getGrowthUser())
                .isNotNull()
                .isNotEqualTo(diaryUpdateDtoThird.getGrowthUser());
        assertThat(actualResult.getUpdatedOn())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoThird.getUpdatedOn());

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoFourth);

        assertThat(actualResult)
                .isNotNull()
                .isNotEqualTo(diaryEntityFirst);

        assertThat(actualResult.getWeightUser())
                .isNotNull()
                .isNotEqualTo(diaryUpdateDtoFourth.getWeightUser());
        assertThat(actualResult.getGrowthUser())
                .isNotNull()
                .isEqualTo(diaryUpdateDtoFourth.getGrowthUser());
        assertThat(actualResult.getUpdatedOn())
                .isNotNull()
                .isNotEqualTo(diaryUpdateDtoFourth.getUpdatedOn());
    }
}
