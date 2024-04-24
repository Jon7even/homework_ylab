package dataproviders.inmemory;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import com.github.jon7even.dataproviders.inmemory.GroupPermissionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import setup.PreparationForTests;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(actualResultFirstPermissions.get())
                .isNotNull()
                .isEqualTo(groupPermissionsForExpectedFirst);
        assertThat(actualResultSecondPermissions.get())
                .isNotNull()
                .isEqualTo(groupPermissionsForExpectedSecond);
    }

    @Test
    @DisplayName("Группы админ и пользователь должны уже быть в БД")
    public void shouldFindGroupPermissionsInBD_ReturnGroupPermissionsAdminAndUser() {
        GroupPermissionsEntity actualResultFirstAdmin = groupPermissionsRepository.findByGroupPermissionsId(
                firstIdInteger).get();

        assertThat(actualResultFirstAdmin)
                .isNotNull();
        assertThat(actualResultFirstAdmin.getId())
                .isNotNull()
                .isEqualTo(firstIdInteger);
        assertThat(actualResultFirstAdmin.getName())
                .isNotNull()
                .isEqualTo("Admin");

        GroupPermissionsEntity actualResultSecondUser = groupPermissionsRepository.findByGroupPermissionsId(
                secondIdInteger).get();

        assertThat(actualResultSecondUser)
                .isNotNull();
        assertThat(actualResultSecondUser.getId())
                .isNotNull()
                .isEqualTo(secondIdInteger);
        assertThat(actualResultSecondUser.getName())
                .isNotNull()
                .isEqualTo("User");
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

        assertThat(permissionsForHistoryAdmin)
                .isNotNull();
        assertThat(nameTypeHistory.getName())
                .isNotNull()
                .isEqualTo(permissionsForHistoryAdmin.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForDiaryAdmin)
                .isNotNull();
        assertThat(nameTypeDiary.getName())
                .isNotNull()
                .isEqualTo(permissionsForDiaryAdmin.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForWorkoutAdmin)
                .isNotNull();
        assertThat(nameTypeWorkout.getName())
                .isNotNull()
                .isEqualTo(permissionsForWorkoutAdmin.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForTypeWorkoutTypeAdmin)
                .isNotNull();
        assertThat(nameTypeWorkoutType.getName())
                .isNotNull()
                .isEqualTo(permissionsForTypeWorkoutTypeAdmin
                        .getServicesList().iterator().next().getNameType().getName());

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

        assertThat(permissionsForHistoryUser)
                .isNotNull();
        assertThat(nameTypeHistory.getName())
                .isNotNull()
                .isEqualTo(permissionsForHistoryAdmin.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForDiaryAdmin)
                .isNotNull();
        assertThat(nameTypeDiary.getName())
                .isNotNull()
                .isEqualTo(permissionsForDiaryAdmin.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForWorkoutAdmin)
                .isNotNull();
        assertThat(nameTypeWorkout.getName())
                .isNotNull()
                .isEqualTo(permissionsForWorkoutAdmin.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForWorkoutAdmin)
                .isNotNull();
        assertThat(nameTypeWorkout.getName())
                .isNotNull()
                .isEqualTo(permissionsForWorkoutAdmin.getServicesList().iterator().next().getNameType().getName());

    }
}
