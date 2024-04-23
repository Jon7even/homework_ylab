package mappers;

import com.github.jon7even.core.domain.v1.dto.diary.DiaryResponseDto;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapper;
import com.github.jon7even.core.domain.v1.mappers.DiaryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(actualResultFirst);
        assertNull(actualResultFirst.getId());
        assertEquals(diaryEntityFirst.getUserId(), actualResultFirst.getUserId());
        assertEquals(diaryEntityFirst.getWeightUser(), actualResultFirst.getWeightUser());
        assertEquals(diaryEntityFirst.getGrowthUser(), actualResultFirst.getGrowthUser());
        assertEquals(diaryEntityFirst.getCreatedOn(), actualResultFirst.getCreatedOn());
        assertEquals(diaryEntityFirst.getUpdatedOn(), actualResultFirst.getUpdatedOn());
        assertEquals(diaryEntityFirst.getIdTypeService(), actualResultFirst.getIdTypeService());

        DiaryEntity actualResultSecond = diaryMapper.toDiaryEntityFromDtoCreate(
                diaryCreateDtoSecond, diaryEntitySecond.getCreatedOn(), idTypeServiceDiary
        );
        assertNotNull(actualResultSecond);
        assertNull(actualResultSecond.getId());
        assertEquals(diaryEntitySecond.getUserId(), actualResultSecond.getUserId());
        assertEquals(diaryEntitySecond.getWeightUser(), actualResultSecond.getWeightUser());
        assertEquals(diaryEntitySecond.getGrowthUser(), actualResultSecond.getGrowthUser());
        assertEquals(diaryEntitySecond.getCreatedOn(), actualResultSecond.getCreatedOn());
        assertEquals(diaryEntitySecond.getUpdatedOn(), actualResultSecond.getUpdatedOn());
        assertEquals(diaryEntitySecond.getIdTypeService(), actualResultSecond.getIdTypeService());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления данных о дневнике")
    public void should_toDiaryResponseDtoFromEntity_ReturnDtoResponse() {
        DiaryResponseDto actualResultFirst = diaryMapper.toDiaryResponseDtoFromEntity(diaryEntityFirst);
        assertNotNull(actualResultFirst);
        assertEquals(diaryEntityFirst.getWeightUser(), actualResultFirst.getWeightUser());
        assertEquals(diaryEntityFirst.getGrowthUser(), actualResultFirst.getGrowthUser());
        assertEquals(diaryEntityFirst.getCreatedOn(), actualResultFirst.getCreatedOn());
        assertEquals(diaryEntityFirst.getUpdatedOn(), actualResultFirst.getUpdatedOn());

        DiaryResponseDto actualResultSecond = diaryMapper.toDiaryResponseDtoFromEntity(diaryEntitySecond);
        assertNotNull(actualResultSecond);
        assertEquals(diaryEntitySecond.getWeightUser(), actualResultSecond.getWeightUser());
        assertEquals(diaryEntitySecond.getGrowthUser(), actualResultSecond.getGrowthUser());
        assertEquals(diaryEntitySecond.getCreatedOn(), actualResultSecond.getCreatedOn());
        assertEquals(diaryEntitySecond.getUpdatedOn(), actualResultSecond.getUpdatedOn());
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
        assertEquals(actualResult, diaryEntityFirst);

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoFirst);
        assertNotNull(actualResult);
        assertNotEquals(diaryEntityFirst, actualResult);
        assertEquals(diaryUpdateDtoFirst.getWeightUser(), actualResult.getWeightUser());
        assertEquals(diaryUpdateDtoFirst.getGrowthUser(), actualResult.getGrowthUser());
        assertEquals(diaryUpdateDtoFirst.getUpdatedOn(), actualResult.getUpdatedOn());

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoSecond);
        assertNotNull(actualResult);
        assertNotEquals(diaryEntityFirst, actualResult);
        assertNotEquals(diaryUpdateDtoSecond.getWeightUser(), actualResult.getWeightUser());
        assertNotEquals(diaryUpdateDtoSecond.getGrowthUser(), actualResult.getGrowthUser());
        assertEquals(diaryUpdateDtoSecond.getUpdatedOn(), actualResult.getUpdatedOn());

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoThird);
        assertNotNull(actualResult);
        assertNotEquals(diaryEntityFirst, actualResult);
        assertEquals(diaryUpdateDtoThird.getWeightUser(), actualResult.getWeightUser());
        assertNotEquals(diaryUpdateDtoThird.getGrowthUser(), actualResult.getGrowthUser());
        assertEquals(diaryUpdateDtoThird.getUpdatedOn(), actualResult.getUpdatedOn());

        diaryMapper.updateDiaryEntityFromDtoUpdate(actualResult, diaryUpdateDtoThird);
        assertNotNull(actualResult);
        assertNotEquals(diaryEntityFirst, actualResult);
        assertNotEquals(diaryUpdateDtoFourth.getWeightUser(), actualResult.getWeightUser());
        assertNotEquals(diaryUpdateDtoFourth.getGrowthUser(), actualResult.getGrowthUser());
        assertEquals(diaryUpdateDtoFourth.getUpdatedOn(), actualResult.getUpdatedOn());
    }
}
