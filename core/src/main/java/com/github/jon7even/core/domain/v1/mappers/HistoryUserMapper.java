package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.core.domain.v1.dto.history.HistoryUserCreateDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.core.domain.v1.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для маппинга DTO и сущностей истории
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper
public interface HistoryUserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "historyUserCreateDto.userId", target = "userId")
    @Mapping(source = "localDateTime", target = "dateTimeOn")
    @Mapping(source = "historyUserCreateDto.event", target = "event")
    HistoryUserEntity toEntityFromHistoryUserCreateDto(HistoryUserCreateDto historyUserCreateDto,
                                                       LocalDateTime localDateTime);

    @Mapping(source = "historyUser.id", target = "id")
    @Mapping(source = "historyUser.userId", target = "userId")
    @Mapping(source = "historyUser.dateTimeOn", target = "dateTimeOn")
    @Mapping(source = "historyUser.event", target = "event")
    List<HistoryUserResponseByAdminDto> toHistoryUserResponseByAdminFromEntity(List<HistoryUserEntity> historyUser);

    @Mapping(source = "historyUser.dateTimeOn", target = "dateTimeOn")
    @Mapping(source = "historyUser.event", target = "event")
    List<HistoryUserResponseByUserDto> toHistoryUserResponseByUserFromEntity(List<HistoryUserEntity> historyUser);
}
