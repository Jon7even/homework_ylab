package services;

import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.services.impl.GroupPermissionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import setup.PreparationForTests;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GroupPermissionsServiceTest extends PreparationForTests {
    @InjectMocks
    private GroupPermissionsServiceImpl groupPermissionsService;

    @BeforeEach
    public void setUp() {
        initGroupPermissions();
    }

    @Test
    @DisplayName("Сервис разрешений должен вернуть разрешение true")
    public void getPermissionsForService_ReturnTrue() {
        boolean actualResult = groupPermissionsService.getPermissionsForService(
                firstIdInteger, historyAdmin.getNameType().getId(), FlagPermissions.DELETE
        );
        assertThat(actualResult)
                .isNotNull()
                .isTrue()
                .isEqualTo(historyAdmin.getDelete());
    }

    @Test
    @DisplayName("Сервис разрешений должен вернуть разрешение false")
    public void getPermissionsForService_ReturnFalse() {
        boolean actualResult = groupPermissionsService.getPermissionsForService(
                secondIdInteger, historyUser.getNameType().getId(), FlagPermissions.DELETE
        );
        assertThat(actualResult)
                .isNotNull()
                .isFalse()
                .isEqualTo(historyUser.getDelete());
    }
}
