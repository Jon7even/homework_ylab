package com.github.jon7even.infrastructure.dataproviders;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.infrastructure.dataproviders.inmemory.GroupPermissionsRepository;
import com.github.jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class GroupPermissionsRepositoryTest extends PreparationForTests {
    @InjectMocks
    private GroupPermissionsRepository groupPermissionsRepository;

    private Optional<GroupPermissionsEntity> actualResultFirstPermissions;

    private Optional<GroupPermissionsEntity> actualResultSecondPermissions;

    @BeforeEach
    public void setUp() {
        initGroupPermissions();
        actualResultFirstPermissions = groupPermissionsRepository.createGroupPermissions(
                groupPermissionsForCreateFirst
        );
        actualResultSecondPermissions = groupPermissionsRepository.createGroupPermissions(
                groupPermissionsForCreateSecond
        );
    }

    @Test
    @DisplayName("Новая группа разрешений должна создаться")
    public void shouldCreateGroupPermissions() {
        assertEquals(groupPermissionsForExpectedFirst, actualResultFirstPermissions.get());
        assertEquals(groupPermissionsForExpectedSecond, actualResultSecondPermissions.get());
    }

    @Test
    @DisplayName("Группы админ и пользователь должны уже быть в БД")
    public void shouldFindGroupPermissionsInBD_ReturnGroupPermissionsAdminAndUser() {
        GroupPermissionsEntity actualResultFirstAdmin = groupPermissionsRepository.findByGroupPermissionsId(
                firstIdInteger).get();
        assertNotNull(actualResultFirstAdmin);
        assertEquals(firstIdInteger, actualResultFirstAdmin.getId());
        assertEquals("Admin", actualResultFirstAdmin.getName());

        GroupPermissionsEntity actualResultSecondUser = groupPermissionsRepository.findByGroupPermissionsId(
                secondIdInteger).get();
        assertNotNull(actualResultSecondUser);
        assertEquals(secondIdInteger, actualResultSecondUser.getId());
        assertEquals("User", actualResultSecondUser.getName());
    }

    @Test
    @DisplayName("Получить разрешения для конкретной группы и конкретного сервиса")
    public void findByGroupPermissionsIdAndByTypeServiceId_ReturnPermissionsForActualService() {
        GroupPermissionsEntity permissionsForHistoryAdmin =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        firstIdInteger, nameTypeHistory.getId()
                ).get();

        GroupPermissionsEntity permissionsForDiaryAdmin =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        firstIdInteger, nameTypeDiary.getId()
                ).get();
        GroupPermissionsEntity permissionsForWorkoutAdmin =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        firstIdInteger, nameTypeWorkout.getId()
                ).get();
        GroupPermissionsEntity permissionsForTypeWorkoutTypeAdmin =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        firstIdInteger, nameTypeWorkoutType.getId()
                ).get();

        assertNotNull(permissionsForHistoryAdmin);
        assertEquals(nameTypeHistory.getName(), permissionsForHistoryAdmin.getServicesList()
                .iterator().next().getNameType().getName());

        assertNotNull(permissionsForDiaryAdmin);
        assertEquals(nameTypeDiary.getName(), permissionsForDiaryAdmin.getServicesList()
                .iterator().next().getNameType().getName());

        assertNotNull(permissionsForWorkoutAdmin);
        assertEquals(nameTypeWorkout.getName(), permissionsForWorkoutAdmin.getServicesList()
                .iterator().next().getNameType().getName());

        assertNotNull(permissionsForTypeWorkoutTypeAdmin);
        assertEquals(nameTypeWorkoutType.getName(), permissionsForTypeWorkoutTypeAdmin.getServicesList()
                .iterator().next().getNameType().getName());

        GroupPermissionsEntity permissionsForHistoryUser =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        secondIdInteger, nameTypeHistory.getId()
                ).get();
        GroupPermissionsEntity permissionsForDiaryUser =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        secondIdInteger, nameTypeDiary.getId()
                ).get();
        GroupPermissionsEntity permissionsForWorkoutUser =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        secondIdInteger, nameTypeWorkout.getId()
                ).get();
        GroupPermissionsEntity permissionsForTypeWorkoutTypeUser =
                groupPermissionsRepository.findByGroupPermissionsIdAndByTypeServiceId(
                        secondIdInteger, nameTypeWorkoutType.getId()
                ).get();

        assertNotNull(permissionsForHistoryUser);
        assertEquals(nameTypeHistory.getName(), permissionsForHistoryUser.getServicesList()
                .iterator().next().getNameType().getName());

        assertNotNull(permissionsForDiaryUser);
        assertEquals(nameTypeDiary.getName(), permissionsForDiaryUser.getServicesList()
                .iterator().next().getNameType().getName());

        assertNotNull(permissionsForWorkoutUser);
        assertEquals(nameTypeWorkout.getName(), permissionsForWorkoutUser.getServicesList()
                .iterator().next().getNameType().getName());

        assertNotNull(permissionsForTypeWorkoutTypeUser);
        assertEquals(nameTypeWorkoutType.getName(), permissionsForTypeWorkoutTypeUser.getServicesList()
                .iterator().next().getNameType().getName());
    }
}
