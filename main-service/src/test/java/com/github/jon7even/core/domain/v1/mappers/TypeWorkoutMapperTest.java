package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.typeworkout.DetailOfTypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutResponseDto;
import com.github.jon7even.application.dto.typeworkout.TypeWorkoutShortDto;
import com.github.jon7even.core.domain.v1.entities.workout.DetailOfTypeWorkoutEntity;
import com.github.jon7even.core.domain.v1.entities.workout.TypeWorkoutEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
                typeWorkoutCreateDtoWalking, idTypeServiceTypeWorkout, detailOfTypeWorkoutEntityTraveled
        );
        assertNotNull(actualResultWalking);
        assertNull(actualResultWalking.getId());
        assertEquals(typeWorkoutEntityWalking.getTypeName(), actualResultWalking.getTypeName());
        assertEquals(typeWorkoutEntityWalking.getCaloriePerHour(), actualResultWalking.getCaloriePerHour());
        assertEquals(typeWorkoutEntityWalking.getDetailOfTypeWorkoutEntity(),
                actualResultWalking.getDetailOfTypeWorkoutEntity());
        assertEquals(typeWorkoutEntityWalking.getIdTypeService(), actualResultWalking.getIdTypeService());
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
        assertNotNull(actualResultType);
        assertEquals(typeWorkoutEntityWalking.getTypeName(), actualResultType.getTypeName());
        assertEquals(typeWorkoutEntityWalking.getCaloriePerHour(), actualResultType.getCaloriePerHour());
        assertEquals(typeWorkoutEntityWalking.getId(), actualResultType.getTypeWorkoutId());
        assertNotNull(actualResultType.getDetailOfTypeWorkoutResponseDto());
        assertEquals(actualResultDetail.getId(),
                actualResultType.getDetailOfTypeWorkoutResponseDto().getId());
        assertEquals(actualResultDetail.getName(),
                actualResultType.getDetailOfTypeWorkoutResponseDto().getName());
        assertEquals(actualResultDetail.getIsFillingRequired(),
                actualResultType.getDetailOfTypeWorkoutResponseDto().getIsFillingRequired());
    }

    @Test
    @DisplayName("Маппинг в сущность из DTO Update для обновления данных о типе тренировки")
    public void should_updateTypeWorkoutEntityFromDtoUpdate_VoidUpdateEntity() {
        TypeWorkoutEntity actualResult = TypeWorkoutEntity.builder()
                .id(typeWorkoutEntityWalking.getId())
                .detailOfTypeWorkoutEntity(typeWorkoutEntityWalking.getDetailOfTypeWorkoutEntity())
                .typeName(typeWorkoutEntityWalking.getTypeName())
                .caloriePerHour(typeWorkoutEntityWalking.getCaloriePerHour())
                .idTypeService(typeWorkoutEntityWalking.getIdTypeService())
                .build();
        assertEquals(actualResult, typeWorkoutEntityWalking);

        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(actualResult, typeWorkoutUpdateFirst);
        assertNotNull(actualResult);
        assertNotEquals(typeWorkoutEntityWalking, actualResult);
        assertEquals(actualResult.getTypeName(), typeWorkoutUpdateFirst.getTypeName());
        assertEquals(actualResult.getCaloriePerHour(), typeWorkoutUpdateFirst.getCaloriePerHour());

        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(actualResult, typeWorkoutUpdateSecond);
        assertNotNull(actualResult);
        assertNotEquals(typeWorkoutEntityWalking, actualResult);
        assertEquals(actualResult.getTypeName(), typeWorkoutUpdateSecond.getTypeName());
        assertNotEquals(actualResult.getCaloriePerHour(), typeWorkoutUpdateSecond.getCaloriePerHour());

        typeWorkoutMapper.updateTypeWorkoutEntityFromDtoUpdate(actualResult, typeWorkoutUpdateThird);
        assertNotNull(actualResult);
        assertNotEquals(typeWorkoutEntityWalking, actualResult);
        assertNotEquals(actualResult.getTypeName(), typeWorkoutUpdateThird.getTypeName());
        assertEquals(actualResult.getCaloriePerHour(), typeWorkoutUpdateThird.getCaloriePerHour());
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

        assertTrue(actualListResult.size() > 0);
        assertEquals(listExpected.size(), actualListResult.size());
        assertEquals(listExpected.get(0).getId(), actualListResult.get(0).getId());
        assertEquals(listExpected.get(0).getTypeName(), actualListResult.get(0).getTypeName());
    }

    @Test
    @DisplayName("Маппинг из сущности в DTO для предоставления деталей о типе тренировки")
    public void should_toDtoDetailOfTypeResponseFromEntity_ReturnDto() {
        DetailOfTypeWorkoutResponseDto actualResultDetail = typeWorkoutMapper.toDtoDetailOfTypeResponseFromEntity(
                detailOfTypeWorkoutEntityTraveled
        );
        assertNotNull(actualResultDetail);
        assertEquals(detailOfTypeWorkoutEntityTraveled.getId(), actualResultDetail.getId());
        assertEquals(detailOfTypeWorkoutEntityTraveled.getName(), actualResultDetail.getName());
        assertEquals(detailOfTypeWorkoutEntityTraveled.getIsFillingRequired(),
                actualResultDetail.getIsFillingRequired());
    }


    @Test
    @DisplayName("Маппинг из DTO в сущность в DTO для сохранения данных о деталях в типе тренировки")
    public void should_toEntityDetailOfTypeFromDtoResponse_ReturnDto() {
        DetailOfTypeWorkoutEntity actualResultTraveled = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                detailOfTypeWorkoutResponseDtoTraveled
        );
        assertNotNull(actualResultTraveled);
        assertEquals(detailOfTypeWorkoutEntityTraveled.getId(), actualResultTraveled.getId());
        assertEquals(detailOfTypeWorkoutEntityTraveled.getName(), actualResultTraveled.getName());
        assertEquals(detailOfTypeWorkoutEntityTraveled.getIsFillingRequired(),
                actualResultTraveled.getIsFillingRequired());

        DetailOfTypeWorkoutEntity actualResultExercises = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                detailOfTypeWorkoutResponseDtoExercises
        );
        assertNotNull(actualResultExercises);
        assertEquals(detailOfTypeWorkoutResponseDtoExercises.getId(), actualResultExercises.getId());
        assertEquals(detailOfTypeWorkoutResponseDtoExercises.getName(), actualResultExercises.getName());
        assertEquals(detailOfTypeWorkoutResponseDtoExercises.getIsFillingRequired(),
                actualResultExercises.getIsFillingRequired());

        DetailOfTypeWorkoutEntity actualResultNotDetails = typeWorkoutMapper.toEntityDetailOfTypeFromDtoResponse(
                detailOfTypeWorkoutResponseDtoNotDetails
        );
        assertNotNull(actualResultNotDetails);
        assertNotNull(actualResultExercises);
        assertEquals(detailOfTypeWorkoutResponseDtoNotDetails.getId(), actualResultNotDetails.getId());
        assertEquals(detailOfTypeWorkoutResponseDtoNotDetails.getName(), actualResultNotDetails.getName());
        assertEquals(detailOfTypeWorkoutResponseDtoNotDetails.getIsFillingRequired(),
                actualResultNotDetails.getIsFillingRequired());
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

        assertTrue(actualListResult.size() > 0);
        assertEquals(listExpected.size(), actualListResult.size());
        assertEquals(listExpected.get(0).getId(), actualListResult.get(0).getId());
        assertEquals(listExpected.get(0).getName(), actualListResult.get(0).getName());
        assertEquals(listExpected.get(0).getIsFillingRequired(), actualListResult.get(0).getIsFillingRequired());
    }
}

