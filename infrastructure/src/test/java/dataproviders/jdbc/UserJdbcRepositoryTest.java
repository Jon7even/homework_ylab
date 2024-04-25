package dataproviders.jdbc;

import com.github.jon7even.core.domain.v1.entities.user.UserEntity;
import com.github.jon7even.core.domain.v1.exception.BadLoginException;
import dataproviders.jdbc.setup.ContainersSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserJdbcRepositoryTest extends ContainersSetup {
    private Integer sizeListRepositoryUser = 3;

    @Test
    @DisplayName("Новый пользователь должен создаться c релевантными полями")
    public void shouldCreateNewUser_ReturnNewUser() {
        assertThat(userEntityFirst)
                .isNotNull()
                .isEqualTo(userInDbFirst);
        assertThat(userEntitySecond)
                .isNotNull()
                .isEqualTo(userInDbSecond);
    }

    @Test
    @DisplayName("Пользователь админ должен быть уже в базе при инициализации программы")
    public void shouldFindUserAdminInBD_ReturnUserAdmin() {
        assertThat(userJdbcRepository.findByUserId(firstIdLong).get().getId())
                .isNotNull()
                .isEqualTo(userAdmin.getId());
    }

    @Test
    @DisplayName("Должен найти пользователя по ID")
    public void shouldFindUserById_ReturnUser() {
        assertThat(userJdbcRepository.findByUserId(secondIdLong).get().getId())
                .isNotNull()
                .isEqualTo(secondIdLong);

        assertThat(userJdbcRepository.findByUserId(thirdIdLong).get().getId())
                .isNotNull()
                .isEqualTo(thirdIdLong);
    }

    @Test
    @DisplayName("Не должен найти пользователя по ID")
    public void shouldNotFindUserById_ReturnUser() {
        assertThat(userJdbcRepository.findByUserId(9999L))
                .isEmpty();
        assertThat(userJdbcRepository.findByUserId(-9999L))
                .isEmpty();
    }

    @Test
    @DisplayName("Должен найти пользователя по логину")
    public void shouldFindUserByLogin_ReturnUser() {
        assertThat(userJdbcRepository.findByUserLogin(userLoginFirst).get())
                .isNotNull()
                .isEqualTo(userEntityFirst);

        assertThat(userJdbcRepository.findByUserLogin(userLoginSecond).get())
                .isNotNull()
                .isEqualTo(userEntitySecond);
    }

    @Test
    @DisplayName("Не должен зарегистрировать пользователя с запрещенным логином")
    public void shouldNotCreateUser_ReturnExceptionBadLogin() {
        userEntityForCreateFirst.setLogin("admin");
        assertThrows(BadLoginException.class, () -> userJdbcRepository.createUser(
                userEntityForCreateFirst
        ));
        userEntityForCreateSecond.setLogin("administrator");
        assertThrows(BadLoginException.class, () -> userJdbcRepository.createUser(
                userEntityForCreateFirst
        ));

        userEntityForCreateFirst.setLogin(userLoginFirst);
        userEntityForCreateSecond.setLogin(userLoginSecond);
    }

    @Test
    @DisplayName("Получить всех пользователей")
    public void shouldFindAllUsers_ReturnAllUsers() {
        assertThat(userJdbcRepository.getAllUsers())
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(sizeListRepositoryUser);
    }

    @Test
    @DisplayName("Должен обновить пользователя")
    public void shouldUpdateUser_ReturnUpdatedUses() {
        Optional<UserEntity> updatedUserFirst = userJdbcRepository.updateUser(userEntityForUpdateFirst);
        Optional<UserEntity> updatedUserSecond = userJdbcRepository.updateUser(userEntityForUpdateSecond);

        assertThat(updatedUserFirst.isPresent())
                .isTrue();
        assertThat(updatedUserSecond.isPresent())
                .isTrue();

        assertThat(userJdbcRepository.findByUserId(userEntityForUpdateFirst.getId()).get())
                .isEqualTo(userEntityForUpdateFirst);
        assertThat(userJdbcRepository.findByUserId(userEntityForUpdateSecond.getId()).get())
                .isEqualTo(userEntityForUpdateSecond);

        assertThat(userJdbcRepository.findByUserId(userEntityForUpdateFirst.getId()))
                .isNotEqualTo(userInDbFirst);
        assertThat(userJdbcRepository.findByUserId(userEntityForUpdateSecond.getId()))
                .isNotEqualTo(userInDbSecond);
    }
}
