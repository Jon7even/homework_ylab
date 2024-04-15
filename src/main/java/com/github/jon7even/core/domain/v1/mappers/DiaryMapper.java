package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.diary.DiaryCreateDto;
import com.github.jon7even.application.dto.diary.DiaryResponseDto;
import com.github.jon7even.application.dto.diary.DiaryUpdateDto;
import com.github.jon7even.core.domain.v1.entities.workout.DiaryEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * Интерфейс для маппинга DTO и сущностей дневника
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper
public interface DiaryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "diaryCreateDto.userId", target = "userId")
    @Mapping(source = "diaryCreateDto.weightUser", target = "weightUser")
    @Mapping(source = "diaryCreateDto.growthUser", target = "growthUser")
    @Mapping(source = "now", target = "createdOn")
    @Mapping(source = "now", target = "updatedOn")
    @Mapping(source = "idTypeService", target = "idTypeService")
    DiaryEntity toDiaryEntityFromDtoCreate(DiaryCreateDto diaryCreateDto, LocalDateTime now, Integer idTypeService);

    @Mapping(source = "diaryEntity.weightUser", target = "weightUser")
    @Mapping(source = "diaryEntity.growthUser", target = "growthUser")
    @Mapping(source = "diaryEntity.createdOn", target = "createdOn")
    @Mapping(source = "diaryEntity.updatedOn", target = "updatedOn")
    DiaryResponseDto toDiaryResponseDtoFromEntity(DiaryEntity diaryEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "diaryUpdateDto.weightUser", target = "weightUser")
    @Mapping(source = "diaryUpdateDto.growthUser", target = "growthUser")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(source = "diaryUpdateDto.updatedOn", target = "updatedOn")
    @Mapping(target = "idTypeService", ignore = true)
    void updateDiaryEntityFromDtoUpdate(@MappingTarget DiaryEntity diaryEntity, DiaryUpdateDto diaryUpdateDto);
}