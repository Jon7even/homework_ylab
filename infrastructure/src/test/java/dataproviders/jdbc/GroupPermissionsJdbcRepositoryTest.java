package dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.permissions.GroupPermissionsEntity;
import dataproviders.jdbc.setup.ContainersSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupPermissionsJdbcRepositoryTest extends ContainersSetup {
    private final Integer SIZE_SERVICE = 5;

    @BeforeEach
    public void setUp() {
        initGroupPermissions();
    }

    @Test
    @DisplayName("Группы админ и пользователь должны уже быть в БД")
    public void shouldFindGroupPermissionsInBD_ReturnGroupPermissionsAdminAndUser() {
        GroupPermissionsEntity actualResultFirstAdmin = groupPermissionsJdbcRepository.findByGroupPermissionsId(
                firstIdInteger).get();

        assertThat(actualResultFirstAdmin)
                .isNotNull();
        assertThat(actualResultFirstAdmin.getId())
                .isNotNull()
                .isEqualTo(firstIdInteger);
        assertThat(actualResultFirstAdmin.getName())
                .isNotNull()
                .isEqualTo("Admin");
        assertThat(actualResultFirstAdmin.getServicesList())
                .doesNotContainNull()
                .hasSize(SIZE_SERVICE);

        GroupPermissionsEntity actualResultSecondUser = groupPermissionsJdbcRepository.findByGroupPermissionsId(
                secondIdInteger).get();

        assertThat(actualResultSecondUser)
                .isNotNull();
        assertThat(actualResultSecondUser.getId())
                .isNotNull()
                .isEqualTo(secondIdInteger);
        assertThat(actualResultSecondUser.getName())
                .isNotNull()
                .isEqualTo("User");
        assertThat(actualResultSecondUser.getServicesList())
                .doesNotContainNull()
                .hasSize(SIZE_SERVICE);
    }

    @Test
    @DisplayName("Получить разрешения для конкретной группы и конкретного сервиса")
    public void findByGroupPermissionsIdAndByTypeServiceId_ReturnPermissionsForActualService() {
        GroupPermissionsEntity permissionsForHistoryAdmin =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        firstIdInteger, nameTypeHistory.getId()
                ).get();
        GroupPermissionsEntity permissionsForDiaryAdmin =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        firstIdInteger, nameTypeDiary.getId()
                ).get();
        GroupPermissionsEntity permissionsForWorkoutAdmin =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        firstIdInteger, nameTypeWorkout.getId()
                ).get();
        GroupPermissionsEntity permissionsForTypeWorkoutTypeAdmin =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        firstIdInteger, nameTypeWorkoutType.getId()
                ).get();
        GroupPermissionsEntity permissionsForUserTypeAdmin =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        firstIdInteger, nameTypeUser.getId()
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

        assertThat(permissionsForUserTypeAdmin)
                .isNotNull();
        assertThat(nameTypeUser.getName())
                .isNotNull()
                .isEqualTo(permissionsForUserTypeAdmin
                        .getServicesList().iterator().next().getNameType().getName());

        GroupPermissionsEntity permissionsForHistoryUser =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        secondIdInteger, nameTypeHistory.getId()
                ).get();
        GroupPermissionsEntity permissionsForDiaryUser =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        secondIdInteger, nameTypeDiary.getId()
                ).get();
        GroupPermissionsEntity permissionsForWorkoutUser =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        secondIdInteger, nameTypeWorkout.getId()
                ).get();
        GroupPermissionsEntity permissionsForTypeWorkoutTypeUser =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        secondIdInteger, nameTypeWorkoutType.getId()
                ).get();
        GroupPermissionsEntity permissionsForUserTypeUser =
                groupPermissionsJdbcRepository.findByGroupPermissionsByIdAndByTypeServiceId(
                        secondIdInteger, nameTypeUser.getId()
                ).get();

        assertThat(permissionsForHistoryUser)
                .isNotNull();
        assertThat(nameTypeHistory.getName())
                .isNotNull()
                .isEqualTo(permissionsForHistoryUser.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForDiaryUser)
                .isNotNull();
        assertThat(nameTypeDiary.getName())
                .isNotNull()
                .isEqualTo(permissionsForDiaryUser.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForWorkoutUser)
                .isNotNull();
        assertThat(nameTypeWorkout.getName())
                .isNotNull()
                .isEqualTo(permissionsForWorkoutUser.getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForTypeWorkoutTypeUser)
                .isNotNull();
        assertThat(nameTypeWorkoutType.getName())
                .isNotNull()
                .isEqualTo(permissionsForTypeWorkoutTypeUser
                        .getServicesList().iterator().next().getNameType().getName());

        assertThat(permissionsForUserTypeUser)
                .isNotNull();
        assertThat(nameTypeUser.getName())
                .isNotNull()
                .isEqualTo(permissionsForUserTypeUser
                        .getServicesList().iterator().next().getNameType().getName());
    }
}
