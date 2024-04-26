package mappers;

import com.github.jon7even.core.domain.v1.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.core.domain.v1.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.mappers.TypeWorkoutMapper;
import com.github.jon7even.core.domain.v1.mappers.TypeWorkoutMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import setup.PreparationForTests;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeWorkoutMapperTest extends PreparationForTests {
    private TypeWorkoutMapper typeWorkoutMapper;

    @BeforeEach
    public void setUp() {
        typeWorkoutMapper = new TypeWorkoutMapperImpl();
        initTypeWorkoutEntity();
        initTypeWorkoutDto();
    }

    @Test
    @DisplayName("Маппинг из DTO в сущность для создания нового типа тренировки")
    public void should_toTypeWorkoutEntityFromDtoCreate_ReturnEntityNotId() {
        TypeWorkoutEntity actualResultWalking = typeWorkoutMapper.toTypeWorkoutEntityFromDtoCreate(
                typeWorkoutCreateDtoWalking, detailOfTypeWorkoutEntityTraveled
        );

        assertThat(actualResultWalking)
                .isNotNull();
        assertThat(actualResultWalking.getId())
                .isNull();

        assertThat(actualResultWalking.getTypeName())
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking.getTypeName());
        assertThat(actualResultWalking.getCaloriePerHour())
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking.getCaloriePerHour());
        assertThat(actualResultWalking.getDetailOfTypeWorkoutEntity())
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking.getDetailOfTypeWorkoutEntity());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления данных о типе тренировки")
    public void should_toTypeWorkoutResponseDtoFromEntity_ReturnDto() {
        DetailOfTypeWorkoutResponseDto actualResultDetail = typeWorkoutMapper.toDtoDetailOfTypeResponseFromEntity(
                detailOfTypeWorkoutEntityTraveled
        );
        TypeWorkoutResponseDto actualResultType = typeWorkoutMapper.toTypeWorkoutResponseDtoFromEntity(
                typeWorkoutEntityWalking
        );

        assertThat(actualResultType)
                .isNotNull();
        assertThat(actualResultType.getTypeName())
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking.getTypeName());
        assertThat(actualResultType.getCaloriePerHour())
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking.getCaloriePerHour());
        assertThat(actualResultType.getTypeWorkoutId())
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking.getId());

        assertThat(actualResultType.getDetailOfTypeWorkoutResponseDto())
                .isNotNull();
        assertThat(actualResultType.getDetailOfTypeWorkoutResponseDto().getId())
                .isNotNull()
                .isEqualTo(actualResultDetail.getId());
        assertThat(actualResultType.getDetailOfTypeWorkoutResponseDto().getName())
                .isNotNull()
                .isEqualTo(actualResultDetail.getName());
        assertThat(actualResultType.getDetailOfTypeWorkoutResponseDto().getIsFillingRequired())
                .isNotNull()
                .isEqualTo(actualResultDetail.getIsFillingRequired());
    }

    @Test
    @DisplayName("Маппинг в сущность из DTO Update для обновления данных о типе тренировки")
    public void should_updateTypeWorkoutEntityFromDtoUpdate_VoidUpdateEntity() {
        TypeWorkoutEntity actualResult = TypeWorkoutEntity.builder()
                .id(typeWorkoutEntityWalking.getId())
                .detailOfTypeWorkoutEntity(typeWorkoutEntityWalking.getDetailOfTypeWorkoutEntity())
                .typeName(typeWorkoutEntityWalking.getTypeName())
                .caloriePerHour(typeWorkoutEntityWalking.getCaloriePerHour())
                .build();

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(typeWorkoutEntityWalking);

        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(actualResult, typeWorkoutUpdateFirst);

        assertThat(actualResult)
                .isNotNull()
                .isNotEqualTo(typeWorkoutEntityWalking);
        assertThat(actualResult.getTypeName())
                .isNotNull()
                .isEqualTo(typeWorkoutUpdateFirst.getTypeName());
        assertThat(actualResult.getCaloriePerHour())
                .isNotNull()
                .isEqualTo(typeWorkoutUpdateFirst.getCaloriePerHour());

        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(actualResult, typeWorkoutUpdateSecond);

        assertThat(actualResult)
                .isNotNull()
                .isNotEqualTo(typeWorkoutEntityWalking);
        assertThat(actualResult.getTypeName())
                .isNotNull()
                .isEqualTo(typeWorkoutUpdateSecond.getTypeName());
        assertThat(actualResult.getCaloriePerHour())
                .isNotNull()
                .isNotEqualTo(typeWorkoutUpdateSecond.getCaloriePerHour());

        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(actualResult, typeWorkoutUpdateThird);

        assertThat(actualResult)
                .isNotNull()
                .isNotEqualTo(typeWorkoutEntityWalking);
        assertThat(actualResult.getTypeName())
                .isNotNull()
                .isNotEqualTo(typeWorkoutUpdateThird.getTypeName());
        assertThat(actualResult.getCaloriePerHour())
                .isNotNull()
                .isEqualTo(typeWorkoutUpdateThird.getCaloriePerHour());
    }

    @Test
    @DisplayName("Маппинг из списка сущностей в  список DTO Update для краткого ответа")
    public void should_toListTypeWorkoutDtoFromEntity_ReturnListDto() {
        List<TypeWorkoutEntity> listExpected = List.of(
                typeWorkoutEntityWalking, typeWorkoutEntityRunning, typeWorkoutEntityStrength,
                typeWorkoutEntityTreadmill, typeWorkoutEntityBicycling, typeWorkoutEntityYoga,
                typeWorkoutEntitySwimming, typeWorkoutEntityTennis, typeWorkoutEntitySkiing
        );
        List<TypeWorkoutShortDto> actualListResult = typeWorkoutMapper.toListTypeWorkoutDtoFromEntity(
                listExpected
        );

        assertThat(actualListResult)
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(listExpected.size());

        assertThat(actualListResult.get(0).getId())
                .isNotNull()
                .isEqualTo(listExpected.get(0).getId());
        assertThat(actualListResult.get(0).getTypeName())
                .isNotNull()
                .isEqualTo(listExpected.get(0).getTypeName());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления деталей о типе тренировки")
    public void should_toDtoDetailOfTypeResponseFromEntity_ReturnDto() {
        DetailOfTypeWorkoutResponseDto actualResultDetail = typeWorkoutMapper.toDtoDetailOfTypeResponseFromEntity(
                detailOfTypeWorkoutEntityTraveled
        );
        assertThat(actualResultDetail)
                .isNotNull();

        assertThat(actualResultDetail.getId())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled.getId());
        assertThat(actualResultDetail.getName())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled.getName());
        assertThat(actualResultDetail.getIsFillingRequired())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled.getIsFillingRequired());
    }

    @Test
    @DisplayName("Маппинг из DTO в сущность в DTO для сохранения данных о деталях в типе тренировки")
    public void should_toEntityDetailOfTypeFromDtoResponse_ReturnDto() {
        DetailOfTypeWorkoutEntity actualResultTraveled = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                detailOfTypeWorkoutResponseDtoTraveled
        );

        assertThat(actualResultTraveled)
                .isNotNull();

        assertThat(actualResultTraveled.getId())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled.getId());
        assertThat(actualResultTraveled.getName())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled.getName());
        assertThat(actualResultTraveled.getIsFillingRequired())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutEntityTraveled.getIsFillingRequired());

        DetailOfTypeWorkoutEntity actualResultExercises = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                detailOfTypeWorkoutResponseDtoExercises
        );

        assertThat(actualResultExercises)
                .isNotNull();

        assertThat(actualResultExercises.getId())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutResponseDtoExercises.getId());
        assertThat(actualResultExercises.getName())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutResponseDtoExercises.getName());
        assertThat(actualResultExercises.getIsFillingRequired())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutResponseDtoExercises.getIsFillingRequired());

        DetailOfTypeWorkoutEntity actualResultNotDetails = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                detailOfTypeWorkoutResponseDtoNotDetails
        );

        assertThat(actualResultNotDetails)
                .isNotNull();

        assertThat(actualResultNotDetails.getId())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutResponseDtoNotDetails.getId());
        assertThat(actualResultNotDetails.getName())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutResponseDtoNotDetails.getName());
        assertThat(actualResultNotDetails.getIsFillingRequired())
                .isNotNull()
                .isEqualTo(detailOfTypeWorkoutResponseDtoNotDetails.getIsFillingRequired());
    }

    @Test
    @DisplayName("Маппинг из списка сущностей в список DTO для получения деталей о типе тренировки")
    public void should_toListDetailResponseDtoFromEntity_ReturnListDto() {
        List<DetailOfTypeWorkoutEntity> listExpected = List.of(
                detailOfTypeWorkoutEntityTraveled, detailOfTypeWorkoutEntityExercises,
                detailOfTypeWorkoutEntityNotDetails
        );
        List<DetailOfTypeWorkoutResponseDto> actualListResult = typeWorkoutMapper.toListDetailResponseDtoFromEntity(
                listExpected
        );

        assertThat(actualListResult)
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(listExpected.size());

        assertThat(actualListResult.get(0).getId())
                .isNotNull()
                .isEqualTo(listExpected.get(0).getId());
        assertThat(actualListResult.get(0).getName())
                .isNotNull()
                .isEqualTo(listExpected.get(0).getName());
        assertThat(actualListResult.get(0).getIsFillingRequired())
                .isNotNull()
                .isEqualTo(listExpected.get(0).getIsFillingRequired());
    }
}

