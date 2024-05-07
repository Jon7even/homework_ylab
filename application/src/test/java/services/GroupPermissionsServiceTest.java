package services;

import com.github.jon7even.core.domain.v1.dao.GroupPermissionsDao;
import com.github.jon7even.core.domain.v1.entities.permissions.enums.FlagPermissions;
import com.github.jon7even.dataproviders.inmemory.GroupPermissionsRepository;
import com.github.jon7even.services.impl.GroupPermissionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.setup.ContainersSetup;
import setup.PreparationForTests;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GroupPermissionsServiceTest extends ContainersSetup {
    @InjectMocks
    private GroupPermissionsServiceImpl groupPermissionsService;
    @Mock
    private GroupPermissionsDao groupPermissionsDao;

    @BeforeEach
    public void setUp() {
        groupPermissionsDao = GroupPermissionsRepository.getInstance();
        groupPermissionsService = new GroupPermissionsServiceImpl(groupPermissionsDao);
        initGroupPermissions();
    }

    @Test
    @DisplayName("Сервис разрешений должен вернуть разрешение true")
    public void getPermissionsForService_ReturnTrue() {
        boolean actualResult = groupPermissionsService.getPermissionForService(
                firstIdLong, firstIdInteger, historyTypeAdmin.getNameType().getId(), FlagPermissions.DELETE
        );
        assertThat(actualResult)
                .isNotNull()
                .isTrue()
                .isEqualTo(historyTypeAdmin.getDelete());
    }

    @Test
    @DisplayName("Сервис разрешений должен вернуть разрешение false")
    public void getPermissionsForService_ReturnFalse() {
        boolean actualResult = groupPermissionsService.getPermissionForService(
                firstIdLong, secondIdInteger, historyTypeUser.getNameType().getId(), FlagPermissions.DELETE
        );
        assertThat(actualResult)
                .isNotNull()
                .isFalse()
                .isEqualTo(historyTypeUser.getDelete());
    }
}
