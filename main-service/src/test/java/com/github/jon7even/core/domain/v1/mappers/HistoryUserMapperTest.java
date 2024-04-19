package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.history.HistoryUserResponseByAdminDto;
import com.github.jon7even.application.dto.history.HistoryUserResponseByUserDto;
import com.github.jon7even.core.domain.v1.entities.history.HistoryUserEntity;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        assertNotNull(actualResult);
        assertNull(actualResult.getId());
        assertEquals(historyUserEntityFirst.getDateTimeOn(), actualResult.getDateTimeOn());
        assertEquals(historyUserEntityFirst.getEvent(), actualResult.getEvent());
        assertEquals(historyUserEntityFirst.getIdTypeService(), actualResult.getIdTypeService());
    }

    @Test
    @DisplayName("Должен произойти правильный в DTO для возвращения списка с полной информацией")
    public void should_toHistoryUserResponseByAdminFromEntity_ReturnAdminListDto() {
        List<HistoryUserResponseByAdminDto> actualResult = historyUserMapper.toHistoryUserResponseByAdminFromEntity(
                historyUserEntityListAdminFirst
        );
        assertEquals(historyUserEntityListAdminFirst.size(), actualResult.size());
        assertEquals(historyUserEntityListAdminFirst.get(0).getId(), actualResult.get(0).getId());
        assertEquals(historyUserEntityListAdminFirst.get(0).getUserId(), actualResult.get(0).getUserId());
        assertEquals(historyUserEntityListAdminFirst.get(0).getDateTimeOn(), actualResult.get(0).getDateTimeOn());
        assertEquals(historyUserEntityListAdminFirst.get(0).getEvent(), actualResult.get(0).getEvent());
        assertEquals(historyUserEntityListAdminFirst.get(1).getId(), actualResult.get(1).getId());
        assertEquals(historyUserEntityListAdminFirst.get(1).getUserId(), actualResult.get(1).getUserId());
        assertEquals(historyUserEntityListAdminFirst.get(1).getDateTimeOn(), actualResult.get(1).getDateTimeOn());
        assertEquals(historyUserEntityListAdminFirst.get(1).getEvent(), actualResult.get(1).getEvent());
        assertEquals(historyUserEntityListAdminFirst.get(2).getId(), actualResult.get(2).getId());
        assertEquals(historyUserEntityListAdminFirst.get(2).getUserId(), actualResult.get(2).getUserId());
        assertEquals(historyUserEntityListAdminFirst.get(2).getDateTimeOn(), actualResult.get(2).getDateTimeOn());
        assertEquals(historyUserEntityListAdminFirst.get(2).getEvent(), actualResult.get(2).getEvent());
    }

    @Test
    @DisplayName("Должен произойти правильный в DTO для возвращения списка с краткой информацией")
    public void should_toHistoryUserResponseByUserFromEntity_ReturnUserListDto() {
        List<HistoryUserResponseByUserDto> actualResult = historyUserMapper.toHistoryUserResponseByUserFromEntity(
                historyUserEntityListUserFirst
        );
        assertEquals(historyUserEntityListAdminFirst.size(), actualResult.size());
        assertEquals(historyUserEntityListAdminFirst.get(0).getDateTimeOn(), actualResult.get(0).getDateTimeOn());
        assertEquals(historyUserEntityListAdminFirst.get(0).getEvent(), actualResult.get(0).getEvent());
        assertEquals(historyUserEntityListAdminFirst.get(1).getDateTimeOn(), actualResult.get(1).getDateTimeOn());
        assertEquals(historyUserEntityListAdminFirst.get(1).getEvent(), actualResult.get(1).getEvent());
        assertEquals(historyUserEntityListAdminFirst.get(2).getDateTimeOn(), actualResult.get(2).getDateTimeOn());
        assertEquals(historyUserEntityListAdminFirst.get(2).getEvent(), actualResult.get(2).getEvent());
    }
}