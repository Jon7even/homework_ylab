package com.github.jon7even.core.domain.v1.mappers;

import com.github.jon7even.application.dto.permission.GroupPermissionsServiceDto;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupPermissionsMapperTest extends PreparationForTests {
    private GroupPermissionsMapper permissionsMapper;

    @BeforeEach
    public void setUp() {
        permissionsMapper = new GroupPermissionsMapperImpl();
        initGroupPermissions();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в DTO для отображения разрешения группы для сервиса")
    public void toPermissionsServiceDtoFromEntity_ReturnDtoForService() {
        GroupPermissionsServiceDto actualResult = permissionsMapper.toPermissionsServiceDtoFromEntity(
                groupPermissionsForExpectedFirst, historyAdmin
        );

        assertNotNull(actualResult);
        assertEquals(groupPermissionsForExpectedFirst.getId(), actualResult.getId());
        assertEquals(groupPermissionsForExpectedFirst.getName(), actualResult.getNameGroup());
        assertEquals(historyAdmin.getNameType().getName(), actualResult.getNameService());
        assertEquals(historyAdmin.getWrite(), actualResult.getWrite());
        assertEquals(historyAdmin.getRead(), actualResult.getRead());
        assertEquals(historyAdmin.getUpdate(), actualResult.getUpdate());
        assertEquals(historyAdmin.getDelete(), actualResult.getDelete());
    }
}